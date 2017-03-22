package com.ibaixiong.erp.service.sysset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.ErpBaseBatch;
import com.ibaixiong.erp.dao.sysset.ErpBaseBatchDao;
import com.ibaixiong.erp.service.sysset.ErpBaseBatchService;
@Service
public class ErpBaseBatchServiceImpl implements ErpBaseBatchService {
	@Resource
	private ErpBaseBatchDao erpBaseBatchDao;
	@Override
	public ErpBaseBatch selectByBatch(Long categoryId, Long categoryModelId,
			Long categoryModelFormatId) {
		return erpBaseBatchDao.selectByBatch(categoryId, categoryModelId, categoryModelFormatId);
	}

	@Override
	public void updateIndexNum(Long categoryId, Long categoryModelId,
			Long categoryModelFormatId) {
		erpBaseBatchDao.updateIndexNum(categoryId, categoryModelId, categoryModelFormatId);
	}

	@Override
	public void insert(ErpBaseBatch batch) {
		erpBaseBatchDao.insertSelective(batch);
	}

}
