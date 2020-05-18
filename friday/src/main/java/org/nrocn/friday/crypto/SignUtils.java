package org.nrocn.friday.crypto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * jwt签名工具
 */
public class SignUtils {
    //email
    public static final String CLAIM_EMAIL = "email";
    //user id
    public static final String CLAIM_USER_ID = "userId";
    //support
    public static final String CLAIM_SUPPORT = "support";


    /**
     * 解签 jwt 令牌
     * @param jwt   源jwt令牌
     * @param signingToken  解签签名
     * @return
     */
    public static DecodedJWT decodedjwt(String jwt, String signingToken){
        Algorithm algorithm = Algorithm.HMAC256(signingToken);
        DecodedJWT verify = JWT.require(algorithm).build().verify(jwt);
        return  verify;
    }


    /**
     * 生成 jwt 令牌
     * @param userId userInfo 表中的id
     * @param email 邮箱
     * @param signingToken 加密信息
     * @param support
     * @param duration
     */
    public static String generateToken(String userId , String email , String signingToken , String webAddress, boolean support , long duration ){
             if(StringUtils.isAllBlank(userId,email,signingToken,webAddress)){
                 throw new IllegalArgumentException("参数不正确");
             }
             long time = System.currentTimeMillis() + duration;
             System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));

             Algorithm algorithm = Algorithm.HMAC256(signingToken);
             String jwt = JWT.create()
                          .withClaim(CLAIM_USER_ID,userId)
                          .withClaim(CLAIM_EMAIL,email)
                          .withClaim(CLAIM_SUPPORT,support)
                          .withIssuer(webAddress)
                          .withExpiresAt(new Date(time))
                          .sign(algorithm);
             return jwt;
    }

    public static  JWTCreator.Builder generateJwt(String userId , String email ,String webAddress, boolean support , long duration ){
        if(StringUtils.isAllBlank(userId,email,webAddress)){
            throw new IllegalArgumentException("参数不正确");
        }
        long time = System.currentTimeMillis() + duration;
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));

        JWTCreator.Builder builder = JWT.create()
                .withClaim(CLAIM_USER_ID, userId)
                .withClaim(CLAIM_EMAIL, email)
                .withClaim(CLAIM_SUPPORT, support)
                .withIssuer(webAddress)
                .withExpiresAt(new Date(time));
        return builder;
    }
}
