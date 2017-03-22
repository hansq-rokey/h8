package com.ibaixiong.erp.dao.storage;

import java.util.List;

import com.ibaixiong.entity.ErpSysStorage;

public interface ErpSysStorageDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpSysStorage record);

    int insertSelective(ErpSysStorage record);

    ErpSysStorage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpSysStorage record);

    int updateByPrimaryKey(ErpSysStorage record);
    
    List<ErpSysStorage> getList();
}