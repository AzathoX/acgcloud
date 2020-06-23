package org.acgcloud.dmsys.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.acgcloud.dmsys.model.PrartitionDomain;

import java.util.List;

@Mapper
public interface PrartitionDomainMapper extends BaseMapper<PrartitionDomain> {
    int updateBatch(List<PrartitionDomain> list);

    int batchInsert(@Param("list") List<PrartitionDomain> list);

    int insertOrUpdate(PrartitionDomain record);

    int insertOrUpdateSelective(PrartitionDomain record);

    PrartitionDomain selectByPrimaryKey(Long id);
}