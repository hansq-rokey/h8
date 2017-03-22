package com.ibaixiong.erp.dao.sys;

import java.util.List;

import com.ibaixiong.entity.SysAdminRole;

public interface SysAdminRoleDao {
	void deleteAdminRoleByAdmin(Long adminId);
	void insertAdminRole(SysAdminRole adminRole);
	List<SysAdminRole> getSysAdminRoleByAdmin(Long adminId);
	/**
	 * 获取角色下的人员数量
	 * @author zhaolei
	 * @date 2015年7月10日
	 * @param roleId
	 * @return
	 */
	Integer getAdminCountByRoleId(Long roleId);
	List<SysAdminRole> getSysAdminRoleByRoleId(Long roleId);
}
