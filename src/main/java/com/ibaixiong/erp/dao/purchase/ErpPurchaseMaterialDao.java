package com.ibaixiong.erp.dao.purchase;

import java.util.List;

import com.ibaixiong.entity.ErpPurchaseMaterial;

public interface ErpPurchaseMaterialDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpPurchaseMaterial record);

    int insertSelective(ErpPurchaseMaterial record);

    ErpPurchaseMaterial selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpPurchaseMaterial record);

    int updateByPrimaryKey(ErpPurchaseMaterial record);
    
    List<ErpPurchaseMaterial> getList();
}