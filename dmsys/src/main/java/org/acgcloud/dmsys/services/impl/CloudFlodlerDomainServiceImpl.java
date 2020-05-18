package org.acgcloud.dmsys.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.acgcloud.dmsys.mapper.CloudFlodlerDomainMapper;
import org.acgcloud.dmsys.model.CloudFlodlerDomain;
import org.acgcloud.dmsys.services.CloudFlodlerDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudFlodlerDomainServiceImpl extends ServiceImpl<CloudFlodlerDomainMapper, CloudFlodlerDomain> implements CloudFlodlerDomainService {



    @Override
    public int updateBatch(List<CloudFlodlerDomain> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<CloudFlodlerDomain> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(CloudFlodlerDomain record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(CloudFlodlerDomain record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
