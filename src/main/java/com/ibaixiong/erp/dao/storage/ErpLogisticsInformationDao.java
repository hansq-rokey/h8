package com.ibaixiong.erp.dao.storage;

import com.ibaixiong.entity.ErpLogisticsInformation;

public interface ErpLogisticsInformationDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpLogisticsInformation record);

    int insertSelective(ErpLogisticsInformation record);

    ErpLogisticsInformation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpLogisticsInformation record);

    int updateByPrimaryKey(ErpLogisticsInformation record);
    
    ErpLogisticsInformation getErpLogisticsInformationBylogisticID(String logisticID);

    
}