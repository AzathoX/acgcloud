package org.acgcloud.filesys.services.opt;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.acgcloud.filesys.dto.FileOptionRequest;
import org.acgcloud.filesys.services.IFileOptStragy;

import java.io.File;
import java.io.IOException;


@Component
@Data
public class UploadFile implements IFileOptStragy {

    private File file;

    private FileOptionRequest fileOptionRequest;

    @Override
    public String getOptCn() {
        return "文件上传";
    }

    @Override
    public String support() {
        return "upload";
    }

    @SneakyThrows
    @Override
    public void handle() {
        MultipartFile multipartFile = fileOptionRequest.getMultipartFile();
        MultipartFile[] files = fileOptionRequest.getFiles();
        if(ObjectUtils.allNotNull(multipartFile)){
            try {
                multipartFile.transferTo(this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(ObjectUtils.allNotNull(files)){
            String parent = this.file.getParent();
            String[] fileNames = fileOptionRequest.getFileNames();
            assert  fileNames.length == files.length : "文件长度不一致和文件名长度不一致无法对照";
            for (int i = 0 , len = files.length; i < len; i++) {
                File file = new File(parent + "/" + fileNames[i]);
                files[i].transferTo(file);
            }
        }
    }
}
