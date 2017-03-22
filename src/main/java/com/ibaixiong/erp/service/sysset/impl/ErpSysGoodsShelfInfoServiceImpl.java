package com.ibaixiong.erp.service.sysset.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.ErpSysGoodsShelfInfo;
import com.ibaixiong.erp.dao.sysset.ErpSysGoodsShelfInfoDao;
import com.ibaixiong.erp.service.sysset.ErpSysGoodsShelfInfoService;
import com.papabear.product.api.CategoryQueryService;
@Service
public class ErpSysGoodsShelfInfoServiceImpl implements
		ErpSysGoodsShelfInfoService {
	@Resource
	private ErpSysGoodsShelfInfoDao erpSysGoodsShelfInfoDao;
	@Resource
	private CategoryQueryService categoryQueryService;
	@Override
	public List<ErpSysGoodsShelfInfo> getListByParentId(Long pid,Long storageId) {
		List<ErpSysGoodsShelfInfo> list = erpSysGoodsShelfInfoDao.getListByParentId(pid,storageId);
//		List<ErpSysGoodsShelfInfo> listRes = new  ArrayList<ErpSysGoodsShelfInfo>();
		if(list != null){
			for (ErpSysGoodsShelfInfo erpSysGoodsShelfInfo : list) {
				erpSysGoodsShelfInfo.setCategory(categoryQueryService.getCategoryById(erpSysGoodsShelfInfo.getCategoryId()));
				erpSysGoodsShelfInfo.setCategoryModel(categoryQueryService.getCategoryModelById(erpSysGoodsShelfInfo.getCategoryModelId()));
				erpSysGoodsShelfInfo.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpSysGoodsShelfInfo.getCategoryModelFormatId()));
//				listRes.add(erpSysGoodsShelfInfo);
			}
		}
		return list;
	}

	@Override
	public void insert(ErpSysGoodsShelfInfo shelfInfo) {
		erpSysGoodsShelfInfoDao.insertSelective(shelfInfo);
	}

	@Override
	public void update(ErpSysGoodsShelfInfo shelfInfo) {
		erpSysGoodsShelfInfoDao.updateByPrimaryKeySelective(shelfInfo);
	}
	@Override
	public ErpSysGoodsShelfInfo get(Long id) {
		ErpSysGoodsShelfInfo erpSysGoodsShelfInfo = erpSysGoodsShelfInfoDao.selectByPrimaryKey(id);
		erpSysGoodsShelfInfo.setCategory(categoryQueryService.getCategoryById(erpSysGoodsShelfInfo.getCategoryId()));
		erpSysGoodsShelfInfo.setCategoryModel(categoryQueryService.getCategoryModelById(erpSysGoodsShelfInfo.getCategoryModelId()));
		erpSysGoodsShelfInfo.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpSysGoodsShelfInfo.getCategoryModelFormatId()));
		return erpSysGoodsShelfInfo;
	}
	@Override
	public List<ErpSysGoodsShelfInfo> getListPageByParentId(Long pid,
			Long storageId, Integer pageNo) {
		PageHelper page= new PageHelper();
		page.startPage(pageNo, PageConstant.pageSize, true);
		List<ErpSysGoodsShelfInfo> list = erpSysGoodsShelfInfoDao.getListByParentId(pid,storageId);
//		List<ErpSysGoodsShelfInfo> listRes = new  ArrayList<ErpSysGoodsShelfInfo>();
		if(list != null){
			for (ErpSysGoodsShelfInfo erpSysGoodsShelfInfo : list) {
				erpSysGoodsShelfInfo.setCategory(categoryQueryService.getCategoryById(erpSysGoodsShelfInfo.getCategoryId()));
				erpSysGoodsShelfInfo.setCategoryModel(categoryQueryService.getCategoryModelById(erpSysGoodsShelfInfo.getCategoryModelId()));
				erpSysGoodsShelfInfo.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpSysGoodsShelfInfo.getCategoryModelFormatId()));
//				listRes.add(erpSysGoodsShelfInfo);
			}
		}
		return list;
	}
}
