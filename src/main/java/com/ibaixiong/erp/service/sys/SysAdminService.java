/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.sys;

import java.util.List;

import com.ibaixiong.entity.SysAdmin;

/**
 * @description
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年7月2日-下午1:19:30
 */
public interface SysAdminService {
	SysAdmin getAdminByLoginName(String loginName);

	SysAdmin getAdminById(Long id);

	int updateAdmin(SysAdmin sysAdmin);

	int delectAdminById(Long id);

	Long saveAdmin(SysAdmin admin);
	/**
	 * 查询用户 按照用户状态，用户登录名或姓名查
	 * @author zhaolei
	 * @date 2015年7月9日
	 * @param status
	 * @param queryName
	 * @return
	 */
	List<SysAdmin> querySysAdminList(Byte status,String queryName);
	/**
	 * 通过部门查询用户列表
	 * @author zhaolei
	 * @date 2015年9月1日
	 * @param orgId
	 * @return
	 */
	List<SysAdmin> queryAdminsByOrgIds(Long orgId);
	
	void insertBatch();
}
