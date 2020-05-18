package org.nrocn.friday.filter;

import com.auth0.jwt.interfaces.DecodedJWT;

import org.nrocn.friday.crypto.SignUtils;
import org.nrocn.friday.model.FridaySession;
import org.nrocn.friday.model.RequestData;
import org.nrocn.friday.utils.AuthConstant;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


public class FridayFilter extends OncePerRequestFilter {

    private static String getToken(HttpServletRequest request) {

        //尝试从token那拿
        String token = request.getHeader(AuthConstant.COOKIE_NAME);
        if(token != null && token.trim().length() > 0){
            return token;
        }

        //再拿cookie
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length < 1){
            return null;
        }
        Cookie tokenCookie = Arrays.stream(cookies)
                .filter((cookie)-> AuthConstant.COOKIE_NAME.equals(cookie.getName()))
                .findAny().orElse(null);
        if (tokenCookie == null){
            return null;
        }
        return tokenCookie.getValue();
    }



    private String setAuthHeader(RequestData data) {
        //获取http 请求头
        HttpHeaders headers = data.getHeaders();

        FridaySession session = this.getSession(data.getOriginRequest());
        if(session == null){
            throw new IllegalArgumentException("非法请求");
        }
        headers.set(AuthConstant.CURRENT_USER_HEADER,session.getUserId());
        headers.set(AuthConstant.CURRENT_USER_EMAIL,session.getEmail());
        return AuthConstant.AUTHORIZATION_SUPPORT_USER;
    }


    private FridaySession getSession(HttpServletRequest request) {
        String token = getToken(request);
        if (token == null) {
            return null;
        }
        try {
            DecodedJWT jwt = SignUtils.decodedjwt(token, AuthConstant.SIGN_TOKEN);
            String userId = jwt.getClaim(SignUtils.CLAIM_USER_ID).asString();
            String email = jwt.getClaim(SignUtils.CLAIM_EMAIL).asString();
            boolean support = jwt.getClaim(SignUtils.CLAIM_SUPPORT).asBoolean();
            FridaySession session = FridaySession.builder().userId(userId).email(email).support(support).build();
            return session;
        } catch (Exception e) {
             return null;
        }
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String origin = request.getHeader("Origin");
        if (!StringUtils.isEmpty(origin)) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, PUT, DELETE");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Accept, Content-Type, Content-Length, Cookie, Accept-Encoding, X-CSRF-Token, Authorization,nrocn-friday");
        }

        if("OPTIONS".equals(request.getMethod())){
            return;
        }

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf8");

        FridaySession session = getSession(request);
        if (session == null){
            response.sendError(403);
            return ;
        }
        request.setAttribute(AuthConstant.SESSION_NAME,session);
        filterChain.doFilter(request,response);
    }
}
