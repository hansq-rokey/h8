package com.ibaixiong.erp.service.purchase.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.ErpPurchaseMaterial;
import com.ibaixiong.erp.dao.purchase.ErpPurchaseMaterialDao;
import com.ibaixiong.erp.service.purchase.ErpPurchaseMaterialService;
@Service
public class ErpPurchaseMaterialServiceImpl implements
		ErpPurchaseMaterialService {
	@Resource
	private ErpPurchaseMaterialDao erpPurchaseMaterialDao;
	@Override
	public List<ErpPurchaseMaterial> getList(Integer pageNo) {
		PageHelper page= new PageHelper();
		page.startPage(pageNo, PageConstant.pageSize, true);
		return erpPurchaseMaterialDao.getList();
	}

	@Override
	public void insert(ErpPurchaseMaterial order) {
		erpPurchaseMaterialDao.insertSelective(order);
	}

	@Override
	public void update(ErpPurchaseMaterial order) {
		erpPurchaseMaterialDao.updateByPrimaryKeySelective(order);
	}

	@Override
	public void delete(Long id) {
		erpPurchaseMaterialDao.deleteByPrimaryKey(id);
	}
	@Override
	public ErpPurchaseMaterial get(Long id) {
		return erpPurchaseMaterialDao.selectByPrimaryKey(id);
	}
}
