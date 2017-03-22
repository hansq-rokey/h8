package com.ibaixiong.erp.service.purchase;

import java.util.List;

import com.ibaixiong.entity.ErpPurchaseOrder;
import com.ibaixiong.entity.SysAdmin;

public interface ErpPurchaseOrderService {
	List<ErpPurchaseOrder> getList(int pageNo);
	void insert(ErpPurchaseOrder order);
	void update(ErpPurchaseOrder order);
	void delete(Long id);
	ErpPurchaseOrder get(Long id);
	ErpPurchaseOrder getOrderByCategoryBatch(Long categoryId,Long categoryModelId,Long categoryModelFormatId,String batch);
	void save(ErpPurchaseOrder order,SysAdmin loginAdmin);
}
