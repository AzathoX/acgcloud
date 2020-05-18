package org.nrocn.user.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import org.nrocn.user.model.AccountDomain;
import org.nrocn.user.mapper.AccountDomainMapper;
import org.nrocn.user.services.AccountDomainService;
@Service
public class AccountDomainServiceImpl extends ServiceImpl<AccountDomainMapper, AccountDomain> implements AccountDomainService{

    @Override
    public int updateBatch(List<AccountDomain> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<AccountDomain> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(AccountDomain record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(AccountDomain record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
