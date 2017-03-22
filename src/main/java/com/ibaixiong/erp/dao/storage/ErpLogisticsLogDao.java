package com.ibaixiong.erp.dao.storage;

import com.ibaixiong.entity.ErpLogisticsLog;

public interface ErpLogisticsLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpLogisticsLog record);

    int insertSelective(ErpLogisticsLog record);

    ErpLogisticsLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpLogisticsLog record);

    int updateByPrimaryKeyWithBLOBs(ErpLogisticsLog record);

    int updateByPrimaryKey(ErpLogisticsLog record);
}