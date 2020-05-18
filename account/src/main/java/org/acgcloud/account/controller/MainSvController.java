package org.acgcloud.account.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.nrocn.friday.crypto.SignUtils;
import org.nrocn.friday.model.FridaySession;
import org.nrocn.friday.utils.AuthConstant;
import org.nrocn.friday.utils.FridayUtil;
import org.nrocn.lib.baserqnp.IMicroResponsable;
import org.nrocn.lib.baserqnp.ResultCode;
import org.nrocn.user.entity.UserEntity;
import org.nrocn.user.model.AccountDomain;
import org.nrocn.user.model.UserDomain;
import org.nrocn.user.services.JpaUserRespositoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.acgcloud.account.dto.AccountRequest;
import org.acgcloud.common.dto.WebResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user/main")
@CrossOrigin("*")
public class MainSvController {


    @Autowired
    private JpaUserRespositoryServices jpaUserRespositoryServices;


    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    public String company(){
        return null;
    }

    @RequestMapping("/model/info")
    public IMicroResponsable modelInfo(){
        return WebResponse.getPrototype().successResp("account",null);
    }



    private String setCookieAndToken(UserEntity userEntity){
        String jwt= SignUtils.generateToken(userEntity.getUsername(),
                userEntity.getAccountEntity().getMail(),
                AuthConstant.SIGN_TOKEN,
                "org.acgcloud.com",
                true,
                2 * 60 * 60 * 1000);
        return jwt;
    }

    @RequestMapping("/role/info")
    public IMicroResponsable info(){
        FridaySession fridaySession = FridayUtil.getFridaySessionFromRequest(httpServletRequest);
        UserEntity info = jpaUserRespositoryServices.info(fridaySession.getUserId());
        info.getAccountEntity().setPasswordSalt(null);
        info.getAccountEntity().setPassword(null);
        info.getAccountEntity().setId(-1);
        info.setId(-1);
        return WebResponse.getPrototype().successResp("返回成功",info);
    }

    @RequestMapping(value = "/role/pwd/update",consumes = "application/json")
    public IMicroResponsable updatePwd(@RequestBody AccountDomain accountDomain){
        FridaySession fridaySession = FridayUtil.getFridaySessionFromRequest(httpServletRequest);
        UserEntity info = jpaUserRespositoryServices.info(fridaySession.getUserId());
        info.getAccountEntity().setPassword(accountDomain.getPassword());
        UserEntity userEntity = jpaUserRespositoryServices.updatePassword(info);
        return WebResponse.getPrototype().successResp("修改密码成功",userEntity);
    }




    @PostMapping("/user/login")
    public IMicroResponsable login(@RequestBody AccountRequest accountRequest){
        UserEntity login = jpaUserRespositoryServices.login(accountRequest.getAccount(), accountRequest.getPassword());
        if(ObjectUtils.isEmpty(login)){
            return WebResponse.getPrototype().failedResp("账号不存在", ResultCode.FAILURE);
        }
        else if(ObjectUtils.isEmpty(login.getAccountEntity())){
            return WebResponse.getPrototype().failedResp("密码错误", ResultCode.FAILURE);
        }
        String jwt = setCookieAndToken(login);
        Cookie cookie = new Cookie(AuthConstant.COOKIE_NAME,jwt);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        httpServletResponse.addHeader(AuthConstant.COOKIE_NAME,jwt);
        return WebResponse.getPrototype().successResp("登录成功",jwt);
    }

    @RequestMapping("/user/register")
    public IMicroResponsable registry(@RequestBody AccountRequest accountRequest){
        UserDomain registry = jpaUserRespositoryServices.registry(accountRequest.getAccount(),
                accountRequest.getMail(), accountRequest.getPassword());
        if(ObjectUtils.isEmpty(registry)){
            return WebResponse.getPrototype().failedResp("该账号已存在", ResultCode.FAILURE);
        }
        return WebResponse.getPrototype().successResp("注册成功用户名:" + accountRequest.getAccount() +",密码"+ accountRequest.getPassword(),accountRequest);
    }
}
