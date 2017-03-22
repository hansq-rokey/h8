package com.ibaixiong.erp.dao.sysset;

import com.ibaixiong.entity.ErpExpress;

public interface ErpExpressDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpExpress record);

    int insertSelective(ErpExpress record);

    ErpExpress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpExpress record);

    int updateByPrimaryKey(ErpExpress record);
}