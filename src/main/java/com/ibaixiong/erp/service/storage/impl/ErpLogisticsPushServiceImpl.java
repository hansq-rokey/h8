package com.ibaixiong.erp.service.storage.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.ErpLogisticsPush;
import com.ibaixiong.erp.dao.storage.ErpLogisticsPushDao;
import com.ibaixiong.erp.service.storage.ErpLogisticsPushService;
@Service
public class ErpLogisticsPushServiceImpl implements ErpLogisticsPushService{
	@Resource
	ErpLogisticsPushDao erpLogisticsPushDao;
	@Override
	public void insert(ErpLogisticsPush push) {
		erpLogisticsPushDao.insertSelective(push);
	}

}
