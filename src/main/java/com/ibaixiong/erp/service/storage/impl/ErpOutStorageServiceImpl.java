package com.ibaixiong.erp.service.storage.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.entity.ErpOutStorage;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.dao.storage.ErpOutStorageDao;
import com.ibaixiong.erp.service.storage.ErpOutStorageService;
import com.ibaixiong.erp.util.InvalidEnum;

@Service
public class ErpOutStorageServiceImpl implements ErpOutStorageService{

	@Resource
	private ErpOutStorageDao erpOutStorageDao;

	@Override
	public List<ErpOutStorage> queryOutStorageList(SysAdmin admin,String keywords,Date startTime,Date endTime, int pageNo,
			int offset) {
		PageHelper helper=new PageHelper();
		helper.startPage(pageNo, offset, true);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("adminId", admin==null?null:admin.getId());
		map.put("invalid", InvalidEnum.FALSE.getInvalidValue());
		map.put("keywords", keywords);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return erpOutStorageDao.queryOutStorageList(map);
	}
}
