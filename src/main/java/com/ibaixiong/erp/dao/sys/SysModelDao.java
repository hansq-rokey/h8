/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.dao.sys;

import java.util.List;

import com.ibaixiong.entity.SysModel;

public interface SysModelDao {
	int deleteByPrimaryKey(Integer id);

	int insert(SysModel record);

	int insertSelective(SysModel record);

	SysModel selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SysModel record);

	int updateByPrimaryKey(SysModel record);

	List<SysModel> getAllModelList();

	List<SysModel> getModelListByIds(List<Long> item);
	
	List<SysModel> getModelsByPid(Long id);
}