package org.nrocn.user.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.nrocn.user.entity.AccountEntity;
import org.nrocn.user.model.AccountDomain;

import java.util.Date;
import java.util.UUID;

public abstract class AccountUtils {
    //加盐
    private static String fixedSalt(String basesalt,String salt){
        return DigestUtil.md5Hex(salt + UUID.randomUUID().toString() + DateUtil.now().toString() + basesalt);
    }

    private static String password(String salt,String basepwd){
        return  DigestUtil.md5Hex(basepwd + salt + basepwd);
    }

    public static AccountEntity newPassword(String pwd, String basesalt){
        //加盐
        int i = RandomUtil.randomInt(8);
        String salt = AccountUtils.fixedSalt(basesalt, String.valueOf(i));
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setPasswordSalt(salt);
        String password = AccountUtils.password(salt, pwd);
        accountEntity.setPassword(password);
        return accountEntity;
    }

    public static String checkPassword(String pwd, String salt){
        return  AccountUtils.password(salt, pwd);
    }
}
