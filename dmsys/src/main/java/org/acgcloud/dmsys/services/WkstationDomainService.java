package org.acgcloud.dmsys.services;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import org.acgcloud.dmsys.model.WkstationDomain;
public interface WkstationDomainService extends IService<WkstationDomain> {


    int updateBatch(List<WkstationDomain> list);

    int batchInsert(List<WkstationDomain> list);

    int insertOrUpdate(WkstationDomain record);

    int insertOrUpdateSelective(WkstationDomain record);

}
