package com.ibaixiong.erp.service.sysset;

import com.ibaixiong.entity.ErpBaseBatch;

public interface ErpBaseBatchService {
	ErpBaseBatch selectByBatch(Long categoryId,Long categoryModelId,Long categoryModelFormatId);
    
    void updateIndexNum(Long categoryId,Long categoryModelId,Long categoryModelFormatId);
    
    void insert(ErpBaseBatch batch);
}
