/* * baixiong.com Inc.
 * Copyright (c) 1999-2001 All Rights Reserved.
 */
package com.ibaixiong.erp.service.storage.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ibaixiong.constant.Constant.Status;
import com.ibaixiong.entity.ErpExceptionProduct;
import com.ibaixiong.entity.ErpHardwareProduct;
import com.ibaixiong.entity.ErpHardwareProductChangeLog;
import com.ibaixiong.entity.ErpPurchaseOrder;
import com.ibaixiong.entity.ErpPutStorage;
import com.ibaixiong.entity.ErpPutStorageCount;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.dao.oem.ErpHardwareProductChangeLogDao;
import com.ibaixiong.erp.dao.oem.ErpHardwareProductDao;
import com.ibaixiong.erp.dao.purchase.ErpPurchaseOrderDao;
import com.ibaixiong.erp.dao.storage.ErpExceptionProductDao;
import com.ibaixiong.erp.dao.storage.ErpPutStorageCountDao;
import com.ibaixiong.erp.dao.storage.ErpPutStorageDao;
import com.ibaixiong.erp.service.storage.ErpPutStorageService;
import com.ibaixiong.erp.service.storage.MallOrderService;
import com.ibaixiong.erp.util.HardwareProductNetStatusEnum;
import com.ibaixiong.erp.util.InvalidEnum;
import com.ibaixiong.erp.util.PurchaseOrderStatusEnum;
import com.ibaixiong.erp.web.storage.HelpBean.ErpExceptionProductList;
import com.ibaixiong.erp.web.util.WebUtil;
import com.papabear.order.entity.OrderConstant.OrderStatus;
import com.papabear.product.api.CategoryCUDService;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.entity.MallBasicCategoryModel;
import com.papabear.product.entity.MallBasicCategoryModelFormat;

/**
 * 
 * 
 * @author yaoweiguo
 * @Email yaoweiguo@ibaixiong.com
 * @Description TODO
 * @date 2015年10月10日
 *
 */
@Service
public class ErpPutStorageServiceImpl implements ErpPutStorageService {

	@Resource
	private ErpPutStorageDao putStorageDao;
	@Resource
	private ErpHardwareProductDao erpHardwareProductDao;
	@Resource
	private ErpPutStorageCountDao erpPutStorageCountDao;
	@Resource
	private ErpHardwareProductChangeLogDao logDao;
	@Resource
	private ErpPurchaseOrderDao erpPurchaseOrderDao;
	@Resource
	private CategoryQueryService categoryQueryService;
	@Resource
	private CategoryCUDService categoryCUDService;
	@Resource
	private ErpExceptionProductDao erpExceptionProductDao;
	@Autowired
	MallOrderService mallOrderService;

	@Override
	public List<ErpPutStorage> queryErpPutStorages(SysAdmin admin, Long storageCountId) {
		return putStorageDao.queryErpPutStorages(admin == null ? null : admin.getId(), storageCountId, InvalidEnum.FALSE.getInvalidValue());
	}

	@Override
	public List<ErpPutStorage> queryErpPutStoragesByHardwareId(Long hardwareId) {

		return putStorageDao.queryErpPutStoragesByHardwareId(hardwareId, InvalidEnum.FALSE.getInvalidValue());
	}

	@Override
	public void insert(ErpPutStorage eps) {
		putStorageDao.insertSelective(eps);
	}

	@Override
	public List<ErpPutStorage> queryErpPutStoragesByUniqueCode(String code) {
		return putStorageDao.queryErpPutStoragesByUniqueCode(code);
	}

