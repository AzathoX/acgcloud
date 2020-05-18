package org.nrocn.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.nrocn.user.dao.UserRepository;
import org.nrocn.user.model.AccountDomain;
import org.nrocn.user.services.AccountDomainService;
import org.nrocn.user.services.JpaUserRespositoryServices;
import org.nrocn.user.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringBootNrocnUserApplicationTests {


	@Autowired
	AccountDomainService accountDomainService;

	@Autowired
	JpaUserRespositoryServices jpaUserRespositoryServices;

	@Autowired
	UserRepository userRepository;

	@Test
	void contextLoads() {
		jpaUserRespositoryServices.registry("12313123456","8545114992@qq.com","12345678");
	}


	@Test
	void findUserNameAndMailTest(){

	}
}
