/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.dao.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.SysAdmin;

public interface SysAdminDao {
	int deleteByPrimaryKey(Long id);

	Long insertSelective(SysAdmin record);

	SysAdmin selectByPrimaryKey(Long id);

	SysAdmin selectByLoginName(String loginName);

	int updateByPrimaryKeySelective(SysAdmin record);

	int updateByPrimaryKey(SysAdmin record);
	
	List<SysAdmin> querySysAdminList(@Param("status") Byte status, @Param("queryName")  String queryName);
	
	List<SysAdmin> queryAdminsByOrgIds(@Param("orgId") Long orgId);
}