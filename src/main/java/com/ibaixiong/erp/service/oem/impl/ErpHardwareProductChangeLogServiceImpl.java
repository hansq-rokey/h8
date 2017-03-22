package com.ibaixiong.erp.service.oem.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.ErpHardwareProductChangeLog;
import com.ibaixiong.erp.dao.oem.ErpHardwareProductChangeLogDao;
import com.ibaixiong.erp.service.oem.ErpHardwareProductChangeLogService;
import com.ibaixiong.erp.util.InvalidEnum;

@Service
public class ErpHardwareProductChangeLogServiceImpl implements
		ErpHardwareProductChangeLogService {

	@Resource
	private ErpHardwareProductChangeLogDao logDao;

	@Override
	public List<ErpHardwareProductChangeLog> queryLogsByHardwareId(
			Long hardwareId) {
		
		return logDao.queryLogsByHardwareId(hardwareId, InvalidEnum.FALSE.getInvalidValue());
	}
	
	@Override
	public void insert(ErpHardwareProductChangeLog log) {
		logDao.insertSelective(log);
	}
}
