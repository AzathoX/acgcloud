package org.acgcloud.dmsys.entity;

import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Timestamp;

@Proxy(lazy = false)
@Entity
@Table(name = "tb_cloud_flodler", schema = "acgcloud", catalog = "")
@ToString
public class CloudFlodlerEntity {
    private long id;
    private long prartitionId;
    private String filesys;
    private long catalogId;
    private String logicPath;
    private Long parentId;
    private Byte isfile;
    private String suffix;
    private String name;
    private String hashName;
    private Boolean uploading;
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
    @Column(name = "prartition_id")
    public long getPrartitionId() {
        return prartitionId;
    }

    public void setPrartitionId(long prartitionId) {
        this.prartitionId = prartitionId;
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
    @Column(name = "catalog_id")
    public long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
    }

    @Basic
    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
    @Column(name = "isfile")
    public Byte getIsfile() {
        return isfile;
    }

    public void setIsfile(Byte isfile) {
        this.isfile = isfile;
    }

    @Basic
    @Column(name = "suffix")
    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "hash_name")
    public String getHashName() {
        return hashName;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
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
    @Column(name = "uploading")
    public Boolean getUploading() {
        return uploading;
    }

    public void setUploading(Boolean uploading) {
        this.uploading = uploading;
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
        CloudFlodlerEntity that = (CloudFlodlerEntity) o;
        if (id != that.id) return false;
        if (prartitionId != that.prartitionId) return false;
        if (catalogId != that.catalogId) return false;
        if (isDel != that.isDel) return false;
        if (filesys != null ? !filesys.equals(that.filesys) : that.filesys != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (isfile != null ? !isfile.equals(that.isfile) : that.isfile != null) return false;
        if (suffix != null ? !suffix.equals(that.suffix) : that.suffix != null) return false;
        if (logicPath != null ? !logicPath.equals(that.logicPath) : that.logicPath != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (hashName != null ? !hashName.equals(that.hashName) : that.hashName != null) return false;
        if (vpSize != null ? !vpSize.equals(that.vpSize) : that.vpSize != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifiedTime != null ? !modifiedTime.equals(that.modifiedTime) : that.modifiedTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (prartitionId ^ (prartitionId >>> 32));
        result = 31 * result + (filesys != null ? filesys.hashCode() : 0);
        result = 31 * result + (int) (catalogId ^ (catalogId >>> 32));
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (logicPath != null ? parentId.hashCode() : 0);
        result = 31 * result + (isfile != null ? isfile.hashCode() : 0);
        result = 31 * result + (suffix != null ? suffix.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (hashName != null ? hashName.hashCode() : 0);
        result = 31 * result + (vpSize != null ? vpSize.hashCode() : 0);
        result = 31 * result + (int) (isDel ^ (isDel >>> 32));
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifiedTime != null ? modifiedTime.hashCode() : 0);
        return result;
    }
}
