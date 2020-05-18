package org.acgcloud.dmsys.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.acgcloud.dmsys.model.CloudFlodlerDomain;

@Mapper
public interface CloudFlodlerDomainMapper extends BaseMapper<CloudFlodlerDomain> {
    int updateBatch(List<CloudFlodlerDomain> list);

    int batchInsert(@Param("list") List<CloudFlodlerDomain> list);

    int insertOrUpdate(CloudFlodlerDomain record);

    int insertOrUpdateSelective(CloudFlodlerDomain record);
}