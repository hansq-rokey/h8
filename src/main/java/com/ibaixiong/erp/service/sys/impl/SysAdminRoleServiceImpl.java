package com.ibaixiong.erp.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.entity.SysAdminRole;
import com.ibaixiong.erp.dao.sys.SysAdminRoleDao;
import com.ibaixiong.erp.service.sys.SysAdminRoleService;

@Service
public class SysAdminRoleServiceImpl implements SysAdminRoleService {
	@Resource
	private SysAdminRoleDao sysAdminRoleDao;
	@Override
	public void deleteAdminRoleByAdmin(SysAdmin admin) {
		if(admin != null && admin.getId().intValue()>0)
			sysAdminRoleDao.deleteAdminRoleByAdmin(admin.getId());
	}

	@Override
	public void insertAdminRole(SysAdminRole adminRole) {
		sysAdminRoleDao.insertAdminRole(adminRole);
	}

	@Override
	public List<SysAdminRole> getSysAdminRoleByAdmin(SysAdmin admin) {
		if(admin != null)
			return sysAdminRoleDao.getSysAdminRoleByAdmin(admin.getId());
		else
			return null;
	}

	@Override
	public boolean checkRoleIncludeAdmin(Long roleId) {
		Integer count = sysAdminRoleDao.getAdminCountByRoleId(roleId);
		if(count>0)//找到了操作员在角色下面
			return true;
		return false;
	}
	
	@Override
	public List<SysAdminRole> getSysAdminRoleByRoleId(Long roleId) {
		return sysAdminRoleDao.getSysAdminRoleByRoleId(roleId);
	}

}
