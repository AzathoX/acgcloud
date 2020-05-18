package org.acgcloud.dmsys.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "tb_logic_catalog")
public class LogicCatalogDomain implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "prartition_id")
    private Long prartitionId;

    @TableField(value = "catalog_name")
    private String catalogName;

    @TableField(value = "catalog_hash_name")
    private String catalogHashName;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "remark1")
    private String remark1;

    @TableField(value = "remark2")
    private String remark2;

    @TableField(value = "remark3")
    private String remark3;

    @TableField(value = "remark4")
    private String remark4;

    @TableField(value = "remark5")
    private String remark5;

    @TableField(value = "remark6")
    private String remark6;

    @TableField(value = "remark7")
    private String remark7;

    @TableField(value = "is_del")
    private Long isDel;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "modified_time")
    private Date modifiedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PRARTITION_ID = "prartition_id";

    public static final String COL_CATALOG_NAME = "catalog_name";

    public static final String COL_CATALOG_HASH_NAME = "catalog_hash_name";

    public static final String COL_REMARK = "remark";

    public static final String COL_REMARK1 = "remark1";

    public static final String COL_REMARK2 = "remark2";

    public static final String COL_REMARK3 = "remark3";

    public static final String COL_REMARK4 = "remark4";

    public static final String COL_REMARK5 = "remark5";

    public static final String COL_REMARK6 = "remark6";

    public static final String COL_REMARK7 = "remark7";

    public static final String COL_IS_DEL = "is_del";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_TIME = "modified_time";
}