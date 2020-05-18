package org.acgcloud.dmsys.entity;

import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Timestamp;

@Proxy(lazy = false)
@Entity
@Table(name = "tb_prartition", schema = "acgcloud", catalog = "")
@ToString
public class PrartitionEntity {
    private long id;
    private String filesys;
    private String vpName;
    private String vpHashName;
    private Double vpSize;
    private long isDel;
    private Timestamp createTime;
    private Timestamp modifiedTime;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "filesys")
    public String getFilesys() {
        return filesys;
    }

    public void setFilesys(String filesys) {
        this.filesys = filesys;
    }

    @Basic
    @Column(name = "vp_name")
    public String getVpName() {
        return vpName;
    }

    public void setVpName(String vpName) {
        this.vpName = vpName;
    }

    @Basic
    @Column(name = "vp_hash_name")
    public String getVpHashName() {
        return vpHashName;
    }

    public void setVpHashName(String vpHashName) {
        this.vpHashName = vpHashName;
    }

    @Basic
    @Column(name = "vp_size")
    public Double getVpSize() {
        return vpSize;
    }

    public void setVpSize(Double vpSize) {
        this.vpSize = vpSize;
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

    @Basic
    @Column(name = "modified_time")
    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrartitionEntity that = (PrartitionEntity) o;

        if (id != that.id) return false;
        if (isDel != that.isDel) return false;
        if (filesys != null ? !filesys.equals(that.filesys) : that.filesys != null) return false;
        if (vpName != null ? !vpName.equals(that.vpName) : that.vpName != null) return false;
        if (vpHashName != null ? !vpHashName.equals(that.vpHashName) : that.vpHashName != null) return false;
        if (vpSize != null ? !vpSize.equals(that.vpSize) : that.vpSize != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifiedTime != null ? !modifiedTime.equals(that.modifiedTime) : that.modifiedTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (filesys != null ? filesys.hashCode() : 0);
        result = 31 * result + (vpName != null ? vpName.hashCode() : 0);
        result = 31 * result + (vpHashName != null ? vpHashName.hashCode() : 0);
        result = 31 * result + (vpSize != null ? vpSize.hashCode() : 0);
        result = 31 * result + (int) (isDel ^ (isDel >>> 32));
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifiedTime != null ? modifiedTime.hashCode() : 0);
        return result;
    }
}
