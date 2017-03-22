/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.entity.SysRole;
import com.ibaixiong.erp.dao.sys.SysAdminDao;
import com.ibaixiong.erp.dao.sys.SysRoleDao;
import com.ibaixiong.erp.service.sys.SysAdminService;

/**
 * @description
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年7月2日-下午1:19:30
 */
@Service("sysAdminService")
public class SysAdminServiceImpl implements SysAdminService {

	@Resource
	SysAdminDao sysAdminDao;
	@Resource
	SysRoleDao sysRoleDao;

	public SysAdmin getAdminByLoginName(String loginName) {
		return sysAdminDao.selectByLoginName(loginName);
	}

	public SysAdmin getAdminById(Long id) {
		return sysAdminDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateAdmin(SysAdmin sysAdmin) {
		return sysAdminDao.updateByPrimaryKeySelective(sysAdmin);
	}

	@Override
	public int delectAdminById(Long id) {
		return sysAdminDao.deleteByPrimaryKey(id);
	}

	@Override
	public Long saveAdmin(SysAdmin admin) {
		return sysAdminDao.insertSelective(admin);
	}
	@Override
	public List<SysAdmin> querySysAdminList(Byte status, String queryName) {
		return sysAdminDao.querySysAdminList(status, queryName);
	}
	@Override
	public List<SysAdmin> queryAdminsByOrgIds(Long orgId) {
		return sysAdminDao.queryAdminsByOrgIds(orgId);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void insertBatch(){
		//try {
			SysAdmin admin1 = new SysAdmin();
			admin1.setLoginName("test");
			admin1.setPhone("11111111");
			sysAdminDao.insertSelective(admin1);
			System.out.println(admin1.getId());
			SysRole role = new SysRole();
			role.setName("test1111");
			sysRoleDao.insertSelective(role);
			SysAdmin admin2 = new SysAdmin();
			admin2.setLoginName("test1");
			admin2.setPhone("11111111111111111111111111111111111");
			sysAdminDao.insertSelective(admin2);
			SysRole role2 = new SysRole();
			role2.setName("test11112111111111211111111112222222");
			//sysRoleDao.insertSelective(role2);
		//} catch (Exception e) {
		//	throw new RuntimeException();
		//}
	}
}
