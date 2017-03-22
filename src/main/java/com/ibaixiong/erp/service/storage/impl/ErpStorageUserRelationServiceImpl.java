package com.ibaixiong.erp.service.storage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.ErpStorageUserRelation;
import com.ibaixiong.erp.dao.storage.ErpStorageUserRelationDao;
import com.ibaixiong.erp.service.storage.ErpStorageUserRelationService;
@Service
public class ErpStorageUserRelationServiceImpl implements
		ErpStorageUserRelationService {
	@Resource
	private ErpStorageUserRelationDao erpStorageUserRelationDao;
	@Override
	public void insert(ErpStorageUserRelation userRelation) {
		erpStorageUserRelationDao.insertSelective(userRelation);
	}
	@Override
	public List<ErpStorageUserRelation> getListByStorageId(Long storageId,Integer pageNo) {
		PageHelper page= new PageHelper();
		page.startPage(pageNo, PageConstant.pageSize, true);
		return erpStorageUserRelationDao.getListByStorageId(storageId);
	}
}
