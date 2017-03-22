/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.SysRole;
import com.ibaixiong.erp.dao.sys.SysRoleDao;
import com.ibaixiong.erp.service.sys.SysRoleService;

/**
 * @description
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年7月2日-下午1:19:30
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	SysRoleDao sysRoleDao;

	@Override
	public List<SysRole> getAllRoleList() {
		return sysRoleDao.getAllRoleList();
	}

	@Override
	public SysRole getRoseById(Long id) {
		return sysRoleDao.selectByPrimaryKey(id);
	}
	@Override
	public List<SysRole> querySysRoleList(String queryName) {
		return sysRoleDao.querySysRoleList(queryName);
	}
	@Override
	public void deleteSysRole(Long id) {
		sysRoleDao.deleteByPrimaryKey(id);
		
	}

	@Override
	public void insert(SysRole role) {
		sysRoleDao.insertSelective(role);
	}

	@Override
	public void update(SysRole role) {
		sysRoleDao.updateByPrimaryKeySelective(role);
	}
}
