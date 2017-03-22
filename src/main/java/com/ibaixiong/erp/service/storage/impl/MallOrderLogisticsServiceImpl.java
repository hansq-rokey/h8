package com.ibaixiong.erp.service.storage.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.MallOrderLogistics;
import com.ibaixiong.erp.dao.storage.MallOrderLogisticsDao;
import com.ibaixiong.erp.service.storage.MallOrderLogisticsService;
@Service
public class MallOrderLogisticsServiceImpl implements MallOrderLogisticsService {
	@Resource
	private MallOrderLogisticsDao mallOrderLogisticsDao;
	@Override
	public void insert(MallOrderLogistics logistics) {
		mallOrderLogisticsDao.insertSelective(logistics);
	}
}
