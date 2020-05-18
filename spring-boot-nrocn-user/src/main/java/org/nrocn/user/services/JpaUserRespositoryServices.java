package org.nrocn.user.services;

import org.nrocn.user.entity.UserEntity;
import org.nrocn.user.model.UserDomain;

public interface JpaUserRespositoryServices {

    UserEntity info(String username);

    UserEntity updatePassword(UserEntity userEntity);

    UserEntity updateInfo(UserEntity userEntity);

    UserDomain registry(String account, String mail, String password);

    UserEntity login(String account, String password);
}
