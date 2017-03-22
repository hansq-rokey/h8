package com.ibaixiong.erp.service.sysset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.ErpBaseRole;
import com.ibaixiong.erp.dao.sysset.ErpBaseRoleDao;
import com.ibaixiong.erp.service.sysset.ErpBaseRoleService;
@Service
public class ErpBaseRoleServiceImpl implements ErpBaseRoleService {
	@Resource
	private ErpBaseRoleDao erpBaseRoleDao;
	@Override
	public ErpBaseRole getErpBaseRoleByType(String type) {
		return erpBaseRoleDao.getErpBaseRoleByType(type);
	}

}
