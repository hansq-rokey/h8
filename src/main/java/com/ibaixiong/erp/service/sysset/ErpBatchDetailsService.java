package com.ibaixiong.erp.service.sysset;

import java.util.List;

import com.ibaixiong.entity.ErpBatchDetails;

public interface ErpBatchDetailsService {
	void insert(ErpBatchDetails batch);
	void deleteByOrderId(Long id);
	List<ErpBatchDetails> getListByBatchId(Long batchId,Byte invalid);
	List<ErpBatchDetails> getListPageByBatchId(Long batchId,Byte invalid,Integer pageNo);
	void updateInvalid(Long id,Byte invalid); 
	ErpBatchDetails get(Long id);
	ErpBatchDetails getByOrderId(Long id);
}
