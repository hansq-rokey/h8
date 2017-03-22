package com.ibaixiong.erp.dao.storage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpOutStorage;

public interface ErpOutStorageDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpOutStorage record);

    int insertSelective(ErpOutStorage record);

    ErpOutStorage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpOutStorage record);

    int updateByPrimaryKey(ErpOutStorage record);
    
    List<ErpOutStorage> queryOutStorageList(Map<String, Object> map);
    
    void updateWaybillNumberByOrderId(@Param("orderNumber")String orderNumber,@Param("waybillNumber")String waybillNumber);
}