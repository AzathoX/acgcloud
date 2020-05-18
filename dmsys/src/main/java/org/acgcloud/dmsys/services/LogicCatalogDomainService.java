package org.acgcloud.dmsys.services;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import org.acgcloud.dmsys.model.LogicCatalogDomain;

public interface LogicCatalogDomainService extends IService<LogicCatalogDomain> {


    int updateBatch(List<LogicCatalogDomain> list);

    int batchInsert(List<LogicCatalogDomain> list);

    int insertOrUpdate(LogicCatalogDomain record);

    int insertOrUpdateSelective(LogicCatalogDomain record);

}
