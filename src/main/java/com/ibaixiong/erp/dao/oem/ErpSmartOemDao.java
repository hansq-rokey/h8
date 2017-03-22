package com.ibaixiong.erp.dao.oem;

import com.ibaixiong.entity.ErpSmartOem;

public interface ErpSmartOemDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpSmartOem record);

    int insertSelective(ErpSmartOem record);

    ErpSmartOem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpSmartOem record);

    int updateByPrimaryKeyWithBLOBs(ErpSmartOem record);

    int updateByPrimaryKey(ErpSmartOem record);
}