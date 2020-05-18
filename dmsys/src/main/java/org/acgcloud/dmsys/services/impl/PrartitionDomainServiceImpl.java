package org.acgcloud.dmsys.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.acgcloud.dmsys.mapper.PrartitionDomainMapper;
import org.acgcloud.dmsys.model.PrartitionDomain;
import org.acgcloud.dmsys.services.PrartitionDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrartitionDomainServiceImpl extends ServiceImpl<PrartitionDomainMapper, PrartitionDomain> implements PrartitionDomainService {

    @Autowired
    private PrartitionDomainMapper prartitionDomainMapper;



    @Override
    public int insert(PrartitionDomain record) {
        return prartitionDomainMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(PrartitionDomain record) {
        return prartitionDomainMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(PrartitionDomain record) {
        return prartitionDomainMapper.insertOrUpdateSelective(record);
    }




    @Override
    public PrartitionDomain selectByPrimaryKey(Long id) {
        return prartitionDomainMapper.selectByPrimaryKey(id);
    }


    @Override
    public int updateBatch(List<PrartitionDomain> list) {
        return prartitionDomainMapper.updateBatch(list);
    }



    @Override
    public int batchInsert(List<PrartitionDomain> list) {
        return prartitionDomainMapper.batchInsert(list);
    }

}



