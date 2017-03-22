package com.ibaixiong.erp.dao.storage;

import com.ibaixiong.entity.ErpExpressCompany;

public interface ErpExpressCompanyDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpExpressCompany record);

    int insertSelective(ErpExpressCompany record);

    ErpExpressCompany selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpExpressCompany record);

    int updateByPrimaryKey(ErpExpressCompany record);
}