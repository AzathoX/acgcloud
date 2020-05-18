package org.acgcloud.filesys;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.acgcloud.filesys.controller.MainSvController;

@SpringBootTest
class FilesysApplicationTests {

	@Autowired
	MainSvController mainSvController;

	@Test
	void contextLoads() {
	}

}
