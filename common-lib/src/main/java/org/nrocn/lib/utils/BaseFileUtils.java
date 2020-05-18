package org.nrocn.lib.utils;

import org.nrocn.lib.baseobj.Directory;
import org.nrocn.lib.baseobj.RelativeFileObj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseFileUtils {


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
