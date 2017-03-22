package com.ibaixiong.erp.service.sysset.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.ErpBatch;
import com.ibaixiong.erp.dao.sysset.ErpBatchDao;
import com.ibaixiong.erp.service.sysset.ErpBatchService;
import com.papabear.product.api.CategoryQueryService;
@Service
public class ErpBatchServiceImpl implements ErpBatchService {
    @Resource
    private ErpBatchDao erpBatchDao;
    @Resource
	private CategoryQueryService categoryQueryService;
	@Override
	public void insert(ErpBatch batch) {
		erpBatchDao.insertSelective(batch);
	}

	@Override
	public ErpBatch queryByBatch(Long categoryId, Long categoryModelId,
			Long categoryModelFormatId,int invalid) {
		ErpBatch epo = erpBatchDao.queryByBatch(categoryId, categoryModelId, categoryModelFormatId,invalid);
		if(epo != null){
			epo.setCategory(categoryQueryService.getCategoryById(epo.getCategoryId()));
			epo.setCategoryModel(categoryQueryService.getCategoryModelById(epo.getCategoryModelId()));
			epo.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(epo.getCategoryModelFormatId()));
		}
		return epo;
	}

	@Override
	public List<ErpBatch> getList() {
		List<ErpBatch> list =  erpBatchDao.getList();
//		List<ErpBatch> listRes = new ArrayList<ErpBatch>();
		if(list != null){
			for (ErpBatch erpBatch : list) {
				erpBatch.setCategory(categoryQueryService.getCategoryById(erpBatch.getCategoryId()));
				erpBatch.setCategoryModel(categoryQueryService.getCategoryModelById(erpBatch.getCategoryModelId()));
				erpBatch.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpBatch.getCategoryModelFormatId()));
//				listRes.add(erpBatch);
			}
		}
		return list;
	}

	@Override
	public ErpBatch get(Long id) {
		ErpBatch epo = erpBatchDao.selectByPrimaryKey(id);
		if(epo != null){
			epo.setCategory(categoryQueryService.getCategoryById(epo.getCategoryId()));
			epo.setCategoryModel(categoryQueryService.getCategoryModelById(epo.getCategoryModelId()));
			epo.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(epo.getCategoryModelFormatId()));
		}
		return epo;
	}
	
	@Override
	public void del(Long id) {
		erpBatchDao.deleteByPrimaryKey(id);
	}
	@Override
	public List<ErpBatch> getListPage(Integer pageNo) {
		PageHelper page= new PageHelper();
		page.startPage(pageNo, PageConstant.pageSize, true);
		List<ErpBatch> list = erpBatchDao.getList();
//		List<ErpBatch> listRes = new ArrayList<ErpBatch>();
		if(list != null){
			for (ErpBatch erpBatch : list) {
				erpBatch.setCategory(categoryQueryService.getCategoryById(erpBatch.getCategoryId()));
				erpBatch.setCategoryModel(categoryQueryService.getCategoryModelById(erpBatch.getCategoryModelId()));
				erpBatch.setCategoryModelFormat(categoryQueryService.getCategoryModelFormatById(erpBatch.getCategoryModelFormatId()));
//				listRes.add(erpBatch);
			}
		}
		return list;
	}
}
