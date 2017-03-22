/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.oem.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibaixiong.core.utils.CodeUtil;
import com.ibaixiong.entity.ErpHardwareProduct;
import com.ibaixiong.entity.ErpHardwareProductChangeLog;
import com.ibaixiong.entity.ErpOemUserRelation;
import com.ibaixiong.entity.ErpSecurityCodeMacRelation;
import com.ibaixiong.entity.ErpUserPermissionRelation;
import com.ibaixiong.entity.MallOrderItemHardwareRelation;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.dao.oem.ErpHardwareProductDao;
import com.ibaixiong.erp.dao.storage.MallOrderItemHardwareRelationDao;
import com.ibaixiong.erp.dao.sysset.ErpBaseBatchDao;
import com.ibaixiong.erp.dao.sysset.ErpUserPermissionRelationDao;
import com.ibaixiong.erp.service.oem.ErpHardwareProductChangeLogService;
import com.ibaixiong.erp.service.oem.ErpHardwareProductService;
import com.ibaixiong.erp.service.oem.ErpOemScanPackageService;
import com.ibaixiong.erp.service.storage.MallOrderService;
import com.ibaixiong.erp.util.HardwareProductNetStatusEnum;
import com.ibaixiong.erp.util.InvalidEnum;
import com.papabear.order.entity.MallOrderItem;
import com.papabear.order.entity.OrderConstant.OrderStatus;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.api.ProductQueryService;
import com.papabear.product.entity.MallBasicCategory;
import com.papabear.product.entity.MallBasicCategoryModel;
import com.papabear.product.entity.MallBasicCategoryModelFormat;

/**
 * @description
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年9月29日-下午7:54:10
 */
@Service
public class ErpOemScanPackageServiceImpl implements ErpOemScanPackageService {

	@Autowired
	ErpUserPermissionRelationDao erpUserPermissionRelationDao;
	@Autowired
	ErpHardwareProductService erpHardwareProductService;
	@Resource
	ErpHardwareProductDao erpHardwareProductDao;
	@Resource
	CategoryQueryService categoryQueryService;
	@Autowired
	ErpHardwareProductChangeLogService erpHardwareProductChangeLogService;
	@Autowired
	MallOrderService mallOrderService;
	@Autowired
	MallOrderItemHardwareRelationDao mallOrderItemHardwareRelationDao;
	@Autowired
	ErpBaseBatchDao erpBaseBatchDao;
	@Autowired
	ProductQueryService productQueryService;
	private static String CUSTOMMADEBATCHNUMBER = "000";

