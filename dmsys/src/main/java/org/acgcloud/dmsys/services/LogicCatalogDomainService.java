package org.acgcloud.dmsys.services;

import com.baomidou.mybatisplus.extension.service.IService;
import org.acgcloud.dmsys.model.LogicCatalogDomain;

import java.util.List;

public interface LogicCatalogDomainService extends IService<LogicCatalogDomain> {


    int updateBatch(List<LogicCatalogDomain> list);

    int batchInsert(List<LogicCatalogDomain> list);

    int insertOrUpdate(LogicCatalogDomain record);

    int insertOrUpdateSelective(LogicCatalogDomain record);

}
