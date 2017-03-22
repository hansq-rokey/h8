package com.ibaixiong.erp.dao.sysset;

import com.ibaixiong.entity.ErpBaseRole;

public interface ErpBaseRoleDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpBaseRole record);


    int insertSelective(ErpBaseRole record);
    
    ErpBaseRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpBaseRole record);

    int updateByPrimaryKey(ErpBaseRole record);
    
    ErpBaseRole getErpBaseRoleByType(String type);
}