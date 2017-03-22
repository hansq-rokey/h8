package com.ibaixiong.erp.service.storage.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.entity.ErpExceptionProduct;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.dao.storage.ErpExceptionProductDao;
import com.ibaixiong.erp.service.storage.ErpExceptionProductService;
import com.ibaixiong.erp.util.InvalidEnum;

@Service
public class ErpExceptionProductServiceImpl implements	ErpExceptionProductService {

	@Resource
	private ErpExceptionProductDao exceptionProductDao;
	
	@SuppressWarnings("static-access")
	@Override
	public List<ErpExceptionProduct> queryExcetionList(SysAdmin admin,String keywords,Date startTime,Date endTime,
			int pageNo, int offset) {
		PageHelper pageHelper=new PageHelper();
		pageHelper.startPage(pageNo, offset);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("adminId", admin==null?null:admin.getId());
		map.put("invalid", InvalidEnum.FALSE.getInvalidValue());
		map.put("keywords", keywords);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return exceptionProductDao.queryExcetionList(map);
	}

	@Override
	public List<ErpExceptionProduct> queryExcetionList(SysAdmin admin,
			int pageNo, int offset) {
		return this.queryExcetionList(admin, null, null, null, pageNo, offset);
	}
	@Override
	public void insert(ErpExceptionProduct eep) {
		exceptionProductDao.insertSelective(eep);
	}
	@Override
	public List<ErpExceptionProduct> queryExcetionList(Map<String, Object> map) {
		PageHelper pageHelper=new PageHelper();
		pageHelper.startPage(Integer.parseInt(map.get("pageNo").toString()),Integer.parseInt(map.get("offset").toString()));
		map.put("invalid", InvalidEnum.FALSE.getInvalidValue());
		return exceptionProductDao.queryExcetionList(map);
	}
}
