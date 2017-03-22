/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.SysRole;

public interface SysRoleDao {
	Long deleteByPrimaryKey(Long id);

	Long insert(SysRole record);

	Long insertSelective(SysRole record);

	SysRole selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SysRole record);

	int updateByPrimaryKey(SysRole record);

	List<SysRole> getAllRoleList();
	
	List<SysRole> querySysRoleList(@Param("queryName") String queryName);
}