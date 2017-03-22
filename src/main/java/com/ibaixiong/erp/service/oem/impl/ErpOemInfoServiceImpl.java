package com.ibaixiong.erp.service.oem.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.ErpOemInfo;
import com.ibaixiong.erp.dao.oem.ErpOemInfoDao;
import com.ibaixiong.erp.service.oem.ErpOemInfoService;
@Service
public class ErpOemInfoServiceImpl implements ErpOemInfoService {
	@Resource
	private ErpOemInfoDao erpOemInfoDao;

	@Override
	public List<ErpOemInfo> getList(Integer pageNo) {
		PageHelper page= new PageHelper();
		page.startPage(pageNo, PageConstant.pageSize, true);
		return erpOemInfoDao.getList();
	}

	@Override
	public void insert(ErpOemInfo info) {
		erpOemInfoDao.insertSelective(info);
	}

	@Override
	public void update(ErpOemInfo info) {
		erpOemInfoDao.updateByPrimaryKeySelective(info);
	}
	
}
