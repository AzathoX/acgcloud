package org.acgcloud.optrxsys;

import lombok.SneakyThrows;

import org.acgcloud.optrxsys.dto.OptrxRequest;
import org.acgcloud.optrxsys.office.IOffices;
import org.acgcloud.optrxsys.office.docx.Word;
import org.acgcloud.optrxsys.office.ppt.PowerPoint;
import org.acgcloud.optrxsys.utils.BaseOfficeUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;
import org.nrocn.lib.utils.BaseIOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.acgcloud.optrxsys.controller.MainSvController;
import org.acgcloud.optrxsys.services.FileOpenServices;
import org.acgcloud.optrxsys.services.IRxFileOptStragy;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.util.List;


@SpringBootTest
class OptrxsysApplicationTests {

	public static final String PATH = "/Volumes/project/java/acgcloud-parent/temp/";

	@Autowired
	private FileOpenServices fileOpenServices;

	@Autowired
	private MainSvController mainSvController;

	@Test
	void classPath(){
//		mainSvController.printFilePath();
	}


	@SneakyThrows
	@Test
	void downloadTest(){

	}


	@SneakyThrows
	@Test
	void xlsTest(){
		OptrxRequest optxRequest = new OptrxRequest();
		optxRequest.setPath(PATH + "/41.doc");
		optxRequest.setBufferPath(PATH);
		File file = new File(optxRequest.getPath());
		optxRequest.setFile(file);
		optxRequest.setOpt("word");
		IRxFileOptStragy parse = fileOpenServices.parse(optxRequest);
		InputStream open = parse.open();
	}



	@SneakyThrows
	@Test
	void pptTest(){

	}

	@Test
	void parseFile(){
		IOffices iOffices = BaseOfficeUtils.officesFactory(PATH + "41.doc");
		iOffices.toHtml(PATH);
	}

	@SneakyThrows
	@Test
	void wordToHtml(){
		Word word = (Word) Word.newInstance(PATH + "41.doc");
		word.toHtml(PATH);
	}


	@Test
	@SneakyThrows
	void docToHtml(){

	}

	public static boolean checkFile(File file) {
		boolean isppt = false;
		String filename = file.getName();
		String suffixname = null;
		if (filename != null && filename.indexOf(".") != -1) {
			suffixname = filename.substring(filename.indexOf("."));
			if (suffixname.equals(".ppt")) {
				isppt = true;
			}
			return isppt;
		} else {
			return isppt;
		}
	}

	private static void fileExists(String path) {
		File file = new File(path);
		if (!file.exists()){
			file.mkdirs();
		}
	}

	@Test
	void powerpointToHtml(){
		PowerPoint ppt = (PowerPoint) PowerPoint.newInstance(PATH + "/123.pptx");
		ppt.toHtml(PATH);
	}



	@SneakyThrows
	@Test
	void contextLoads() {

	}


	void pptToHtml(){

	}


	@SneakyThrows
	@Test
	void excelToHtml(){
	}


}
