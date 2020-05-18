package org.nrocn.lib.baseobj;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.*;

/**
 * @author Administrator
 */
public class Directory  extends BaseDomain  {


    /**
     * 映射文件
     */
    private File file;

    /**
     * 顶级目录
     */
    private Directory topDiretory;


    private  String  directoryName;

    /**
     * 相对路径
     */
    private String capacity;




    /**
     * 完全限定名与目录类的映射
     */
    private Map<String,Directory> pthNmDirectroyMaps;


    /**
     * id绑定映射
     */
    private Map<Integer,Directory> idDiretroyMaps;
    /**
     * 目录中的目录和名字
     */
    private List<Directory> directorieList;

    private List<String> directorieNames;



    /**
     * 目录中的文件列表和名字
     */
    private List<File> fileList;

    private List<String> fileNames;


    public List<Directory> getDirectorieList() {
        return directorieList;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public List<String> getDirectorieNames() {
        return directorieNames;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public Directory(File file) throws FileNotFoundException {
        this.file = file;
        this.directoryName = file.getName();
    }

    public  Directory(File file,Directory directory) throws FileNotFoundException {
        this.topDiretory = directory;
        this.file = file;
        this.directoryName = file.getName();
    }

    public void doInit() throws FileNotFoundException {
        this.topDiretory = this;
        init();
        doLoadTree(this.file);
    }


    public void doSubInit() throws FileNotFoundException {
        init();
        this.topDiretory.pthNmDirectroyMaps.put(this.file.getAbsolutePath(),this);
        this.id = this.topDiretory.pthNmDirectroyMaps.size();
        this.topDiretory.idDiretroyMaps.put(this.id,this);
        doLoadTree(this.file);
    }

    //初始化方法
    private void init() throws FileNotFoundException {
        this.fileList = new ArrayList<>();
        this.directorieList = new ArrayList<>();
        this.pthNmDirectroyMaps = new HashMap<>();
        this.idDiretroyMaps = new HashMap<>();
        this.fileNames = new ArrayList<>();
        this.directorieNames = new ArrayList<>();
    }

    private  void doLoadTree(File file) throws FileNotFoundException {
        if(!file.exists()){
            throw new FileNotFoundException("文件不存在");
        }
        /**
         * 如果是文件就不进行递归,否则就创建一个新的文件夹对象
         */
        File[] files = file.listFiles();
        for (File f : files) {
            if(!f.isDirectory()){
                //添加文件对象
                this.fileList.add(f);
                this.fileNames.add(f.getName());
            }
            else{
                //新建一个目录文件
                Directory subDirectory = new Directory(f, this.topDiretory);
                subDirectory.doSubInit();
                this.directorieList.add(subDirectory);
                this.directorieNames.add(f.getName());


            }
        }
    }




}
