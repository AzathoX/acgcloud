package org.acgcloud.account;

import org.acgcloud.account.controller.MainSvController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountApplicationTests {

	@Autowired
	private MainSvController mainSvController;



	@Test
	void contextLoads() {
//		System.out.println(mainSvController.login("hlloworld", "1345678"));
	}

}
