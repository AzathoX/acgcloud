package org.acgcloud.dmsys.services;

import org.nrocn.user.entity.UserEntity;
import org.acgcloud.dmsys.entity.LogicCatalogEntity;
import org.acgcloud.dmsys.entity.WkstationEntity;

import java.util.List;

public interface JpaRepositoryServices {

    List<LogicCatalogEntity> cataloglist();

    LogicCatalogEntity findByCatalogName(String name);

    UserEntity findByUserName(String userName);

    LogicCatalogEntity findByCatalogHashName(String hashName);

    WkstationEntity findWorkStationById(Long id);

    List<WkstationEntity> findWorkStationByUserId(Long userId);
}
