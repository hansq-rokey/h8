package com.ibaixiong.erp.service.storage.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.MallOrderItemHardwareRelation;
import com.ibaixiong.erp.dao.storage.MallOrderItemHardwareRelationDao;
import com.ibaixiong.erp.service.storage.MallOrderItemHardwareRelationService;
@Service
public class MallOrderItemHardwareRelationServiceImpl implements
		MallOrderItemHardwareRelationService {
	@Resource
	private MallOrderItemHardwareRelationDao mallOrderItemHardwareRelationDao;
	@Override
	public void insert(MallOrderItemHardwareRelation dr) {
		mallOrderItemHardwareRelationDao.insertSelective(dr);
	}
}
