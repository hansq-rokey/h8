package com.ibaixiong.erp.service.purchase;

import java.util.List;

import com.ibaixiong.entity.ErpPurchaseMaterial;

public interface ErpPurchaseMaterialService {
	List<ErpPurchaseMaterial> getList(Integer pageNo);
	void insert(ErpPurchaseMaterial order);
	void update(ErpPurchaseMaterial order);
	void delete(Long id);
	ErpPurchaseMaterial get(Long id);
}
