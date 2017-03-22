package com.ibaixiong.erp.service.sysset.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.ErpBatchDetails;
import com.ibaixiong.erp.dao.sysset.ErpBatchDetailsDao;
import com.ibaixiong.erp.service.sysset.ErpBatchDetailsService;
@Service
public class ErpBatchDetailsServiceImpl implements ErpBatchDetailsService {
	@Resource
	ErpBatchDetailsDao erpBatchDetailsDao;
	@Override
	public void insert(ErpBatchDetails batch) {
		erpBatchDetailsDao.insertSelective(batch);
	}

	@Override
	public void deleteByOrderId(Long id) {
		erpBatchDetailsDao.deleteByOrderId(id);
	}
	@Override
	public List<ErpBatchDetails> getListByBatchId(Long batchId,Byte invalid) {
		return erpBatchDetailsDao.getListByBatchId(batchId,invalid);
	}
	@Override
	public void updateInvalid(Long id, Byte invalid) {
		erpBatchDetailsDao.updateInvalid(id, invalid);
	}
	@Override
	public ErpBatchDetails get(Long id) {
		return erpBatchDetailsDao.selectByPrimaryKey(id);
	}
	@Override
	public ErpBatchDetails getByOrderId(Long id) {
		return erpBatchDetailsDao.getByOrderId(id);
	}
	@Override
	public List<ErpBatchDetails> getListPageByBatchId(Long batchId,
			Byte invalid, Integer pageNo) {
		PageHelper page= new PageHelper();
		page.startPage(pageNo, PageConstant.pageSize, true);
		return erpBatchDetailsDao.getListByBatchId(batchId,invalid);
	}
}
