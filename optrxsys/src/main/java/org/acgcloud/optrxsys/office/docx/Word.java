package org.acgcloud.optrxsys.office.docx;



import lombok.SneakyThrows;
import org.acgcloud.optrxsys.AbstractConstant;
import org.acgcloud.optrxsys.office.AbstractMicrosoftOffices;
import org.acgcloud.optrxsys.office.IOffices;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
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



public class Word extends AbstractMicrosoftOffices implements IOffices {

    public Word(String pathname) {
        super(pathname);
    }

    public Word(String parent, String child) {
        super(parent, child);
    }

    public Word(File parent, String child) {
        super(parent, child);
    }

    public Word(URI uri) {
        super(uri);
    }

    //static factory method
    public final static  IOffices newInstance(String pathName){
        //create object
        Word office = new Word(pathName);
        //check suffix by name
        String fileSuffix = BaseFileUtils.getFileSuffix(pathName, false);
        for (String suffix : WORD_SUFFIX) {
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



    @Override
    @SneakyThrows
    protected InputStream converter2003ToHtml(String target){
        File imageFolderFile = new File(target + "/img");
        InputStream input = new FileInputStream(this);
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            @Override
            public String savePicture(byte[] content, PictureType pictureType,
                                      String suggestedName, float widthInches, float heightInches) {
                return AbstractConstant.TEMP + "/img/" + suggestedName;
            }
        });
        wordToHtmlConverter.processDocument(wordDocument);
        List pics = wordDocument.getPicturesTable().getAllPictures();

        if (pics != null) {
            for (int i = 0; i < pics.size(); i++) {
                Picture pic = (Picture) pics.get(i);
                try {
                    pic.writeImageContent(new FileOutputStream(imageFolderFile.getAbsolutePath() + "/" + pic.suggestFullFileName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
        String content = new String(outStream.toByteArray());
        String name = BaseFileUtils.getFileNameNoSuffix(this.getName());
        File html = new File(target + "/" + name + ".html");
        FileUtils.writeStringToFile(html, content, "utf-8");
        return new FileInputStream(html);
    }

    @Override
    @SneakyThrows
    protected InputStream converter2007PlusToHtml(String target){
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
        return  fileInputStream;
    }





}
