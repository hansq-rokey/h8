package com.ibaixiong.erp.service.storage;

import java.util.List;

import com.ibaixiong.entity.ErpStorageUserRelation;

public interface ErpStorageUserRelationService {
	void insert(ErpStorageUserRelation userRelation);
	List<ErpStorageUserRelation> getListByStorageId(Long storageId,Integer pageNo);
}
