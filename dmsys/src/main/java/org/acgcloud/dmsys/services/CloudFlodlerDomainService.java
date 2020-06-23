package org.acgcloud.dmsys.services;

import com.baomidou.mybatisplus.extension.service.IService;
import org.acgcloud.dmsys.model.CloudFlodlerDomain;

import java.util.List;

public interface CloudFlodlerDomainService extends IService<CloudFlodlerDomain> {


    int updateBatch(List<CloudFlodlerDomain> list);

    int batchInsert(List<CloudFlodlerDomain> list);

    int insertOrUpdate(CloudFlodlerDomain record);

    int insertOrUpdateSelective(CloudFlodlerDomain record);

}
