package com.ibaixiong.erp.service.storage;

import java.util.List;

import com.ibaixiong.entity.ErpBom;

public interface ErpBomService {
	List<ErpBom> getListByOrderNumber(String orderNumber,Integer pageNo);
	
	void delete(Long bomId);
}
