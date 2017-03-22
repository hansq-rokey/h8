package com.ibaixiong.erp.service.sysset.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.ErpUserPermissionRelation;
import com.ibaixiong.erp.dao.sysset.ErpUserPermissionRelationDao;
import com.ibaixiong.erp.service.sysset.ErpUserPermissionRelationService;
import com.ibaixiong.erp.util.InvalidEnum;
import com.papabear.product.api.CategoryQueryService;
@Service
public class ErpUserPermissionRelationServiceImpl implements
		ErpUserPermissionRelationService {
	@Resource
	private ErpUserPermissionRelationDao erpUserPermissionRelationDao;
	@Resource
	CategoryQueryService categoryQueryService;
	@Override
	public List<ErpUserPermissionRelation> selectByAdminId(Long adminId,Integer pageNo) {
		PageHelper page= new PageHelper();
		page.startPage(pageNo, PageConstant.pageSize, true);
		List<ErpUserPermissionRelation> list = erpUserPermissionRelationDao.selectByAdminId(adminId, null);
//		List<ErpUserPermissionRelation> listRes = new  ArrayList<ErpUserPermissionRelation>();
		if(list != null){
			for (ErpUserPermissionRelation relation : list) {
				relation.setCategory(categoryQueryService.getCategoryById(relation.getCategoryId()));
				relation.setCategoryModel(categoryQueryService.getCategoryModelById(relation.getCategoryModelId()));
				relation.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(relation.getCategoryModelFormatId()));
//				listRes.add(relation);
			}
		}
		return list;
	}
	
	@Override
	public void insert(ErpUserPermissionRelation relation) {
		erpUserPermissionRelationDao.insertSelective(relation);
	}
	
	@Override
	public void updateInvalid(Long id, Byte invalid) {
		erpUserPermissionRelationDao.updateInvalid(id, invalid);
	}
	@Override
	public ErpUserPermissionRelation getCheck( Long batchId,
			Long adminId) {
		ErpUserPermissionRelation relation =  erpUserPermissionRelationDao.getCheck(batchId, adminId);
		relation.setCategory(categoryQueryService.getCategoryById(relation.getCategoryId()));
		relation.setCategoryModel(categoryQueryService.getCategoryModelById(relation.getCategoryModelId()));
		relation.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(relation.getCategoryModelFormatId()));
		return relation;
	}
	@Override
	public List<ErpUserPermissionRelation> selectByPurchaseOrderId(Long orderId) {
		List<ErpUserPermissionRelation> list = erpUserPermissionRelationDao.selectByPurchaseOrderId(orderId);
//		List<ErpUserPermissionRelation> listRes = new  ArrayList<ErpUserPermissionRelation>();
		if(list != null){
			for (ErpUserPermissionRelation relation : list) {
				relation.setCategory(categoryQueryService.getCategoryById(relation.getCategoryId()));
				relation.setCategoryModel(categoryQueryService.getCategoryModelById(relation.getCategoryModelId()));
				relation.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(relation.getCategoryModelFormatId()));
//				listRes.add(relation);
			}
		}
		return list;
	}
}
