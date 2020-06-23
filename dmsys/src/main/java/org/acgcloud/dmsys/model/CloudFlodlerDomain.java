package org.acgcloud.dmsys.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "tb_cloud_flodler")
public class CloudFlodlerDomain implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "prartition_id")
    private Long prartitionId;

    @TableField(value = "filesys")
    private String filesys;

    @TableField(value = "catalog_id")
    private Long catalogId;

    @TableField(value = "parent_id")
    private Long parentId;

    @TableField(value = "isfile")
    private Boolean isfile;

    @TableField(value = "suffix")
    private String suffix;

    @TableField(value = "name")
    private String name;

    @TableField(value = "hash_name")
    private String hashName;

    @TableField(value = "vp_size")
    private Double vpSize;

    @TableField(value = "logic_path")
    private String logicPath;

    @TableField(value = "uploading")
    private Boolean uploading;

    @TableField(value = "remark1")
    private String remark1;

    @TableField(value = "remark2")
    private String remark2;

    @TableField(value = "is_del")
    private Long isDel;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "modified_time")
    private Date modifiedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PRARTITION_ID = "prartition_id";

    public static final String COL_FILESYS = "filesys";

    public static final String COL_CATALOG_ID = "catalog_id";

    public static final String COL_LOGIC_PATH = "logic_path";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_ISFILE = "isfile";

    public static final String COL_SUFFIX = "suffix";

    public static final String COL_NAME = "name";

    public static final String COL_HASH_NAME = "hash_name";

    public static final String COL_VP_SIZE = "vp_size";

    public static final String COL_UPLOADING = "uploading";

    public static final String COL_REMARK1 = "remark1";

    public static final String COL_REMARK2 = "remark2";

    public static final String COL_IS_DEL = "is_del";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_TIME = "modified_time";
}