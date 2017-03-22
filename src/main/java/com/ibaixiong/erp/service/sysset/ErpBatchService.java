package com.ibaixiong.erp.service.sysset;

import java.util.List;

import com.ibaixiong.entity.ErpBatch;

public interface ErpBatchService {
	void insert(ErpBatch batch);
	ErpBatch queryByBatch(Long categoryId,Long categoryModelId,Long categoryModelFormatId,int invalid);
	List<ErpBatch> getList();
	List<ErpBatch> getListPage(Integer pageNo);
	ErpBatch get(Long id);
	void del(Long id);
}
