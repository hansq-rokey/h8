package com.ibaixiong.erp.dao.storage;

import java.util.List;
import java.util.Map;

import com.ibaixiong.entity.ErpPutStorageCount;

public interface ErpPutStorageCountDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpPutStorageCount record);

    int insertSelective(ErpPutStorageCount record);

    ErpPutStorageCount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpPutStorageCount record);

    int updateByPrimaryKey(ErpPutStorageCount record);
    
    /**
     * 入库列表
     * @param adminId   入库人员ID
     * @param invalid   记录有效
     * @return
     */
    List<ErpPutStorageCount> queryPutStorageList(Map<String, Object> map);
}