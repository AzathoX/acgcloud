package org.acgcloud.dmsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.acgcloud.dmsys.model.WkstationDomain;

@Mapper
public interface WkstationDomainMapper extends BaseMapper<WkstationDomain> {
    int updateBatch(List<WkstationDomain> list);

    int batchInsert(@Param("list") List<WkstationDomain> list);

    int insertOrUpdate(WkstationDomain record);

    int insertOrUpdateSelective(WkstationDomain record);
}