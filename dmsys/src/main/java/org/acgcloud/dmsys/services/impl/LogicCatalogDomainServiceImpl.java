package org.acgcloud.dmsys.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.acgcloud.dmsys.mapper.LogicCatalogDomainMapper;
import org.acgcloud.dmsys.model.LogicCatalogDomain;
import org.acgcloud.dmsys.services.LogicCatalogDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogicCatalogDomainServiceImpl extends ServiceImpl<LogicCatalogDomainMapper, LogicCatalogDomain> implements LogicCatalogDomainService {

    @Override
    public int updateBatch(List<LogicCatalogDomain> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<LogicCatalogDomain> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(LogicCatalogDomain record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(LogicCatalogDomain record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
