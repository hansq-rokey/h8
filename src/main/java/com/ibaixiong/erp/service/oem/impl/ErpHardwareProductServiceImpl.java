package com.ibaixiong.erp.service.oem.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.ErpHardwareProduct;
import com.ibaixiong.erp.dao.oem.ErpHardwareProductDao;
import com.ibaixiong.erp.service.oem.ErpHardwareProductService;
import com.ibaixiong.erp.util.InvalidEnum;
import com.papabear.product.api.CategoryQueryService;

@Service
public class ErpHardwareProductServiceImpl implements ErpHardwareProductService {

	@Resource
	private ErpHardwareProductDao erpHardwareProductDao;
	@Resource
	private CategoryQueryService categoryQueryService;

	@Override
	public List<ErpHardwareProduct> queryErpHardwareProductByTransportCodeId(Long transportCodeId) {
		List<ErpHardwareProduct> list = erpHardwareProductDao.queryErpHardwareProductByTransportId(transportCodeId, InvalidEnum.FALSE.getInvalidValue());
//		List<ErpHardwareProduct> listRes = new ArrayList<ErpHardwareProduct>();
		if(list != null){
			for (ErpHardwareProduct erpHardwareProduct : list) {
				erpHardwareProduct.setCategory(categoryQueryService.getCategoryById(erpHardwareProduct.getCategoryId()));
				erpHardwareProduct.setCategoryModel(categoryQueryService.getCategoryModelById(erpHardwareProduct.getCategoryModelId()));
				erpHardwareProduct.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpHardwareProduct.getCategoryModelFormatId()));
//				listRes.add(erpHardwareProduct);
			}
		}
		return list;
	}

	@Override
	public ErpHardwareProduct getErpHardwareProductByMacNumber(String macNumber) {
		ErpHardwareProduct erpHardwareProduct = erpHardwareProductDao.getErpHardwareProductByMacNumber(macNumber);;
		if(erpHardwareProduct != null){
			erpHardwareProduct.setCategory(categoryQueryService.getCategoryById(erpHardwareProduct.getCategoryId()));
			erpHardwareProduct.setCategoryModel(categoryQueryService.getCategoryModelById(erpHardwareProduct.getCategoryModelId()));
			erpHardwareProduct.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpHardwareProduct.getCategoryModelFormatId()));
		}
		return erpHardwareProduct;
	}

	@Override
	public ErpHardwareProduct get(Long id) {
		ErpHardwareProduct erpHardwareProduct = erpHardwareProductDao.selectByPrimaryKey(id);
		if(erpHardwareProduct != null){
			erpHardwareProduct.setCategory(categoryQueryService.getCategoryById(erpHardwareProduct.getCategoryId()));
			erpHardwareProduct.setCategoryModel(categoryQueryService.getCategoryModelById(erpHardwareProduct.getCategoryModelId()));
			erpHardwareProduct.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpHardwareProduct.getCategoryModelFormatId()));
		}
		return erpHardwareProduct;
	}

	@Override
	public ErpHardwareProduct getErpHardwareProductByUniqueCode(String uniqueCode) {
		ErpHardwareProduct erpHardwareProduct = erpHardwareProductDao.getErpHardwareProductByUniqueCode(uniqueCode);
		if(erpHardwareProduct != null){
			erpHardwareProduct.setCategory(categoryQueryService.getCategoryById(erpHardwareProduct.getCategoryId()));
			erpHardwareProduct.setCategoryModel(categoryQueryService.getCategoryModelById(erpHardwareProduct.getCategoryModelId()));
			erpHardwareProduct.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpHardwareProduct.getCategoryModelFormatId()));
		}
		return erpHardwareProduct;
	}

	@Override
	public List<ErpHardwareProduct> getErpHardwareProductByAdminId(Long adminId, String startDate, String endDate,Integer pageNo) {
		PageHelper page= new PageHelper();
		page.startPage(pageNo, PageConstant.pageSize, true);
		List<ErpHardwareProduct> list = erpHardwareProductDao.getErpHardwareProductByAdminId(adminId, startDate, endDate);
//		List<ErpHardwareProduct> listRes = new ArrayList<ErpHardwareProduct>();
		if(list != null){
			for (ErpHardwareProduct erpHardwareProduct : list) {
				erpHardwareProduct.setCategory(categoryQueryService.getCategoryById(erpHardwareProduct.getCategoryId()));
				erpHardwareProduct.setCategoryModel(categoryQueryService.getCategoryModelById(erpHardwareProduct.getCategoryModelId()));
				erpHardwareProduct.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpHardwareProduct.getCategoryModelFormatId()));
//				listRes.add(erpHardwareProduct);
			}
		}
		return list;
	}
	@Override
	public List<ErpHardwareProduct> queryListByFormatId(Map<String, Object> map) {
		PageHelper page= new PageHelper();
		page.startPage(Integer.parseInt(map.get("pageNo").toString()), PageConstant.pageSize, true);
		List<ErpHardwareProduct> list = erpHardwareProductDao.queryListByFormatId(map);
//		List<ErpHardwareProduct> listRes = new ArrayList<ErpHardwareProduct>();
		if(list != null){
			for (ErpHardwareProduct erpHardwareProduct : list) {
				erpHardwareProduct.setCategory(categoryQueryService.getCategoryById(erpHardwareProduct.getCategoryId()));
				erpHardwareProduct.setCategoryModel(categoryQueryService.getCategoryModelById(erpHardwareProduct.getCategoryModelId()));
				erpHardwareProduct.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpHardwareProduct.getCategoryModelFormatId()));
//				listRes.add(erpHardwareProduct);
			}
		}
		return list;
	}
	@Override
	public void save(ErpHardwareProduct pruduct) {
		erpHardwareProductDao.updateByPrimaryKeySelective(pruduct);
	}
}
