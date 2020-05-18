package org.acgcloud.dmsys.entity;

import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Proxy(lazy = false)
@Table(name = "tb_logic_catalog", schema = "acgcloud", catalog = "")
@ToString
public class LogicCatalogEntity {
    private long id;
    private long prartitionId;
    private String catalogName;
    private String catalogHashName;
    private long isDel;
    private Timestamp createTime;
    private Timestamp modifiedTime;
    private PrartitionEntity prartitionEntity;




    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "catalog_name")
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @Basic
    @Column(name = "catalog_hash_name")
    public String getCatalogHashName() {
        return catalogHashName;
    }

    public void setCatalogHashName(String catalogHashName) {
        this.catalogHashName = catalogHashName;
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


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prartition_id",referencedColumnName = "id")
    public PrartitionEntity getPrartitionEntity() {
        return prartitionEntity;
    }

    public void setPrartitionEntity(PrartitionEntity prartitionEntity) {
        this.prartitionEntity = prartitionEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogicCatalogEntity that = (LogicCatalogEntity) o;

        if (id != that.id) return false;
        if (prartitionId != that.prartitionId) return false;
        if (isDel != that.isDel) return false;
        if (catalogName != null ? !catalogName.equals(that.catalogName) : that.catalogName != null) return false;
        if (catalogHashName != null ? !catalogHashName.equals(that.catalogHashName) : that.catalogHashName != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifiedTime != null ? !modifiedTime.equals(that.modifiedTime) : that.modifiedTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (prartitionId ^ (prartitionId >>> 32));
        result = 31 * result + (catalogName != null ? catalogName.hashCode() : 0);
        result = 31 * result + (catalogHashName != null ? catalogHashName.hashCode() : 0);
        result = 31 * result + (int) (isDel ^ (isDel >>> 32));
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifiedTime != null ? modifiedTime.hashCode() : 0);
        return result;
    }
}
