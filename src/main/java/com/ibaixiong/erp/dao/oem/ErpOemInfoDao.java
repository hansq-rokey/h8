package com.ibaixiong.erp.dao.oem;

import java.util.List;

import com.ibaixiong.entity.ErpOemInfo;

public interface ErpOemInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpOemInfo record);

    int insertSelective(ErpOemInfo record);

    ErpOemInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpOemInfo record);

    int updateByPrimaryKey(ErpOemInfo record);
    
    List<ErpOemInfo> getList();
}