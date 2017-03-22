package com.ibaixiong.erp.service.purchase.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.core.utils.DateUtil;
import com.ibaixiong.entity.ErpBatch;
import com.ibaixiong.entity.ErpBatchDetails;
import com.ibaixiong.entity.ErpPurchaseOrder;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.dao.purchase.ErpPurchaseOrderDao;
import com.ibaixiong.erp.dao.sysset.ErpBaseBatchDao;
import com.ibaixiong.erp.dao.sysset.ErpBatchDao;
import com.ibaixiong.erp.dao.sysset.ErpBatchDetailsDao;
import com.ibaixiong.erp.service.purchase.ErpPurchaseOrderService;
import com.ibaixiong.erp.util.InvalidEnum;
import com.ibaixiong.erp.util.PurchaseOrderStatusEnum;
import com.papabear.product.api.CategoryQueryService;

@Service
public class ErpPurchaseOrderServiceImpl implements ErpPurchaseOrderService {
	@Resource
	private ErpPurchaseOrderDao erpPurchaseOrderDao;
	@Resource
	private ErpBatchDao erpBatchDao;
	@Resource
	private ErpBatchDetailsDao erpBatchDetailsDao;
	@Resource
	private ErpBaseBatchDao erpBaseBatchDao;
	@Resource
	private CategoryQueryService categoryQueryService;
	@Override
	public List<ErpPurchaseOrder> getList(int pageNo) {
//		page.startPage(pageNo, PageConstant.pageSize, true);
		PageHelper.startPage(pageNo, PageConstant.pageSize, true);
		List<ErpPurchaseOrder> res = erpPurchaseOrderDao.getList();
//		List<ErpPurchaseOrder> resNew = new ArrayList<ErpPurchaseOrder>();
		if(res != null && res.size()>0){
			for (ErpPurchaseOrder erpPurchaseOrder : res) {
				//查找相关的对象
				erpPurchaseOrder.setCategory(categoryQueryService.getCategoryById(erpPurchaseOrder.getCategoryId()));
				erpPurchaseOrder.setCategoryModel(categoryQueryService.getCategoryModelById(erpPurchaseOrder.getCategoryModelId()));
				erpPurchaseOrder.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpPurchaseOrder.getCategoryModelFormatId()));
//				resNew.add(erpPurchaseOrder);
			}
		}
		return res;
	}
	@Override
	public void insert(ErpPurchaseOrder order) {
		erpPurchaseOrderDao.insertSelective(order);
	}
	@Override
	public void update(ErpPurchaseOrder order) {
		erpPurchaseOrderDao.updateByPrimaryKeySelective(order);
	}
	@Transactional
	@Override
	public void delete(Long purchaseId) {
		//如果没有使用到，删除掉批次表 tbl_erp_batch_details
		ErpBatchDetails epd = erpBatchDetailsDao.getByOrderId(purchaseId);
		erpBatchDetailsDao.deleteByOrderId(purchaseId);
		//如果主表tbl_erp_batch没有结果集就直接也一起删除掉
		List<ErpBatchDetails> list = erpBatchDetailsDao.getListByBatchId(epd.getBatch().getId(),(byte)0);
		if(list == null || list.size()==0){
			//说明已经没有结果集了可以删除了
			erpBatchDao.deleteByPrimaryKey(epd.getBatch().getId());
		}
		erpPurchaseOrderDao.deleteByPrimaryKey(purchaseId);
		erpBatchDetailsDao.deleteByOrderId(purchaseId);//删除附表
	}
	@Override
	public ErpPurchaseOrder get(Long id) {
		ErpPurchaseOrder erpPurchaseOrder = erpPurchaseOrderDao.selectByPrimaryKey(id);
		if(erpPurchaseOrder != null){
			erpPurchaseOrder.setCategory(categoryQueryService.getCategoryById(erpPurchaseOrder.getCategoryId()));
			erpPurchaseOrder.setCategoryModel(categoryQueryService.getCategoryModelById(erpPurchaseOrder.getCategoryModelId()));
			erpPurchaseOrder.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpPurchaseOrder.getCategoryModelFormatId()));
		}
		return erpPurchaseOrder;
	}
	@Override
	public ErpPurchaseOrder getOrderByCategoryBatch(Long categoryId,
			Long categoryModelId, Long categoryModelFormatId, String batch) {
		ErpPurchaseOrder erpPurchaseOrder = erpPurchaseOrderDao.getOrderByCategoryBatch(categoryId, categoryModelId, categoryModelFormatId, batch);
		if(erpPurchaseOrder != null){
			erpPurchaseOrder.setCategory(categoryQueryService.getCategoryById(erpPurchaseOrder.getCategoryId()));
			erpPurchaseOrder.setCategoryModel(categoryQueryService.getCategoryModelById(erpPurchaseOrder.getCategoryModelId()));
			erpPurchaseOrder.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpPurchaseOrder.getCategoryModelFormatId()));
		}
		return erpPurchaseOrder;
	}
	@Transactional
	@Override
	public void save(ErpPurchaseOrder purchase,SysAdmin loginAdmin) {
		if(StringUtils.isNotBlank(purchase.getTransportTimeStr())){
			purchase.setTransportTime(DateUtil.parse(purchase.getTransportTimeStr()));
		}
		if(StringUtils.isNotBlank(purchase.getEndTimeStr())){
			purchase.setEndTime(DateUtil.parse(purchase.getEndTimeStr()));
		}
		if(purchase.getId() == null){
			//新增
			purchase.setStatus(PurchaseOrderStatusEnum.INIT.getCode());
			purchase.setInvalid(InvalidEnum.FALSE.getInvalidValue());
			purchase.setCreateDateTime(new Date());
			purchase.setAdmin(loginAdmin);
			purchase.setPutStorageNum(0);
			erpPurchaseOrderDao.insertSelective(purchase);
			//后续生成产品批次等相关表数据
			ErpBatch erpBatch = erpBatchDao.queryByBatch(purchase.getCategory().getId(), purchase.getCategoryModel().getId(), purchase.getCategoryModelFormat().getId(),0);
			if(erpBatch==null){
				erpBatch = new ErpBatch();
				erpBatch.setCategory(purchase.getCategory());
				erpBatch.setCategoryModel( purchase.getCategoryModel());
				erpBatch.setCategoryModelFormat(purchase.getCategoryModelFormat());
				erpBatch.setInvalid(InvalidEnum.FALSE.getInvalidValue());
				//erpBatch.setStatus(PurchaseOrderStatusEnum.INIT.getCode());
				erpBatch.setCreateDateTime(new Date());
				erpBatchDao.insertSelective(erpBatch);
			}
			ErpBatchDetails details = new ErpBatchDetails();
			details.setBatch(erpBatch);
			details.setOrder(purchase);
			details.setBatchNumber(purchase.getBatch());
			details.setDescription(purchase.getDescription());
			details.setCreateDateTime(new Date());
			details.setInvalid(InvalidEnum.FALSE.getInvalidValue());
			//details.setStatus(PurchaseOrderStatusEnum.INIT.getCode());
			erpBatchDetailsDao.insertSelective(details);
			//修改批次值
			erpBaseBatchDao.updateIndexNum(purchase.getCategory().getId(), purchase.getCategoryModel().getId(), purchase.getCategoryModelFormat().getId());
		}else{
			//修改
			purchase.setUpdateTime(new Date());
			erpPurchaseOrderDao.updateByPrimaryKeySelective(purchase);
		}
	}
}
