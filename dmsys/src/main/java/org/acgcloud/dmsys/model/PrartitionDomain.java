package org.acgcloud.dmsys.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "tb_prartition")
public class PrartitionDomain implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "filesys")
    private String filesys;

    @TableField(value = "vp_name")
    private String vpName;

    @TableField(value = "vp_hash_name")
    private String vpHashName;

    @TableField(value = "vp_size")
    private Double vpSize;

    @TableField(value = "is_del")
    private Long isDel;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "modified_time")
    private Date modifiedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_FILESYS = "filesys";

    public static final String COL_VP_NAME = "vp_name";

    public static final String COL_VP_HASH_NAME = "vp_hash_name";

    public static final String COL_VP_SIZE = "vp_size";

    public static final String COL_IS_DEL = "is_del";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_TIME = "modified_time";
}