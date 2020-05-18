package org.nrocn.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "tb_user")
public class UserDomain implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "account_id")
    private Long accountId;

    @TableField(value = "username")
    private String username;

    @TableField(value = "myname")
    private String myname;

    @TableField(value = "is_del")
    private Long isDel;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "modified_time")
    private Date modifiedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ACCOUNT_ID = "myname";

    public static final String COL_USERNAME = "username";

    public static final String COL_MYNAME = "myname";


    public static final String COL_REMARK = "remark";

    public static final String COL_REMARK1 = "remark1";

    public static final String COL_REMARK2 = "remark2";

    public static final String COL_REMARK3 = "remark3";

    public static final String COL_REMARK4 = "remark4";

    public static final String COL_REMARK5 = "remark5";

    public static final String COL_REMARK6 = "remark6";

    public static final String COL_REMARK7 = "remark7";

    public static final String COL_REMARK8 = "remark8";

    public static final String COL_REMARK9 = "remark9";

    public static final String COL_REMARK10 = "remark10";

    public static final String COL_REMARK11 = "remark11";

    public static final String COL_REMARK12 = "remark12";

    public static final String COL_REMARK13 = "remark13";

    public static final String COL_REMARK14 = "remark14";

    public static final String COL_REMARK15 = "remark15";

    public static final String COL_REMARK16 = "remark16";

    public static final String COL_REMARK17 = "remark17";

    public static final String COL_REMARK18 = "remark18";

    public static final String COL_REMARK19 = "remark19";

    public static final String COL_REMARK20 = "remark20";

    public static final String COL_REMARK21 = "remark21";

    public static final String COL_REMARK22 = "remark22";

    public static final String COL_REMARK23 = "remark23";

    public static final String COL_REMARK24 = "remark24";

    public static final String COL_REMARK25 = "remark25";

    public static final String COL_IS_DEL = "is_del";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_TIME = "modified_time";
}