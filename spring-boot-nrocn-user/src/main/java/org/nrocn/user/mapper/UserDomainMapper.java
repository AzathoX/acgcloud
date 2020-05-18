package org.nrocn.user.mapper;


import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.nrocn.user.model.UserDomain;

@Mapper
public interface UserDomainMapper extends BaseMapper<UserDomain> {
    int updateBatch(List<UserDomain> list);

    int batchInsert(@Param("list") List<UserDomain> list);

    int insertOrUpdate(UserDomain record);

    int insertOrUpdateSelective(UserDomain record);
}