package org.nrocn.lib.baseobj;

import org.nrocn.lib.utils.BaseFileUtils;

import java.io.File;

public class RelativeFileObj {


    public static final Boolean IS_FILE = true;

    public static  final Boolean IS_DIRECTORY = false;


    /**
     * 是否为文件
     */
    private  Boolean isFile;


    /**
     * 相对文件路径
     */
    private String path;


    /**
     * 文件后缀
     * @return
     */
    private String suffix;




    public Boolean getFile() {
        return isFile;
    }

    public void setFile(Boolean file) {
        isFile = file;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public RelativeFileObj() {
    }

    public RelativeFileObj(Boolean isFile, String path) {
        this.isFile = isFile;
        this.path = path;
        this.suffix =  BaseFileUtils.getFileSuffix(path,false);
    }

    public RelativeFileObj( String path ,Boolean isFile) {
        this.isFile = isFile;
        this.path = path;
        this.suffix =  BaseFileUtils.getFileSuffix(path,false);
    }

    @Override
    public String toString() {
        return "RelativeFileObj{" +
                "isFile=" + isFile +
                ", path='" + path + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}
