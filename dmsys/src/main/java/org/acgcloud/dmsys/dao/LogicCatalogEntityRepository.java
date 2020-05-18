package org.acgcloud.dmsys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.acgcloud.dmsys.entity.LogicCatalogEntity;

public interface LogicCatalogEntityRepository extends JpaRepository<LogicCatalogEntity,Long> {
    LogicCatalogEntity findByCatalogHashName(String hashName);

    LogicCatalogEntity findByCatalogName(String name);
}
