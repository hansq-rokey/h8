package com.ibaixiong.erp.service.storage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.ErpBom;
import com.ibaixiong.erp.dao.storage.ErpBomDao;
import com.ibaixiong.erp.service.storage.ErpBomService;

@Service
public class ErpBomServiceImpl implements ErpBomService {

	@Resource
	private ErpBomDao erpBomDao;
	
	public List<ErpBom> getListByOrderNumber(String orderNumber,Integer pageNo) {
		PageHelper.startPage(pageNo, PageConstant.pageSize, true);
		return erpBomDao.getListByOrderNumber(orderNumber);
	}

	@Override
	public void delete(Long bomId) {
		erpBomDao.deleteByPrimaryKey(bomId);
	}

}
