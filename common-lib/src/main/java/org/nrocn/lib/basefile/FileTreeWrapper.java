package org.nrocn.lib.basefile;


import org.nrocn.lib.baseobj.Directory;
import org.nrocn.lib.utils.BaseFileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


public class FileTreeWrapper {

    private String treePath;

    private List<File> fileTree;

    private Directory directory;

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public List<File> getFileTree() {
        return this.fileTree;
    }

    public void setFileTree(List<File> fileTree) {
        this.fileTree = fileTree;
    }


    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public FileTreeWrapper() {
    }

    public FileTreeWrapper(String treePath, List<File> fileTree) {
        this.treePath = treePath;
        this.fileTree = fileTree;
    }

    public void refresh(String path){
        this.treePath = path;
        refresh();
    }

    public void refresh(){
        try {
           this.fileTree =  BaseFileUtils.tree(this.treePath);
           this.directory = BaseFileUtils.catalog(this.treePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "FileTreeWrapper{" +
                "treePath='" + treePath + '\'' +
                ", fileTree=" + fileTree +
                '}';
    }
}
