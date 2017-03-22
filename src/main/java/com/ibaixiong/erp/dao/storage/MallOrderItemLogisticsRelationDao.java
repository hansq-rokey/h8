package com.ibaixiong.erp.dao.storage;

import com.ibaixiong.entity.MallOrderItemLogisticsRelation;

public interface MallOrderItemLogisticsRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(MallOrderItemLogisticsRelation record);

    int insertSelective(MallOrderItemLogisticsRelation record);

    MallOrderItemLogisticsRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MallOrderItemLogisticsRelation record);

    int updateByPrimaryKey(MallOrderItemLogisticsRelation record);
}