/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.storage;

import java.util.List;

import com.ibaixiong.entity.ErpSysStorage;


/**
 * @description
 * @author zhaolei
 */
public interface ErpSysStorageService {
	List<ErpSysStorage> getList(Integer pageNo);
	void insert(ErpSysStorage stor);
	void update(ErpSysStorage stor);
	ErpSysStorage get(Long id);
}
