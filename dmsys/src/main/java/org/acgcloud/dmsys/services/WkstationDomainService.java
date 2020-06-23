package org.acgcloud.dmsys.services;

import com.baomidou.mybatisplus.extension.service.IService;
import org.acgcloud.dmsys.model.WkstationDomain;

import java.util.List;

public interface WkstationDomainService extends IService<WkstationDomain> {


    int updateBatch(List<WkstationDomain> list);

    int batchInsert(List<WkstationDomain> list);

    int insertOrUpdate(WkstationDomain record);

    int insertOrUpdateSelective(WkstationDomain record);

}
