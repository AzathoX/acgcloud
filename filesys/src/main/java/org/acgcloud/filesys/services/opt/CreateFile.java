package org.acgcloud.filesys.services.opt;

import cn.hutool.core.io.FileUtil;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.acgcloud.filesys.dto.FileOptionRequest;
import org.acgcloud.filesys.services.IFileOptStragy;

import java.io.File;
import java.io.IOException;

@Component
@Data
public class CreateFile implements IFileOptStragy {

    private  File file;

    private FileOptionRequest fileOptionRequest;

    @Override
    public String support() {
        return "mk";
    }

    @Override
    public void handle() {
        if(fileOptionRequest.getIsFile()){
            FileUtil.touch(file);
        }
        else {
            FileUtil.mkdir(file);
        }
    }


    @Override
    public String getOptCn() {
        return "创建文件";
    }
}
