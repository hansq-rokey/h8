package com.ibaixiong.erp.service.storage.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.ErpExpressCompany;
import com.ibaixiong.erp.dao.storage.ErpExpressCompanyDao;
import com.ibaixiong.erp.service.storage.ErpExpressCompanyService;
@Service
public class ErpExpressCompanyServiceImpl implements ErpExpressCompanyService {
	@Resource
	ErpExpressCompanyDao erpExpressCompanyDao;
	@Override
	public ErpExpressCompany get(Long id) {
		return erpExpressCompanyDao.selectByPrimaryKey(id);
	}
}
