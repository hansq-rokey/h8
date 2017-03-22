package com.ibaixiong.erp.service.storage.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.MallOrderItemLogisticsRelation;
import com.ibaixiong.erp.dao.storage.MallOrderItemLogisticsRelationDao;
import com.ibaixiong.erp.service.storage.MallOrderItemLogisticsRelationService;

@Service
public class MallOrderItemLogisticsRelationServiceImpl implements MallOrderItemLogisticsRelationService{
	@Resource
	private MallOrderItemLogisticsRelationDao mallOrderItemLogisticsRelationDao;
	@Override
	public void insert(MallOrderItemLogisticsRelation relation) {
		mallOrderItemLogisticsRelationDao.insertSelective(relation);
	}
}
