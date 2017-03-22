package com.ibaixiong.erp.service.oem.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.ErpHardwareTransportRelation;
import com.ibaixiong.erp.dao.oem.ErpHardwareTransportRelationDao;
import com.ibaixiong.erp.service.oem.ErpHardwareTransportRelationService;
@Service
public class ErpHardwareTransportRelationServiceImpl implements
		ErpHardwareTransportRelationService {
	@Resource
	private ErpHardwareTransportRelationDao erpHardwareTransportRelationServicedDao;

	@Override
	public void insert(ErpHardwareTransportRelation ehtr) {
		erpHardwareTransportRelationServicedDao.insertSelective(ehtr);
	}
	@Override
	public List<ErpHardwareTransportRelation> queryListByCodeId(Long codeId) {
		return erpHardwareTransportRelationServicedDao.queryListByCodeId(codeId);
	}
}
