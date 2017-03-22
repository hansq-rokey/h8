/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.sys;

import java.util.List;

import com.ibaixiong.entity.SysRole;

/**
 * @description
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年7月2日-下午1:19:30
 */
public interface SysRoleService {
	List<SysRole> getAllRoleList();

	SysRole getRoseById(Long id);
	/**
	 * 获取查询页面的角色列表
	 * @author zhaolei
	 * @date 2015年7月10日
	 * @param queryName 查询条件
	 * @return
	 */
	List<SysRole> querySysRoleList(String queryName);
	/**
	 * 删除角色
	 * @author zhaolei
	 * @date 2015年7月10日
	 * @param id
	 */
	void deleteSysRole(Long id);
	
	void insert(SysRole role);
	
	void update(SysRole role);
	
}
