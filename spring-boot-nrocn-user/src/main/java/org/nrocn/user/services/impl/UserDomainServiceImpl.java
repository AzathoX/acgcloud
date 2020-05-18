package org.nrocn.user.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;
import org.nrocn.user.mapper.UserDomainMapper;
import org.nrocn.user.model.UserDomain;
import org.nrocn.user.services.UserDomainService;
@Service
public class UserDomainServiceImpl extends ServiceImpl<UserDomainMapper, UserDomain> implements UserDomainService{

    @Override
    public int updateBatch(List<UserDomain> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<UserDomain> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(UserDomain record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(UserDomain record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
