package org.acgcloud.dmsys.dao;

import org.acgcloud.dmsys.entity.WkstationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkStationRepository  extends JpaRepository<WkstationEntity,Long> {
    List<WkstationEntity> findByUserId(Long userId);
}
