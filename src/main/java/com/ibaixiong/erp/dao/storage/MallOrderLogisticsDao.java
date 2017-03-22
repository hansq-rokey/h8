package com.ibaixiong.erp.dao.storage;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.MallOrderLogistics;

public interface MallOrderLogisticsDao {
    int deleteByPrimaryKey(Long id);

    int insert(MallOrderLogistics record);

    int insertSelective(MallOrderLogistics record);

    MallOrderLogistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MallOrderLogistics record);

    int updateByPrimaryKey(MallOrderLogistics record);
    
    MallOrderLogistics getByLogisticsId(String logisticId);
    
    void updateExpressNoByOrderNumber(@Param("orderNumber")String orderNumber,@Param("expressNo")String expressNo);
}