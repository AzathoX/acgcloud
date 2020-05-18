package org.acgcloud.dmsys.dto;

import lombok.Data;
import org.nrocn.lib.baseobj.AbstractDomain;
import org.nrocn.lib.baseobj.BaseDomain;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDomainRequest extends AbstractDomain {

    private Integer prartitionId;

    private String  vpName;

    private String vpHashName;

    private Double  vpSize;

    private  String filesys;

    private Integer catalogId;

    private String catalogName;

    private String catalogHashName;

    private String logicPath;

    private Long userId;

    private Long parentId;

    private String suffix;

    private String name;

    private String hashName;

    private  Boolean isFile;

    private  Boolean delRealPath;

    private  Boolean isRoot;

    private Boolean isAdmin;

    private MultipartFile multipartFile;




}
