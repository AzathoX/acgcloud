package org.nrocn.user.services;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import org.nrocn.user.model.UserDomain;
public interface UserDomainService extends IService<UserDomain> {



    int updateBatch(List<UserDomain> list);

    int batchInsert(List<UserDomain> list);

    int insertOrUpdate(UserDomain record);

    int insertOrUpdateSelective(UserDomain record);

}
