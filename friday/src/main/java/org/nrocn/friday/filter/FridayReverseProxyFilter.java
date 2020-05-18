package org.nrocn.friday.filter;

import com.auth0.jwt.interfaces.DecodedJWT;

import org.nrocn.friday.crypto.SignUtils;
import org.nrocn.friday.model.FridaySession;
import org.nrocn.friday.model.RequestData;
import org.nrocn.friday.utils.AuthConstant;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 反向代理
 */
public class FridayReverseProxyFilter extends OncePerRequestFilter {


    public static String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        Cookie tokenCookie = Arrays.stream(cookies)
                .filter(cookie -> "friday".equals(cookie.getName()))
                .findAny().orElse(null);
        if (tokenCookie == null) {
            return null;
        }
        return tokenCookie.getValue();
    }


    private String setAuthHeader(RequestData data) {
        //获取http 请求头
        HttpHeaders headers = data.getHeaders();

        FridaySession session = this.getSession(data.getOriginRequest());
        if (session == null) {
            throw new IllegalArgumentException("非法请求");
        }
        headers.set(AuthConstant.CURRENT_USER_HEADER, session.getUserId());
        headers.set(AuthConstant.CURRENT_USER_EMAIL, session.getEmail());
        return AuthConstant.AUTHORIZATION_SUPPORT_USER;
    }


    private FridaySession getSession(HttpServletRequest request) {
        String token = getToken(request);
        if (token == null) {
            return null;
        }
        try {
            DecodedJWT jwt = SignUtils.decodedjwt(token, "");
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        FridaySession session = getSession(request);
        request.setAttribute("friday-user", session);
        filterChain.doFilter(request, response);
    }
}