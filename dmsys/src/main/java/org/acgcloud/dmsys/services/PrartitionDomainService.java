package org.acgcloud.dmsys.services;

import com.baomidou.mybatisplus.extension.service.IService;
import org.acgcloud.dmsys.model.PrartitionDomain;

import java.util.List;

public interface PrartitionDomainService extends IService<PrartitionDomain> {



    int insert(PrartitionDomain record);

    int insertOrUpdate(PrartitionDomain record);

    int insertOrUpdateSelective(PrartitionDomain record);


    PrartitionDomain selectByPrimaryKey(Long id);

    int updateBatch(List<PrartitionDomain> list);


    int batchInsert(List<PrartitionDomain> list);

}



