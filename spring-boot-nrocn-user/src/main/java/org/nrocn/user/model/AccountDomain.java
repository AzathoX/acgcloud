package org.nrocn.user.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "tb_account")
public class AccountDomain implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "tel")
    private String tel;

    @TableField(value = "mail")
    private String mail;

    @TableField(value = "username")
    private String username;


    @TableField(value = "password")
    private String password;

    @TableField(value = "password_salt")
    private String passwordSalt;


    @TableField(value = "is_del")
    private Long isDel;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "modified_time")
    private Date modifiedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_TEL = "tel";

    public static final String COL_MAIL = "mail";

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";

    public static final String COL_PASSWORD_SALT = "password_salt";

    public static final String COL_REMARK = "remark";

    public static final String COL_REMARK1 = "remark1";

    public static final String COL_REMARK2 = "remark2";

    public static final String COL_REMARK3 = "remark3";

    public static final String COL_IS_DEL = "is_del";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_TIME = "modified_time";
}