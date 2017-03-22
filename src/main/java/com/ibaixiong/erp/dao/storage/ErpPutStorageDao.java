package com.ibaixiong.erp.dao.storage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpPutStorage;

public interface ErpPutStorageDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpPutStorage record);

    int insertSelective(ErpPutStorage record);

    ErpPutStorage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpPutStorage record);

    int updateByPrimaryKey(ErpPutStorage record);
    
    List<ErpPutStorage> queryErpPutStorages(@Param("adminId") Long adminId,@Param("storageCountId") Long storageCountId,@Param("invalid")boolean invalid);
    
    /**
     * 根据某个硬件ID查询入库记录
     * @param hardwareId
     * @param invalid
     * @return
     */
    List<ErpPutStorage> queryErpPutStoragesByHardwareId(@Param("hardwareId")Long hardwareId,@Param("invalid") boolean invalid);
    List<ErpPutStorage> queryErpPutStoragesByUniqueCode(String code);
}