package org.nrocn.lib.utils;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public abstract class BaseIOUtils {


    /**
     * 文件下载http相应输出流
     * @param response
     * @param writeTo
     * @param isText
     * @return
     * @throws IOException
     */
    public static long httpDownloadFileResponse(File file,HttpServletResponse writeTo) throws IOException {
        InputStream fileIs = new FileInputStream(file);
        writeTo.reset();
        writeTo.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(file.getName(),"UTF-8"));
        writeTo.addHeader("Content-Length","" + file.length() );
        writeTo.addHeader("Content-Type","application/octet-stream");
        BaseIOUtils.copy(fileIs,writeTo.getOutputStream());
        writeTo.flushBuffer();
        return file.length();
    }


    /**
     * 文件下载http相应输出流
     * @param response
     * @param writeTo
     * @param isText
     * @return
     * @throws IOException
     */
    public static long httpDownloadFileResponse(File file,String fileName,HttpServletResponse writeTo) throws IOException {
        InputStream fileIs = new FileInputStream(file);
        writeTo.reset();
        writeTo.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        writeTo.addHeader("Content-Length","" + file.length() );
        writeTo.addHeader("Content-Type","application/octet-stream");
        BaseIOUtils.copy(fileIs,writeTo.getOutputStream());
        writeTo.flushBuffer();
        return file.length();
    }

    /**
     * 流方式写出文件
     * @param in
     * @param out
     * @return
     */
    public static String httpHtmlWriteResponse(HttpServletResponse writeTo , String content) throws IOException {
        writeTo.setContentType("text/html;charset=utf-8");
        writeTo.setCharacterEncoding("UTF-8");
        PrintWriter writer = writeTo.getWriter();
        writer.println(content);
        writeTo.flushBuffer();
        return content;
    }




    /**
     * 点对点输出文件封装
     * @param in
     * @param out
     * @return
     */
    public static long copy(InputStream in, OutputStream out) {
        return copy(in, out, 8192);
    }

    /**
     * 点对点输入输出流
     * @param in
     * @param out
     * @param bufferSize
     * @return
     */
    public static long copy(InputStream in, OutputStream out, int bufferSize) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
        if (bufferSize <= 0) {
            bufferSize = 8192;
        }
        //文件复制
        byte[] buffer = new byte[bufferSize];
        long size = 0L;
        int readSize;
        try {
            while((readSize = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, readSize);
                size += (long)readSize;
                bufferedOutputStream.flush();
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return size;
    }
}
