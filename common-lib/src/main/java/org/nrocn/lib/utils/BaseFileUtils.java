package org.nrocn.lib.utils;

import org.nrocn.lib.baseobj.Directory;
import org.nrocn.lib.baseobj.RelativeFileObj;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseFileUtils {

    private static final Integer BUFFER_SIZE = 1024;

    /**
     * 判断文件是否存在 存在返回文件
     * @param path 路径
     * @return 文件
     */
    public final static File find(String path){
        assert  path != null && path.length() > 0 : "路径不合法";
        File file  = new File(path);
        return  find(file);
    }

    public final static File find(File file){
        if(!file.exists()){
            return null;
        }
        return  file;
    }


    /**
     * 创建文件夹，当路径不存在的适合可以创建路径
     * @param path
     * @return
     */
    public final static File mkdir(String path){
        assert  path != null && path.length() > 0 : "路径不合法";
        //按照/拆成数组
        // C://test = C:/test
        path =  path.replace("//","/");
        // /test
        String[] fileArray = path.split("/");
        //创建文件
        String basePath = "/";
        File file = null;
        for (String fileName : fileArray) {
            if(fileName.length() == 0){
                continue;
            }
            basePath += ("/" + fileName);
            file = new File(basePath);
            if(!file.exists()){
                file.mkdir();
            }
        }
        return file;
    }

    /**
     * 返回文件后缀
     * @param file 源文件
     * @param hasDot 有没有点 .docx？docx
     * @return
     */
    public final static String getFileSuffix(String file,boolean hasDot){
        //返回最后一个的下标
        int liof = file.lastIndexOf(".");
        //剪切从第0个开始到liof的下标
        return file.substring(hasDot?liof:liof+1,file.length());
    }
    /**
     * 返回文件后缀
     * @param file 源文件
     * @param hasDot 有没有点 .docx？docx
     * @return
     */
    public final static String getFileSuffix(File file,boolean hasDot){
        //获取文件名
        String name = file.getName();
        //返回最后一个的下标
        int liof = name.lastIndexOf(".");
        //剪切从第0个开始到liof的下标
        return name.substring(hasDot?liof:liof+1,name.length());
    }


    /**
     * 返回单纯的文件名
     */
    public final static String getFileNameNoSuffix(String file){
        //返回最后一个的下标
        int liof = file.lastIndexOf(".");
        //剪切从第0个开始到liof的下标
        return file.substring(0,liof);
    }

    /**
     * 返回单纯的文件名
     */
    public final static String getFileNameNoSuffix(File file){
        //获取文件名
        String name = file.getName();
        //返回最后一个的下标
        int liof = name.lastIndexOf(".");
        //剪切从第0个开始到liof的下标
        return name.substring(0,liof);
    }



    public final static List<RelativeFileObj> ls(String path) throws FileNotFoundException {
        List<RelativeFileObj> fileList = new ArrayList<>();
        ls(new File(path),fileList);
        return fileList;
    }


    /**
     * 返回一个list的文件列表
     * @param path
     * @return
     */
    public final static List<File> tree(String path) throws FileNotFoundException {
          List<File> fileList = new ArrayList<>();
          tree(path,fileList);
          return  fileList;
    }


    /**
     * 返回一个list的文件列表
     * @param path
     * @return
     */
    public final static List<File> tree(File file) throws FileNotFoundException {
        List<File> fileList = new ArrayList<>();
        tree(file,fileList);
        return  fileList;
    }


    /**
     * 返回一个list的文件列表
     * @param path
     * @return
     */
    public final static void tree(String path,List<File> filelist) throws FileNotFoundException {
        File file = new File(path);
        if(!file.exists()){
            throw new FileNotFoundException("文件不存在");
        }
        tree(file,filelist);
    }



    public final static long cp(String source,String target){
        return cp(source,target,BUFFER_SIZE);
    }

    public final static long cp(File source,File target){
        return cp(source,target,BUFFER_SIZE);
    }


    //复制文件
    public final static long cp(String source,String target,Integer buffreSize)  {
        try {
            BufferedInputStream sourceIO = new BufferedInputStream(new FileInputStream(source));
            BufferedOutputStream targetIO = new BufferedOutputStream(new FileOutputStream(target));
            return BaseIOUtils.copy(sourceIO, targetIO, buffreSize);
        }catch (FileNotFoundException e){
            System.out.println("文件为空");
        }
        return 0L;
    }


    //复制文件
    public final static long cp(File source,File target,Integer bufferSize) {
        try{
            BufferedInputStream sourceIO = new BufferedInputStream(new FileInputStream(source));
            BufferedOutputStream targetIO = new BufferedOutputStream(new FileOutputStream(target));
            return  BaseIOUtils.copy(sourceIO,targetIO,bufferSize);
        }catch (FileNotFoundException e){
            System.out.println("文件为空");
        }
        return 0L;
    }


    /**
     * 返回一个文件字符串
     */
    public final static void ls(File file, List<RelativeFileObj> fileList) throws FileNotFoundException {
        if(!file.exists()){
            throw new FileNotFoundException("文件不存在");
        }
        File[] files = file.listFiles();
        //遍历文件集合
        for (File f : files) {
            String filepath = f.getPath();
            filepath = filepath.replace(file.getPath(),"");
            fileList.add(new RelativeFileObj(filepath,f.isFile()));

        }
    }

    /**
     * 返回一个list的文件列表
     * @param path
     * @return
     */
    public final static void tree(File file,List<File> filelist) throws FileNotFoundException {
        if(!file.exists()){
            throw new FileNotFoundException("文件不存在");
        }
        if (file.isFile()) {
            filelist.add(file);
            return;
        }
        File[] files = file.listFiles();
        //遍历文件集合
        for (File f : files) {
            tree(f,filelist);

        }
    }


    /**
     * 返回一个文件目录树
     * a
     *   b
     *      1
     *      2
     *   1
     *   2
     *
     *
     */
    public final static Directory catalog(File file){
        try {
            Directory directory = new Directory(file);
            directory.doInit();
            return directory;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
    }

    //根据名字获取文件目录树
    public final static Directory catalog(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if(!file.exists()){
            throw new FileNotFoundException("文件不存在");
        }
        return BaseFileUtils.catalog(file);
    }
}
