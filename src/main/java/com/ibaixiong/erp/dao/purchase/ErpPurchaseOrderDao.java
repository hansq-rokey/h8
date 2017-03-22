package com.ibaixiong.erp.dao.purchase;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpPurchaseOrder;

public interface ErpPurchaseOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpPurchaseOrder record);

    int insertSelective(ErpPurchaseOrder record);

    ErpPurchaseOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpPurchaseOrder record);

    int updateByPrimaryKey(ErpPurchaseOrder record);
    
    List<ErpPurchaseOrder> getList();
    
    ErpPurchaseOrder getOrderByCategoryBatch(@Param("categoryId")Long categoryId,@Param("categoryModelId")Long categoryModelId,@Param("categoryModelFormatId")Long categoryModelFormatId ,@Param("batch")String batch);
}