package org.acgcloud.filesys.services;

import org.acgcloud.filesys.dto.FileOptionRequest;

public interface IFileOptionServices {
     IFileOptStragy doService(FileOptionRequest fileOptionRequest, String opt);
}
