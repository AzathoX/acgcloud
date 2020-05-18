package org.nrocn.lib.orm;


import java.util.List;


@SuppressWarnings("ALL")
public interface IBaseDataAccessMapper<T> {
    long countByExample(Object criterion);

    int deleteByExample(Object criterion);

    List<T> selectByExample(Object criterion);

    int updateByExampleSelective(T record, Object criterion);

    int updateByExample(T record, Object criterion);

    int updateBatch(List<T> list);

    int batchInsert(List<T> list);

    int insertOrUpdate(T record);

    int insertOrUpdateSelective(T record);

}
