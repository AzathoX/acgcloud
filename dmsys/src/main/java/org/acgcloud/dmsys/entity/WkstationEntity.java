package org.acgcloud.dmsys.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Proxy(lazy = false)
@Table(name = "tb_cloud_wkstation", schema = "acgcloud", catalog = "")
public class WkstationEntity {
    private long id;
    private long userId;
    private CloudFlodlerEntity cloudFlodlerEntity;
    private Byte isAdmin;
    private long isDel;
    private String logicPath;
    private Timestamp createTime;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flodler_id",referencedColumnName = "id")
    public CloudFlodlerEntity getCloudFlodlerEntity() {
        return cloudFlodlerEntity;
    }

    public void setCloudFlodlerEntity(CloudFlodlerEntity cloudFlodlerEntity) {
        this.cloudFlodlerEntity = cloudFlodlerEntity;
    }


    @Basic
    @Column(name = "logic_path")
    public String getLogicPath() {
        return logicPath;
    }

    public void setLogicPath(String logicPath) {
        this.logicPath = logicPath;
    }


    @Basic
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    @Basic
    @Column(name = "is_admin")
    public Byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Basic
    @Column(name = "is_del")
    public long getIsDel() {
        return isDel;
    }

    public void setIsDel(long isDel) {
        this.isDel = isDel;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WkstationEntity that = (WkstationEntity) o;
        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (isDel != that.isDel) return false;
        if (isAdmin != null ? !isAdmin.equals(that.isAdmin) : that.isAdmin != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (isAdmin != null ? isAdmin.hashCode() : 0);
        result = 31 * result + (logicPath != null ? logicPath.hashCode() : 0);
        result = 31 * result + (int) (isDel ^ (isDel >>> 32));
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
