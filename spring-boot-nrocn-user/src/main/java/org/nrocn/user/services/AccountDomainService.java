package org.nrocn.user.services;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import org.nrocn.user.model.AccountDomain;

public interface AccountDomainService extends IService<AccountDomain> {


    int updateBatch(List<AccountDomain> list);

    int batchInsert(List<AccountDomain> list);

    int insertOrUpdate(AccountDomain record);

    int insertOrUpdateSelective(AccountDomain record);

}