	@Override
	public List<ErpUserPermissionRelation> getListErpUserPermissionRelation(Long adminId) {
		List<ErpUserPermissionRelation> list = erpUserPermissionRelationDao.selectByAdminId(adminId, InvalidEnum.FALSE.getInvalidValue());
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
	public List<ErpUserPermissionRelation> getListErpUserPermissionRelationByAdminIdAndFormatId(Long adminId, Long formatId) {
		List<ErpUserPermissionRelation> list = erpUserPermissionRelationDao.selectByAdminIdAndFormatId(adminId, formatId, InvalidEnum.FALSE.getInvalidValue());
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
	
	/* (non-Javadoc)
	 * @see com.ibaixiong.erp.service.oem.ErpOemScanPackageService#getListErpUserPermissionRelationByFormatId(java.lang.Long)
	 */
	@Override
	public List<ErpUserPermissionRelation> getListErpUserPermissionRelationByFormatId(Long formatId) {
		List<ErpUserPermissionRelation> list = erpUserPermissionRelationDao.selectByFormatId(formatId, InvalidEnum.FALSE.getInvalidValue());
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
	public ErpUserPermissionRelation getErpUserPermissionRelationBySelectId(Long selectId) {
		ErpUserPermissionRelation relation = erpUserPermissionRelationDao.selectByPrimaryKey(selectId);
		if(relation != null){
			relation.setCategory(categoryQueryService.getCategoryById(relation.getCategoryId()));
			relation.setCategoryModel(categoryQueryService.getCategoryModelById(relation.getCategoryModelId()));
			relation.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(relation.getCategoryModelFormatId()));
		}
		return relation;
	}
	
	@Override
	@Transactional
	public String scanPackageCode(String macNumber,ErpSecurityCodeMacRelation relation,SysAdmin admin) throws Exception {
		if (macNumber.length() != 16) {
			throw new Exception("扫描到的Mac地址不符合规范!");
		}
		// 查检该Mac地址是否已经存在
		ErpHardwareProduct erpHardwareProductDb = erpHardwareProductService.getErpHardwareProductByMacNumber(macNumber);
		if (erpHardwareProductDb != null) {
			throw new Exception("该Mac地址已经存在!");
		}
		MallBasicCategoryModelFormat format = categoryQueryService.getCategoryModelFormat(relation.getFormatId());
		MallBasicCategoryModel model = categoryQueryService.getCategoryModelById(relation.getModelId());
		MallBasicCategory category = categoryQueryService.getCategoryById(model.getCategoryId());
		String uniqueCode = CodeUtil.getUniqueCode();
		ErpHardwareProduct erpHardwareProductInsert = new ErpHardwareProduct();
		erpHardwareProductInsert.setUniqueCode(uniqueCode);
		erpHardwareProductInsert.setBatch(relation.getBatch());
		erpHardwareProductInsert.setCategoryCode(category.getCode());
		erpHardwareProductInsert.setCategoryId(category.getId());
		erpHardwareProductInsert.setCategoryModelCode(model.getCode());
		erpHardwareProductInsert.setCategoryModelFormatCode(format.getCode());
		erpHardwareProductInsert.setCategoryModelFormatId(relation.getFormatId());
		erpHardwareProductInsert.setCategoryModelId(relation.getModelId());
		erpHardwareProductInsert.setCode(format.getCategoryModelFormatNumber()
				+ relation.getBatch() + macNumber);
		erpHardwareProductInsert.setCreateDateTime(new Date());
		erpHardwareProductInsert.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		erpHardwareProductInsert.setIsSmart(format.getIsSmart());
		erpHardwareProductInsert.setMacAddress(macNumber);
		erpHardwareProductInsert.setMfgDateTime(new Date());
//		erpHardwareProductInsert.setOemCode(oem.getOem().getOemCode());
//		erpHardwareProductInsert.setOemInfoId(oem.getOem().getId());
//		erpHardwareProductInsert.setOemInfoName(oem.getOem().getName());
		erpHardwareProductInsert.setProductCode(format.getCategoryModelFormatNumber()
				+ relation.getBatch());
		erpHardwareProductInsert.setStatus(HardwareProductNetStatusEnum.QRCODE.getCode());
		erpHardwareProductInsert.setUpdateTime(new Date());
		erpHardwareProductInsert.setIsCustomMade((byte) 0);
		erpHardwareProductInsert.setAdminId(admin.getId());
		erpHardwareProductDao.insert(erpHardwareProductInsert);

		ErpHardwareProductChangeLog changeLog = new ErpHardwareProductChangeLog();
		changeLog.setCreateDateTime(new Date());
		changeLog.setHardwareProductId(erpHardwareProductInsert.getId());
		changeLog.setInvalid(false);
		changeLog.setOperateExplain("防伪码生成");
		changeLog.setOperateId(admin.getId());
		changeLog.setStatus((byte) 1);
		changeLog.setUserId(admin.getId());
		changeLog.setUserName(admin.getUserName());
		erpHardwareProductChangeLogService.insert(changeLog);
		return uniqueCode;
	}

	@Override
	@Transactional
	public String scanPackage(String macNumber, ErpUserPermissionRelation select, ErpOemUserRelation oem) throws Exception {
		if (macNumber.length() != 16) {
			throw new Exception("扫描到的Mac地址不符合规范!");
		}
		// 查检该Mac地址是否已经存在
		ErpHardwareProduct erpHardwareProductDb = erpHardwareProductService.getErpHardwareProductByMacNumber(macNumber);
		if (erpHardwareProductDb != null) {
			throw new Exception("该Mac地址已经存在!");
		}

		String uniqueCode = CodeUtil.getUniqueCode();
		ErpHardwareProduct erpHardwareProductInsert = new ErpHardwareProduct();
		erpHardwareProductInsert.setUniqueCode(uniqueCode);
		erpHardwareProductInsert.setBatch(select.getBatchDetail().getBatchNumber());
		erpHardwareProductInsert.setCategoryCode(select.getCategory().getCode());
		erpHardwareProductInsert.setCategoryId(select.getCategory().getId());
		erpHardwareProductInsert.setCategoryModelCode(select.getCategoryModel().getCode());
		erpHardwareProductInsert.setCategoryModelFormatCode(select.getCategoryModelFormat().getCode());
		erpHardwareProductInsert.setCategoryModelFormatId(select.getCategoryModelFormat().getId());
		erpHardwareProductInsert.setCategoryModelId(select.getCategoryModel().getId());
		erpHardwareProductInsert.setCode(select.getCategoryModelFormat().getCategoryModelFormatNumber() + oem.getOem().getOemCode()
				+ select.getBatchDetail().getBatchNumber() + macNumber);
		erpHardwareProductInsert.setCreateDateTime(new Date());
		erpHardwareProductInsert.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		erpHardwareProductInsert.setIsSmart(CodeUtil.isSmart(macNumber) ? (byte) 1 : (byte) 0);
		erpHardwareProductInsert.setMacAddress(macNumber);
		erpHardwareProductInsert.setMfgDateTime(new Date());
		erpHardwareProductInsert.setOemCode(oem.getOem().getOemCode());
		erpHardwareProductInsert.setOemInfoId(oem.getOem().getId());
		erpHardwareProductInsert.setOemInfoName(oem.getOem().getName());
		erpHardwareProductInsert.setProductCode(select.getCategoryModelFormat().getCategoryModelFormatNumber() + oem.getOem().getOemCode()
				+ select.getBatchDetail().getBatchNumber());
		erpHardwareProductInsert.setStatus(HardwareProductNetStatusEnum.QRCODE.getCode());
		erpHardwareProductInsert.setUpdateTime(new Date());
		erpHardwareProductInsert.setIsCustomMade((byte) 0);
		erpHardwareProductInsert.setAdminId(select.getAdmin().getId());
		erpHardwareProductDao.insert(erpHardwareProductInsert);

		ErpHardwareProductChangeLog changeLog = new ErpHardwareProductChangeLog();
		changeLog.setCreateDateTime(new Date());
		changeLog.setHardwareProductId(erpHardwareProductInsert.getId());
		changeLog.setInvalid(false);
		changeLog.setOperateExplain("防伪码生成");
		changeLog.setOperateId(select.getAdmin().getId());
		changeLog.setStatus((byte) 1);
		changeLog.setUserId(select.getAdmin().getId());
		changeLog.setUserName(select.getAdmin().getUserName());
		erpHardwareProductChangeLogService.insert(changeLog);
		return uniqueCode;
	}

	@Override
	@Transactional
	public String scanPackageCustomMade(String macNumber, MallOrderItem orderItem, ErpOemUserRelation oem, String ip) throws Exception {
		if (macNumber.length() != 16) {
			throw new Exception("扫描到的Mac地址不符合规范!");
		}
		// 查检该Mac地址是否已经存在
		ErpHardwareProduct erpHardwareProductDb = erpHardwareProductService.getErpHardwareProductByMacNumber(macNumber);
		if (erpHardwareProductDb != null) {
			throw new Exception("该Mac地址已经存在!");
		}
		String uniqueCode = CodeUtil.getUniqueCode();
		ErpHardwareProduct erpHardwareProductInsert = new ErpHardwareProduct();
		erpHardwareProductInsert.setUniqueCode(uniqueCode);
		erpHardwareProductInsert.setBatch(CUSTOMMADEBATCHNUMBER);
//		MallBasicCategoryModelFormat format = orderItem.getFormat();
		MallBasicCategoryModelFormat format = categoryQueryService.getCategoryModelFormat(orderItem.getProductModelFormatId());
		MallBasicCategoryModel categoryModel = format.getBasicCategoryModel();
		MallBasicCategory category = categoryModel.getCategory();
		erpHardwareProductInsert.setCategoryCode(category.getCode());
		erpHardwareProductInsert.setCategoryId(category.getId());
		erpHardwareProductInsert.setCategoryModelCode(categoryModel.getCode());
		erpHardwareProductInsert.setCategoryModelId(categoryModel.getId());
		erpHardwareProductInsert.setCategoryModelFormatCode(format.getCode());
		erpHardwareProductInsert.setCategoryModelFormatId(format.getId());
		erpHardwareProductInsert.setCode(format.getCategoryModelFormatNumber() + oem.getOem().getOemCode() + CUSTOMMADEBATCHNUMBER + macNumber);
		erpHardwareProductInsert.setCreateDateTime(new Date());
		erpHardwareProductInsert.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		erpHardwareProductInsert.setIsSmart(CodeUtil.isSmart(macNumber) ? (byte) 1 : (byte) 0);
		erpHardwareProductInsert.setMacAddress(macNumber);
		erpHardwareProductInsert.setMfgDateTime(new Date());
		erpHardwareProductInsert.setOemCode(oem.getOem().getOemCode());
		erpHardwareProductInsert.setOemInfoId(oem.getOem().getId());
		erpHardwareProductInsert.setOemInfoName(oem.getOem().getName());
		erpHardwareProductInsert.setProductCode(format.getCategoryModelFormatNumber() + oem.getOem().getOemCode() + CUSTOMMADEBATCHNUMBER);
		erpHardwareProductInsert.setStatus(HardwareProductNetStatusEnum.QRCODE.getCode());
		erpHardwareProductInsert.setUpdateTime(new Date());
		erpHardwareProductInsert.setAdminId(oem.getAdmin().getId());
		erpHardwareProductInsert.setIsCustomMade((byte) 1);
		erpHardwareProductDao.insert(erpHardwareProductInsert);

		ErpHardwareProductChangeLog changeLog = new ErpHardwareProductChangeLog();
		changeLog.setCreateDateTime(new Date());
		changeLog.setHardwareProductId(erpHardwareProductInsert.getId());
		changeLog.setInvalid(false);
		changeLog.setOperateExplain("防伪码生成");
		changeLog.setOperateId(oem.getAdmin().getId());
		changeLog.setStatus((byte) 1);
		changeLog.setUserId(oem.getAdmin().getId());
		changeLog.setUserName(oem.getAdmin().getUserName());
		erpHardwareProductChangeLogService.insert(changeLog);

		// 绑定硬件和Item关系
		MallOrderItemHardwareRelation mallOrderItemHardwareRelation = new MallOrderItemHardwareRelation();
		mallOrderItemHardwareRelation.setAdminId(oem.getAdmin().getId());
		mallOrderItemHardwareRelation.setCategoryModelFormatId(format.getId());
		mallOrderItemHardwareRelation.setCreateDateTime(new Date());
		mallOrderItemHardwareRelation.setHardwareProductId(erpHardwareProductInsert.getId());
		mallOrderItemHardwareRelation.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		mallOrderItemHardwareRelation.setIsCustomMade((byte) 1);
		mallOrderItemHardwareRelation.setOrderItemId(orderItem.getId());
		mallOrderItemHardwareRelation.setUniqueCode(uniqueCode);
		mallOrderItemHardwareRelationDao.insert(mallOrderItemHardwareRelation);

		// 更新状态为组装中
		mallOrderService.changeCustomMadeOrder(OrderStatus.CUSTOM_MADING, orderItem, oem.getAdmin().getId(), ip);

		return uniqueCode;
	}
	@Override
	@Transactional
	public String scanPackageCustom(String macNumber, MallOrderItem orderItem, ErpSecurityCodeMacRelation relation, SysAdmin admin,String ip) throws Exception {
		if (macNumber.length() != 16) {
			throw new Exception("扫描到的Mac地址不符合规范!");
		}
		// 查检该Mac地址是否已经存在
		ErpHardwareProduct erpHardwareProductDb = erpHardwareProductService.getErpHardwareProductByMacNumber(macNumber);
		if (erpHardwareProductDb != null) {
			throw new Exception("该Mac地址已经存在!");
		}
		String uniqueCode = CodeUtil.getUniqueCode();
		ErpHardwareProduct erpHardwareProductInsert = new ErpHardwareProduct();
		erpHardwareProductInsert.setUniqueCode(uniqueCode);
		erpHardwareProductInsert.setBatch(relation.getBatch());
//		MallBasicCategoryModelFormat format = orderItem.getFormat();
		MallBasicCategoryModelFormat format = categoryQueryService.getCategoryModelFormat(relation.getFormatId());
		MallBasicCategoryModel model = categoryQueryService.getCategoryModelById(relation.getModelId());
		MallBasicCategory category = categoryQueryService.getCategoryById(model.getCategoryId());
		erpHardwareProductInsert.setCategoryCode(category.getCode());
		erpHardwareProductInsert.setCategoryId(category.getId());
		erpHardwareProductInsert.setCategoryModelCode(model.getCode());
		erpHardwareProductInsert.setCategoryModelId(model.getId());
		erpHardwareProductInsert.setCategoryModelFormatCode(format.getCode());
		erpHardwareProductInsert.setCategoryModelFormatId(format.getId());
		erpHardwareProductInsert.setCode(format.getCategoryModelFormatNumber() + relation.getBatch() + CUSTOMMADEBATCHNUMBER + macNumber);
		erpHardwareProductInsert.setCreateDateTime(new Date());
		erpHardwareProductInsert.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		erpHardwareProductInsert.setIsSmart(CodeUtil.isSmart(macNumber) ? (byte) 1 : (byte) 0);
		erpHardwareProductInsert.setMacAddress(macNumber);
		erpHardwareProductInsert.setMfgDateTime(new Date());
//		erpHardwareProductInsert.setOemCode(oem.getOem().getOemCode());
//		erpHardwareProductInsert.setOemInfoId(oem.getOem().getId());
//		erpHardwareProductInsert.setOemInfoName(oem.getOem().getName());
		erpHardwareProductInsert.setProductCode(format.getCategoryModelFormatNumber() + relation.getBatch() + CUSTOMMADEBATCHNUMBER);
		erpHardwareProductInsert.setStatus(HardwareProductNetStatusEnum.QRCODE.getCode());
		erpHardwareProductInsert.setUpdateTime(new Date());
		erpHardwareProductInsert.setAdminId(admin.getId());
		erpHardwareProductInsert.setIsCustomMade((byte) 1);
		erpHardwareProductDao.insert(erpHardwareProductInsert);

		ErpHardwareProductChangeLog changeLog = new ErpHardwareProductChangeLog();
		changeLog.setCreateDateTime(new Date());
		changeLog.setHardwareProductId(erpHardwareProductInsert.getId());
		changeLog.setInvalid(false);
		changeLog.setOperateExplain("打印唯一码");
		changeLog.setOperateId(admin.getId());
		changeLog.setStatus((byte) 1);
		changeLog.setUserId(admin.getId());
		changeLog.setUserName(admin.getUserName());
		erpHardwareProductChangeLogService.insert(changeLog);

		// 绑定硬件和Item关系
		MallOrderItemHardwareRelation mallOrderItemHardwareRelation = new MallOrderItemHardwareRelation();
		mallOrderItemHardwareRelation.setAdminId(admin.getId());
		mallOrderItemHardwareRelation.setCategoryModelFormatId(format.getId());
		mallOrderItemHardwareRelation.setCreateDateTime(new Date());
		mallOrderItemHardwareRelation.setHardwareProductId(erpHardwareProductInsert.getId());
		mallOrderItemHardwareRelation.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		mallOrderItemHardwareRelation.setIsCustomMade((byte) 1);
		mallOrderItemHardwareRelation.setOrderItemId(orderItem.getId());
		mallOrderItemHardwareRelation.setUniqueCode(uniqueCode);
		mallOrderItemHardwareRelationDao.insert(mallOrderItemHardwareRelation);

		// 更新状态为组装中
		mallOrderService.changeCustomMadeOrder(OrderStatus.CUSTOM_MADING, orderItem, admin.getId(), ip);

		return uniqueCode;
	}
	@Override
	@Transactional
	public void deleteCustomMade(MallOrderItem orderItem) {
		MallOrderItemHardwareRelation mallOrderItemHardwareRelation = mallOrderItemHardwareRelationDao.selectByOrderItemId(orderItem.getId());
		if (mallOrderItemHardwareRelation == null) {
			return;
		}
		erpHardwareProductDao.deleteByPrimaryKey(mallOrderItemHardwareRelation.getHardwareProductId());
		mallOrderItemHardwareRelationDao.deleteByPrimaryKey(mallOrderItemHardwareRelation.getId());
	}

	@Override
	@Transactional
	public void deleteCustomMadeRelation(MallOrderItem orderItem) {
		MallOrderItemHardwareRelation mallOrderItemHardwareRelation = mallOrderItemHardwareRelationDao.selectByOrderItemId(orderItem.getId());
		if (mallOrderItemHardwareRelation == null) {
			return;
		}
//		erpHardwareProductDao.deleteByPrimaryKey(mallOrderItemHardwareRelation.getHardwareProductId());
		mallOrderItemHardwareRelationDao.deleteByPrimaryKey(mallOrderItemHardwareRelation.getId());
	}
	@Override
	public List<MallBasicCategoryModel> getMallBasicCategoryModelByAdminId(Long adminId) {
		List<Long> listCategoryModelId = erpUserPermissionRelationDao.selectCategoryModelIdByAdminId(adminId, InvalidEnum.FALSE.getInvalidValue());
		List<MallBasicCategoryModel> listMallBasicCategoryModel = categoryQueryService.queryCategoryModelByIds(listCategoryModelId);
		return listMallBasicCategoryModel;
	}
	
	/* 
	 * 查询所有的MallBasicCategoryModel
	 * (non-Javadoc)
	 * @see com.ibaixiong.erp.service.oem.ErpOemScanPackageService#getMallBasicCategoryModel()
	 */
	@Override
	public List<MallBasicCategoryModel> getMallBasicCategoryModel() {
//		List<Long> listCategoryModelId = erpUserPermissionRelationDao.selectCategoryModelId(InvalidEnum.FALSE.getInvalidValue());
//		List<MallBasicCategoryModel> listMallBasicCategoryModel = categoryQueryService.queryCategoryModelByIds(listCategoryModelId);
		List<MallBasicCategoryModel> listMallBasicCategoryModel =categoryQueryService.queryAllCategoryModel();
		return listMallBasicCategoryModel;
	}

	@Override
	public List<MallBasicCategoryModelFormat> getMallBasicCategoryModelFormatByAdminId(Long adminId, Long categoryModelId) {
		List<Long> listCategoryModelFormatId = erpUserPermissionRelationDao.selectCategoryModelFormatIdByAdminId(adminId, categoryModelId,
				InvalidEnum.FALSE.getInvalidValue());
		List<MallBasicCategoryModelFormat> listMallBasicCategoryModelFormat = categoryQueryService.queryCategoryModelFormatByIds(listCategoryModelFormatId);
		return listMallBasicCategoryModelFormat;
	}
	
	/*
	 * 查询所有的MallBasicCategoryModelFormat
	 * (non-Javadoc)
	 * @see com.ibaixiong.erp.service.oem.ErpOemScanPackageService#getMallBasicCategoryModel()
	 */
	@Override
	public List<MallBasicCategoryModelFormat> getMallBasicCategoryModelFormat(Long categoryModelId) {
//		List<Long> listCategoryModelFormatId = erpUserPermissionRelationDao.selectCategoryModelFormatId(categoryModelId,
//				InvalidEnum.FALSE.getInvalidValue());
//		List<MallBasicCategoryModelFormat> listMallBasicCategoryModelFormat = categoryQueryService.queryCategoryModelFormatByIds(listCategoryModelFormatId);
		List<MallBasicCategoryModelFormat> listMallBasicCategoryModelFormat = categoryQueryService.queryByCategoryModel(categoryModelId);
		for(MallBasicCategoryModelFormat f:listMallBasicCategoryModelFormat){
			f.setPics(productQueryService.queryPics(null, f.getId(), (short)2));
		}
		return listMallBasicCategoryModelFormat;
	}

}
