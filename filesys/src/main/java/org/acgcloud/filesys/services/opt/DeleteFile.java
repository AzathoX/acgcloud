package org.acgcloud.filesys.services.opt;

import cn.hutool.core.io.FileUtil;
import lombok.Data;
import org.acgcloud.filesys.dto.FileOptionRequest;
import org.acgcloud.filesys.services.IFileOptStragy;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Data
public class DeleteFile implements IFileOptStragy {

    private  File file;

    private FileOptionRequest fileOptionRequest;


    @Override
    public String support() {
        return "del";
    }

    @Override
    public void handle() {
        FileUtil.del(file);
    }


    @Override
    public String getOptCn() {
        return "删除文件";
    }
}
