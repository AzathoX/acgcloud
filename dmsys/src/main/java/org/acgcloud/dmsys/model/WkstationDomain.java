package org.acgcloud.dmsys.model;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "tb_cloud_wkstation")
public class WkstationDomain implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "flodler_id")
    private Long flodlerId;

    @TableField(value = "logic_path")
    private String logicPath;

    @TableField(value = "is_admin")
    private Boolean isAdmin;

    @TableField(value = "is_del")
    private Long isDel;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "modified_time")
    private Date modifiedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_FLODLER_ID = "flodler_id";

    public static final String COL_IS_ADMIN = "is_admin";

    public static final String COL_IS_DEL = "is_del";

    public static final String COL_LOGIC_PATH = "logic_path";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_TIME = "modified_time";
}