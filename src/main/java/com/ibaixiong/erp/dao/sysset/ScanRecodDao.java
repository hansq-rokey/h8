package com.ibaixiong.erp.dao.sysset;

import com.ibaixiong.entity.ScanRecod;

public interface ScanRecodDao {

	int insert(ScanRecod record);

	ScanRecod selectByPrimaryKey(Long id);

	void deleteByMacCode(String macCode);

}