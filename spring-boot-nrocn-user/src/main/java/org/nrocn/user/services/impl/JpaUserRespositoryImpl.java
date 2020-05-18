package org.nrocn.user.services.impl;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.nrocn.user.dao.AccountRepository;
import org.nrocn.user.dao.UserRepository;
import org.nrocn.user.entity.AccountEntity;
import org.nrocn.user.entity.UserEntity;
import org.nrocn.user.model.AccountDomain;
import org.nrocn.user.model.UserDomain;
import org.nrocn.user.services.AccountDomainService;
import org.nrocn.user.services.JpaUserRespositoryServices;
import org.nrocn.user.services.UserDomainService;
import org.nrocn.user.utils.AccountUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class JpaUserRespositoryImpl implements JpaUserRespositoryServices {


    private static final String BASE_SALT="nacoqiang001235346afbghvjf";


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountDomainService accountDomainService;

    @Autowired
    private UserDomainService userDomainService;


    @Override
    public UserEntity info(String username){
        UserEntity userEntity = userRepository.findByUsername(username);
        return  userEntity;
    }


    @Override
    public UserEntity updatePassword(UserEntity userEntity){
        AccountEntity accountEntity = AccountUtils.newPassword(userEntity.getAccountEntity().getPassword(), BASE_SALT);
        AccountEntity account = userEntity.getAccountEntity();
        account.setPassword(accountEntity.getPassword());
        account.setPasswordSalt(accountEntity.getPasswordSalt());
        return updateInfo(userEntity);

    }

    @Override
    public UserEntity updateInfo(UserEntity userEntity){
        UserEntity info = info(userEntity.getUsername());
        userEntity.setId(info.getId());
        UserEntity userEntity1 = userRepository.saveAndFlush(info);
        return userEntity1;
    }

    @Override
    @Transactional
    public UserDomain registry(String account,String mail,String password){
        UserEntity userEntity = userRepository.findByUsername(account);
        if(!Objects.isNull(userEntity)){
            return null;
        }
        AccountEntity accountEntity = AccountUtils.newPassword(password, BASE_SALT);
        accountEntity.setMail(mail);
        accountEntity.setUsername(account);
        AccountDomain accountDomain = new AccountDomain();
        BeanUtils.copyProperties(accountEntity,accountDomain);
        accountDomainService.save(accountDomain);
        accountDomain = accountDomainService.getOne(new QueryWrapper<AccountDomain>().eq(AccountDomain.COL_USERNAME, account));
        UserDomain userDomain = new UserDomain();
        userDomain.setAccountId(accountDomain.getId());
        userDomain.setUsername(account);
        userDomainService.save(userDomain);
        return  userDomain;
    }

    @Override
    public UserEntity login(String account, String password){
        List<Map<String, Object>> accounts = accountRepository.findByAccount(account);
        if(ObjectUtils.isEmpty(accounts) && accounts.size() == 0){
            return null;
        }
        UserEntity userEntity = userRepository.findByUsername(account);
        AccountEntity accountEntity = userEntity.getAccountEntity();
        String passwordSalt = accountEntity.getPasswordSalt();
        String pwd = accountEntity.getPassword();
        password = AccountUtils.checkPassword(password,passwordSalt);
        if(!password.equals(pwd)){
            return new UserEntity();
        }
        return userEntity;
    }

}