	@Transactional
	@Override
	public Map<String, String> putSave(Map<String, Object> map) {
		String normalPros = map.get("normalPros").toString();
		String code = map.get("code").toString();
		ErpExceptionProductList products = (ErpExceptionProductList) map.get("products");
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		if (org.apache.commons.lang.StringUtils.isNotBlank(normalPros)) {
			// 正常产品入库的记录
			String[] normalProducts = StringUtils.tokenizeToStringArray(normalPros, ",");
			int i = 0;
			Long pcId = 0l;
			Long categoryId = 0l;
			Long categoryModelId = 0l;
			Long categoryModelFormatId = 0l;
			String batch = "";
			SysAdmin loginUser = WebUtil.getLoginUser(request);
			for (String string : normalProducts) {
				// 获取硬件
				ErpHardwareProduct edp = erpHardwareProductDao.selectByPrimaryKey(Long.parseLong(string));
				// 先插入 汇总记录表
				if (i == 0) {
					// 说明是第一次进来
					ErpPutStorageCount esc = new ErpPutStorageCount();
					esc.setAdminId(loginUser.getId());
					esc.setCreateDateTime(new Date());
					esc.setInvalid(InvalidEnum.FALSE.getInvalidValue());
					esc.setBatch(edp.getBatch());
					esc.setCount(normalProducts.length);
					esc.setOemName(edp.getOemInfoName());
					MallBasicCategoryModelFormat format = categoryQueryService.getCategoryModelFormatById(edp.getCategoryModelFormatId());
					esc.setProductFormat(format != null ? format.getName() : null);
					MallBasicCategoryModel model = categoryQueryService.getCategoryModelById(edp.getCategoryModelId());
					esc.setProductName(model != null ? model.getName() : null);
					esc.setStatus(Status.NORMAL.getStatus());
					esc.setTransportcode(code);
					erpPutStorageCountDao.insertSelective(esc);
					pcId = esc.getId();
					insertEps(edp, pcId, loginUser.getId());
					categoryId = edp.getCategoryId();
					categoryModelId = edp.getCategoryModelId();
					categoryModelFormatId = edp.getCategoryModelFormatId();
					batch = edp.getBatch();
				} else {
					insertEps(edp, pcId, WebUtil.getLoginUser(request).getId());
				}
				// 修改硬件相关状态信息
				edp.setStatus(HardwareProductNetStatusEnum.STORAGE.getCode());
				erpHardwareProductDao.updateByPrimaryKeySelective(edp);
				// 入库
				insertProductLog(WebUtil.getLoginUser(request), edp.getId(), HardwareProductNetStatusEnum.STORAGE.getCode().longValue(), "确认入库");

				// 如果订单为私人定制，则更新订单信息
				if (edp.getIsCustomMade() == 1) {
					mallOrderService.changeCustomMadeOrder(OrderStatus.CUSTOM_MADE_STORAGE, edp.getId(), WebUtil.getLoginUser(request).getId(),
							request.getRemoteAddr());
				}
				i++;
			}
			// 采购订单修改库存量相关
			ErpPurchaseOrder epo = erpPurchaseOrderDao.getOrderByCategoryBatch(categoryId, categoryModelId, categoryModelFormatId, batch);
			if (epo != null) {
				epo.setCategory(categoryQueryService.getCategoryById(epo.getCategoryId()));
				epo.setCategoryModel(categoryQueryService.getCategoryModelById(epo.getCategoryModelId()));
				epo.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(epo.getCategoryModelFormatId()));
			}
			if (epo != null) {
				if (epo.getId() != null) {
					if (epo.getStatus() != PurchaseOrderStatusEnum.STORAGEEND.getCode()) {// 入库未完成进行下面操作
						int orderNum = epo.getOrderNum();// 定购数量
						int putStorageNum = epo.getPutStorageNum();// 已入库数量
						int newputStorageNum = putStorageNum + normalProducts.length;
						byte status = PurchaseOrderStatusEnum.STORAGSTART.getCode();
						// 比较当前订购数量是否大于他如果不大于则说明是入库中的操作
						if (orderNum <= newputStorageNum) {
							// 说明是如果完成了
							newputStorageNum = orderNum;
							status = PurchaseOrderStatusEnum.STORAGEEND.getCode();
						}
						ErpPurchaseOrder newEpo = new ErpPurchaseOrder();
						newEpo.setId(epo.getId());
						newEpo.setPutStorageNum(newputStorageNum);
						newEpo.setStatus(status);
						newEpo.setSurplusStorageNum(orderNum - newputStorageNum);
						erpPurchaseOrderDao.updateByPrimaryKeySelective(newEpo);
					}
				}
			}
			// 库存更新操作
			categoryCUDService.updateStockById(categoryModelFormatId, normalProducts.length);

		}
		// 插入异常列表数据
		if (products != null) {
			List<ErpExceptionProduct> ps = products.getProducts();
			if (ps.size() > 0) {
				for (ErpExceptionProduct eep : ps) {
					ErpHardwareProduct edp = erpHardwareProductDao.selectByPrimaryKey(eep.getHardwareProductId());
					if (edp != null && edp.getId() != null) {
						eep.setAdminId(WebUtil.getLoginUser(request).getId());
						eep.setCreateDateTime(new Date());
						eep.setInvalid(InvalidEnum.FALSE.getInvalidValue());
						eep.setStatus(Status.NORMAL.getStatus());
						eep.setBatch(edp.getBatch());
						eep.setOemName(edp.getOemInfoName());
						MallBasicCategoryModelFormat format = categoryQueryService.getCategoryModelFormatById(edp.getCategoryModelFormatId());
						eep.setProductFormat(format != null ? format.getName() : null);
						MallBasicCategoryModel model = categoryQueryService.getCategoryModelById(edp.getCategoryModelId());
						eep.setProductName(model != null ? model.getName() : null);
						eep.setTransportCode(code);
						eep.setUniqeCode(edp.getUniqueCode());
						eep.setDescription(WebUtil.getZHhandle(eep.getDescription()));
						erpExceptionProductDao.insertSelective(eep);
						insertProductLog(WebUtil.getLoginUser(request), edp.getId(), HardwareProductNetStatusEnum.EXCEPTION.getCode().longValue(), "入库异常标记");
					}
				}
			}
		}
		return null;
	}

	/**
	 * 入库详情记录插入
	 * 
	 * @author zhaolei
	 * @date 2015年10月15日
	 * @param edp
	 * @param pcId
	 * @param adminId
	 */
	private void insertEps(ErpHardwareProduct edp, Long pcId, Long adminId) {
		ErpPutStorage eps = new ErpPutStorage();
		eps.setAdminId(adminId);
		eps.setCreateDateTime(new Date());
		eps.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		eps.setStatus(Status.NORMAL.getStatus());
		eps.setHardwareProductId(edp.getId());
		eps.setMfgDate(edp.getMfgDateTime());
		eps.setUniqueCode(edp.getUniqueCode());
		eps.setPutStorageCountId(pcId);
		putStorageDao.insertSelective(eps);
	}

	// 插入物联网相关信息
	private void insertProductLog(SysAdmin loginUser, Long productId, Long typeId, String explain) {
		ErpHardwareProductChangeLog log = new ErpHardwareProductChangeLog();
		log.setHardwareProductId(productId);
		log.setCreateDateTime(new Date());
		log.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		log.setStatus(Status.NORMAL.getStatus());
		log.setUserId(loginUser.getId());
		log.setUserName(loginUser.getUserName());
		log.setOperateId(typeId);
		log.setOperateExplain(explain);
		logDao.insertSelective(log);
	}
}
