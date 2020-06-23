package org.acgcloud.optrxsys.office.ppt;

import lombok.SneakyThrows;
import org.acgcloud.optrxsys.AbstractConstant;
import org.acgcloud.optrxsys.office.AbstractMicrosoftOffices;
import org.acgcloud.optrxsys.office.IOffices;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hslf.usermodel.HSLFAutoShape;
import org.apache.poi.hslf.usermodel.HSLFTable;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.sl.usermodel.SlideShowFactory;
import org.apache.poi.xslf.usermodel.*;
import org.nrocn.lib.utils.BaseFileUtils;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.util.List;


public class PowerPoint extends AbstractMicrosoftOffices implements IOffices {



    public static final IOffices newInstance(String pathName){
        PowerPoint office = new PowerPoint(pathName);
        //check suffix by name
        String fileSuffix = BaseFileUtils.getFileSuffix(pathName, false);
        for (String suffix : POWERPOINT_SUFFIX) {
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

    public PowerPoint(String pathname) {
        super(pathname);
    }

    public PowerPoint(String parent, String child) {
        super(parent, child);
    }

    public PowerPoint(File parent, String child) {
        super(parent, child);
    }

    public PowerPoint(URI uri) {
        super(uri);
    }

    @SneakyThrows
    @Override
    protected InputStream converter2007PlusToHtml(String target) {
        File imageFile = new File(target + "/img");
        FileInputStream is = null;
        SlideShow ppt;
        try {
            is = new FileInputStream(this);
            ppt = SlideShowFactory.create(is);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        Dimension pgsize = ppt.getPageSize();
        List<XSLFSlide> pptPageXSLFSLiseList=ppt.getSlides();
        FileOutputStream out=null;
        String imghtml="";
        for (int i = 0; i < pptPageXSLFSLiseList.size(); i++) {
            for(XSLFShape shape : pptPageXSLFSLiseList.get(i).getShapes()){
                //设置文字字体
                if(shape instanceof XSLFTextShape) {
                    XSLFTextShape tsh = (XSLFTextShape)shape;
                    for(XSLFTextParagraph p : tsh){
                        for(XSLFTextRun r : p){
                            r.setFontFamily("宋体");
                        }
                    }
                    //设置表格字体
                }else if(shape instanceof XSLFTable){
                    XSLFTable table = (XSLFTable)shape;
                    int rowSize = table.getNumberOfRows();
                    int columnSize = table.getNumberOfColumns();
                    for (int j = 0; j < rowSize; j++) {
                        for (int k = 0; k < columnSize; k++) {
                            for (int l =0;l <  table.getCell(j, k).getTextParagraphs().size();l++){
                                XSLFTextParagraph xslfTextRuns = table.getCell(j, k).getTextParagraphs().get(l);
                                for (int m = 0;m < xslfTextRuns.getTextRuns().size();m++){
                                    xslfTextRuns.getTextRuns().get(m).setFontFamily("宋体");
                                }
                            }
                        }
                    }
                }
            }
            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
            pptPageXSLFSLiseList.get(i).draw(graphics);
            //设置图片存放位置
            try {
                out = new FileOutputStream(imageFile.getAbsolutePath()+ "/"+ BaseFileUtils.getFileNameNoSuffix(this.getName()) + "_" + (i + 1) + ".jpeg");
                javax.imageio.ImageIO.write(img, "jpeg", out);
            } catch (java.io.IOException e) {
                try {
                    out.close();
                } catch (IOException e1) {
                    System.out.println(e.getMessage());
                }
                return null;
            }
            //图片在html加载路径
            String imgs= AbstractConstant.TEMP + "/img/"+ BaseFileUtils.getFileNameNoSuffix(this.getName()) + "_" + (i + 1) + ".jpeg";
            imghtml+="<img src=\'"+imgs+"\' style=\'width:1200px;height:830px;vertical-align:text-bottom;\'><br><br><br><br>";
        }

        DOMSource domSource = new DOMSource();
        StreamResult streamResult = new StreamResult(out);
        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
            return null;
        }finally {
            try {
                out.close();
            } catch (java.io.IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }

        File html = new File(target + "/" + BaseFileUtils.getFileNameNoSuffix(this.getName()) + ".html");
        String ppthtml="<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>"+imghtml+"</body></html>";
        try {
            FileUtils.writeStringToFile(html, ppthtml, "utf-8");
        } catch (Exception e) {
            return null;
        }
        return new FileInputStream(html);
    }

    @SneakyThrows
    @Override
    protected InputStream converter2003ToHtml(String target) {
        File imageFile = new File(target + "/img");
        // 读入PPT文件
        FileInputStream is = null;
        SlideShow ppt;
        try {
            is = new FileInputStream(this);
            ppt = SlideShowFactory.create(is);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        Dimension pgsize = ppt.getPageSize();
        java.util.List<Slide> slide = ppt.getSlides();
        FileOutputStream out =null;
        String imghtml="";
        for (int i = 0; i < slide.size(); i++) {
            for (Object o : slide.get(i).getShapes()) {
                if(o instanceof HSLFAutoShape) {
                    HSLFAutoShape shapes = (HSLFAutoShape)o;
                    List<HSLFTextParagraph> list = shapes.getTextParagraphs();
                    for (HSLFTextParagraph hslfTextRuns : list) {
                        for (HSLFTextRun hslfTextRun : hslfTextRuns.getTextRuns()) {
                            hslfTextRun.setFontFamily("宋体");
                        }
                    }
                }else if(o instanceof HSLFTable){
                    HSLFTable hslfTable = (HSLFTable) o;
                    int rowSize = hslfTable.getNumberOfRows();
                    int columnSize = hslfTable.getNumberOfColumns();
                    for (int j = 0; j < rowSize; j++) {
                        for (int k = 0; k < columnSize; k++) {
                            for (int l =0;l <  hslfTable.getCell(j, k).getTextParagraphs().size();l++){
                                HSLFTextParagraph hslfTextRuns = hslfTable.getCell(j, k).getTextParagraphs().get(l);
                                for (int m = 0;m < hslfTextRuns.getTextRuns().size();m++){
                                    HSLFTextRun textRun = hslfTextRuns.getTextRuns().get(m);
                                    //todo 设置字体失败，输出html依旧会乱码
                                    textRun.setFontFamily("宋体");
                                }
                            }
                        }
                    }
                }
            }
            BufferedImage img = new BufferedImage(pgsize.width,pgsize.height, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics = img.createGraphics();
            graphics.setPaint(Color.BLUE);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
            slide.get(i).draw(graphics);
            // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径与源文件同一个目录
            try {
                out= new FileOutputStream(imageFile.getAbsolutePath() + "/"+ BaseFileUtils.getFileNameNoSuffix(this.getName())+"_"+(i + 1) + ".jpeg");
                javax.imageio.ImageIO.write(img, "jpeg", out);
            } catch (IOException e) {

                try {
                    out.close();
                } catch (IOException e1) {

                }

            }
            //图片在html加载路径
            String imgs=AbstractConstant.TEMP  + "/img/" + BaseFileUtils.getFileNameNoSuffix(this.getName()) +"_"+(i + 1) + ".jpeg";
            imghtml+="<img src=\'"+imgs+"\' style=\'width:1200px;height:830px;vertical-align:text-bottom;\'><br><br><br><br>";

        }
        DOMSource domSource = new DOMSource();
        StreamResult streamResult = new StreamResult(out);
        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        String ppthtml="<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>"+imghtml+"</body></html>";
        File html = new File(target + "/" + BaseFileUtils.getFileNameNoSuffix(this.getName()) + ".html");
        try {
            FileUtils.writeStringToFile(html, ppthtml, "utf-8");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new FileInputStream(html);
    }
}
