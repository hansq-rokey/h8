package com.ibaixiong.erp.service.oem;

import java.util.List;

import com.ibaixiong.entity.ErpOemInfo;

public interface ErpOemInfoService {
	List<ErpOemInfo> getList(Integer pageNo);
	void insert(ErpOemInfo info);
	void update(ErpOemInfo info);
}
