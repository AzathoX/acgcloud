package org.nrocn.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.nrocn.user.model.AccountDomain;

@Mapper
public interface AccountDomainMapper extends BaseMapper<AccountDomain> {
    int updateBatch(List<AccountDomain> list);

    int batchInsert(@Param("list") List<AccountDomain> list);

    int insertOrUpdate(AccountDomain record);

    int insertOrUpdateSelective(AccountDomain record);
}