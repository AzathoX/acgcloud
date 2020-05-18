package org.acgcloud.dmsys;

import org.acgcloud.dmsys.config.DmsysConfig;
import org.acgcloud.dmsys.dao.LogicCatalogEntityRepository;
import org.acgcloud.dmsys.dao.PrartitionEntityRepository;
import org.acgcloud.dmsys.dto.FileDomainRequest;
import org.acgcloud.dmsys.model.CloudFlodlerDomain;
import org.acgcloud.dmsys.model.TreeCloudFlodlerDomain;
import org.acgcloud.dmsys.services.CloudFlodlerDomainService;
import org.acgcloud.dmsys.services.PrartitionDomainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.acgcloud.dmsys.controller.MainSvController;
import org.acgcloud.dmsys.controller.WorkStationSvController;
import org.acgcloud.dmsys.entity.LogicCatalogEntity;

import java.util.Calendar;
import java.util.List;

@SpringBootTest
class AcgCloudDmsysApplicationTests {

	@Autowired
	private PrartitionDomainService prartitionDomainService;



	@Autowired
	private PrartitionEntityRepository prartitionEntityRepository;


	@Autowired
	private LogicCatalogEntityRepository logicCatalogEntityRepository;


	@Autowired
	private MainSvController mainSvController;

	@Autowired
	private DmsysConfig dmsysConfig;

	@Autowired
	private WorkStationSvController workStationSvController;


	@Autowired
	private CloudFlodlerDomainService cloudFlodlerDomainService;


	@Test
	void jpaTest(){
		LogicCatalogEntity one = logicCatalogEntityRepository.findByCatalogHashName("7ef7aab4adf451e263cb905a92f34c63");
		System.out.println(one);
	}

	@Test
	void contextLoads() {
		FileDomainRequest fileDomainRequest = new FileDomainRequest();
		fileDomainRequest.setVpName("/test");
		fileDomainRequest.setIsFile(true);
		fileDomainRequest.setFilesys("");
		System.out.println(mainSvController.praritionApplyFor(fileDomainRequest));
	}

	@Test
	void createCatalog(){
			FileDomainRequest fileDomainRequest = new FileDomainRequest();
			fileDomainRequest.setVpHashName("ba66c314a4cbc0907c9afa438a1def1b");
			fileDomainRequest.setIsFile(true);
			fileDomainRequest.setCatalogName("逻辑目录");
			fileDomainRequest.setFilesys("");
		    System.out.println(mainSvController.catalogApplyFor(fileDomainRequest));
	}

	@Test
	void testFileAdd(){
		FileDomainRequest fileDomainRequest = new FileDomainRequest();
		fileDomainRequest.setCatalogHashName("ccd136474e6d4f45e276983336fb9451");
		fileDomainRequest.setName("测试.txt");
		fileDomainRequest.setParentId(1L);
		fileDomainRequest.setIsFile(true);
		fileDomainRequest.setIsRoot(false);
		mainSvController.fileAdd(fileDomainRequest);
	}


	@Test
	void testFile(){
		List<CloudFlodlerDomain> list = cloudFlodlerDomainService.list();
//		list.forEach(System.out::println);
	}

	@Test
	void getwkstation(){
		TreeCloudFlodlerDomain treeCloudFlodlerDomain = workStationSvController.cloudFolderTree(873L);
		System.out.println(treeCloudFlodlerDomain);
	}

//	@Test
	void batchCreateFile(){


	}
//
//	@Test
	void createSubFile(){


	}

	@Test
	void treeTest(){
		long current = System.currentTimeMillis();
		System.out.println(workStationSvController.myWorkStationByTreeMap(2L));
		long userTime = System.currentTimeMillis() - current;
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(userTime);
		int i = instance.get(Calendar.MINUTE);
		int se = instance.get(Calendar.SECOND);
		System.out.println(se);
	}


	@Test
	void getList(){
		System.out.println(workStationSvController.queryFileByParentId("874", 1));
	}


	@Test
	void getFileBroadwise(){
		workStationSvController.doMyWorkStationByTreeMap(873L);
	}
}
