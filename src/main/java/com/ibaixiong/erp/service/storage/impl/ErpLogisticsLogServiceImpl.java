package com.ibaixiong.erp.service.storage.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.ErpLogisticsLog;
import com.ibaixiong.erp.dao.storage.ErpLogisticsLogDao;
import com.ibaixiong.erp.service.storage.ErpLogisticsLogService;
@Service
public class ErpLogisticsLogServiceImpl implements ErpLogisticsLogService {
	@Resource
	private ErpLogisticsLogDao erpLogisticsLogDao;
	@Override
	public void insert(ErpLogisticsLog log) {
		erpLogisticsLogDao.insertSelective(log);
	}

	@Override
	public void update(ErpLogisticsLog log) {
		erpLogisticsLogDao.updateByPrimaryKey(log);
	}

}
