package com.ibaixiong.erp.service.storage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.ErpSysStorage;
import com.ibaixiong.erp.dao.storage.ErpSysStorageDao;
import com.ibaixiong.erp.service.storage.ErpSysStorageService;
@Service
public class ErpSysStorageServiceImpl implements ErpSysStorageService {
	@Resource
	private ErpSysStorageDao erpSysStorageDao;
	@Override
	public List<ErpSysStorage> getList(Integer pageNo) {
		PageHelper page= new PageHelper();
		page.startPage(pageNo, PageConstant.pageSize, true);
		return erpSysStorageDao.getList();
	}
	@Override
	public void insert(ErpSysStorage stor) {
		erpSysStorageDao.insertSelective(stor);
	}
	@Override
	public void update(ErpSysStorage stor) {
		erpSysStorageDao.updateByPrimaryKeySelective(stor);
	}
	@Override
	public ErpSysStorage get(Long id) {
		return erpSysStorageDao.selectByPrimaryKey(id);
	}
}
