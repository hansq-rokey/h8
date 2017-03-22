package com.ibaixiong.erp.dao.storage;

import com.ibaixiong.entity.ErpLogisticsPush;

public interface ErpLogisticsPushDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpLogisticsPush record);

    int insertSelective(ErpLogisticsPush record);

    ErpLogisticsPush selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpLogisticsPush record);

    int updateByPrimaryKeyWithBLOBs(ErpLogisticsPush record);

    int updateByPrimaryKey(ErpLogisticsPush record);
}