package org.acgcloud.dmsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.acgcloud.dmsys.model.LogicCatalogDomain;

import java.util.List;

@Mapper
public interface LogicCatalogDomainMapper extends BaseMapper<LogicCatalogDomain> {
    int updateBatch(List<LogicCatalogDomain> list);

    int batchInsert(@Param("list") List<LogicCatalogDomain> list);

    int insertOrUpdate(LogicCatalogDomain record);

    int insertOrUpdateSelective(LogicCatalogDomain record);
}