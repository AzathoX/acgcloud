package org.acgcloud.filesys.services;

import org.nrocn.lib.design.IStrategy;
import org.acgcloud.filesys.dto.FileOptionRequest;

import java.io.File;

public interface IFileOptStragy extends IStrategy {

    void setFile(File file);

    File getFile();

    FileOptionRequest getFileOptionRequest();

    void setFileOptionRequest(FileOptionRequest fileOptionRequest);

    String getOptCn();

}
