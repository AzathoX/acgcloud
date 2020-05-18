package org.nrocn.lib.baseobj;

import java.io.Serializable;
import java.util.Date;

/**
 * 统一基本类型 域
 */
public abstract class AbstractDomain<T> implements IPojo, Serializable {

    /**
     *该对象创建时间 不可修改
     */
    private Date genericReadonlyTime = new Date();
    /**
     * 数据id
     */
    protected Long id;

    /**
     * 对应业务id
     */
    protected String  bussiness;

    /**
     * 票务 或用于 单点登录
     */
    protected String ticky;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 修改时间
     */
    protected Date modifiedTime;

    /**
     * 该对象创建时间 可修改
     */
    protected  Date genericTime = genericReadonlyTime;

    protected  Long isDel;


    public Date getGenericReadonlyTime() {
        return genericReadonlyTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBussiness() {
        return bussiness;
    }

    public void setBussiness(String bussiness) {
        this.bussiness = bussiness;
    }

    public String getTicky() {
        return ticky;
    }

    public void setTicky(String ticky) {
        this.ticky = ticky;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getGenericTime() {
        return genericTime;
    }

    public void setGenericTime(Date genericTime) {
        this.genericTime = genericTime;
    }

    public Long getIsDel() {
        return isDel;
    }

    public void setIsDel(Long isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "AbstractDomain{" +
                "genericReadonlyTime=" + genericReadonlyTime +
                ", id=" + id +
                ", bussiness='" + bussiness + '\'' +
                ", ticky='" + ticky + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", genericTime=" + genericTime +
                ", isDel=" + isDel +
                '}';
    }
}
