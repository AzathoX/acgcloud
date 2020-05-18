package org.nrocn.user.dao;

import org.nrocn.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

      UserEntity findByUsername(String username);


}
