package org.acgcloud.optrxsys.office.xls;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.acgcloud.optrxsys.office.AbstractMicrosoftOffices;
import org.acgcloud.optrxsys.office.IOffices;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.nrocn.lib.utils.BaseFileUtils;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URI;
import java.util.List;


@Setter
@Getter
public class Excel extends AbstractMicrosoftOffices implements IOffices {



    public Excel(String pathname) {
        super(pathname);
    }

    public Excel(String parent, String child) {
        super(parent, child);
    }

    public Excel(File parent, String child) {
        super(parent, child);
    }

    public Excel(URI uri) {
        super(uri);
    }


    public static final IOffices  newInstance(String pathName){
        Excel office = new Excel(pathName);
        //check suffix by name
        String fileSuffix = BaseFileUtils.getFileSuffix(pathName, false);
        for (String suffix : EXCEL_SUFFIX) {
            if(suffix.equals(fileSuffix)){
                office.setSuffix(fileSuffix);
                if(fileSuffix.indexOf("x") > 0){
                    office.setVersion("2007");
                }
                else{
                    office.setVersion("2003");
                }
                return  office;
            }
        }
        return  null;
    }


    @SneakyThrows
    @Override
    protected InputStream converter2007PlusToHtml(String target) {
        // 1) 加载XWPFDocument及文件
        InputStream in = new FileInputStream(this);
        XWPFDocument document = new XWPFDocument(in);
        // 2) 实例化XHTML内容(这里将会把图片等文件放到生成的"word/media"目录)
        File imageFolderFile = new File(target + "/img");
        XHTMLOptions options = XHTMLOptions.create().URIResolver(
                new FileURIResolver(imageFolderFile));
        options.setExtractor(new FileImageExtractor(imageFolderFile));
        String name = BaseFileUtils.getFileNameNoSuffix(this.getName());
        File html = new File(target + "/" + name + ".html");
        OutputStream out = new FileOutputStream(html);
        XHTMLConverter.getInstance().convert(document, out, options);
        FileInputStream fileInputStream = new FileInputStream(html);
        return null;
    }

    @SneakyThrows
    @Override
    public InputStream converter2003ToHtml(String target){
        File imageFolderFile = new File(target + "/img");
        InputStream input=new FileInputStream(this);
        HSSFWorkbook excelBook=new HSSFWorkbook(input);
        ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter (DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument() );
        excelToHtmlConverter.processWorkbook(excelBook);
        List pics = excelBook.getAllPictures();
        if (pics != null) {
            for (int i = 0; i < pics.size(); i++) {
                Picture pic = (Picture) pics.get (i);
                try {
                    pic.writeImageContent (new FileOutputStream(imageFolderFile.getAbsolutePath() + "/" + pic.suggestFullFileName() ) );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        Document htmlDocument =excelToHtmlConverter.getDocument();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource (htmlDocument);
        StreamResult streamResult = new StreamResult (outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty (OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty (OutputKeys.INDENT, "yes");
        serializer.setOutputProperty (OutputKeys.METHOD, "html");
        serializer.transform (domSource, streamResult);
        outStream.close();
        String content = new String (outStream.toByteArray() );
        File html = new File(target + "/"+ BaseFileUtils.getFileNameNoSuffix(this.getName()+".html"));
        FileUtils.writeStringToFile(html , content, "utf-8");
        return new FileInputStream(html);
    }



}
