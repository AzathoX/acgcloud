package org.nrocn.user.dao;

import org.nrocn.user.entity.AccountEntity;
import org.nrocn.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface AccountRepository extends JpaRepository<AccountEntity,Integer> {

      UserEntity findByUsername(String username);

      @Query(nativeQuery = true,value = "(select user.id, account.username,account.tel,account.mail,account.password from tb_user as user " +
              "inner join tb_account as account " +
              "on user.account_id = account.id " +
              "where account.username = :account) " +
              "union  all" +
              "(select user.id, account.username,account.tel,account.mail,account.password from tb_user as user " +
              "inner join tb_account as account " +
              "on user.account_id = account.id " +
              "where account.tel = :account ) " +
              "union  all " +
              "(select user.id, account.username,account.tel,account.mail,account.password from tb_user as user " +
              "inner join tb_account as account " +
              "on user.account_id = account.id " +
              "where account.mail = :account) ")
      List<Map<String,Object>> findByAccount(@Param("account") String account);



}
