package com.ibaixiong.erp.service.sys;

import java.util.List;

import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.entity.SysAdminRole;

public interface SysAdminRoleService {
	/**
	 * 删除用户角色中间表数据，按照用户ID删除
	 * @author zhaolei
	 * @date 2015年7月9日
	 * @param admin
	 */
	void deleteAdminRoleByAdmin(SysAdmin admin);
	/**
	 * 添加用户角色中间表数据
	 * @author zhaolei
	 * @date 2015年7月9日
	 * @param admin
	 * @param role
	 */
	void insertAdminRole(SysAdminRole adminRole);
	/**
	 * 通过用户查找
	 * @author zhaolei
	 * @date 2015年7月9日
	 * @param admin
	 * @return
	 */
	List<SysAdminRole> getSysAdminRoleByAdmin(SysAdmin admin);
	/**
	 * 检查角色中是否包含用户
	 * @author zhaolei
	 * @date 2015年7月10日
	 * @param roleId 角色ID
	 * @return
	 */
	boolean checkRoleIncludeAdmin(Long roleId);
	/**
	 * 通过角色查找用户
	 * @author zhaolei
	 * @date 2015年8月18日
	 * @param admin
	 * @return
	 */
	List<SysAdminRole> getSysAdminRoleByRoleId(Long roleId);
}
