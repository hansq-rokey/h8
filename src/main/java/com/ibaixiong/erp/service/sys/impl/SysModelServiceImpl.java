/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.SysModel;
import com.ibaixiong.erp.dao.sys.SysModelDao;
import com.ibaixiong.erp.service.sys.SysModelService;

/**
 * @description
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年7月2日-下午1:19:30
 */
@Service("sysModelService")
public class SysModelServiceImpl implements SysModelService {

	@Resource
	SysModelDao modelDao;

	@Override
	public List<SysModel> getAllModelList() {
		return modelDao.getAllModelList();
	}

	@Override
	public List<SysModel> getModelListByIds(List<Long> ids) {
		return modelDao.getModelListByIds(ids);
	}
	@Override
	public List<SysModel> getModelsByPid(Long id) {
		return modelDao.getModelsByPid(id);
	}
	@Override
	public void saveModel(SysModel model) {
		modelDao.insertSelective(model);
	}
	@Override
	public void updateModel(SysModel model) {
		modelDao.updateByPrimaryKeySelective(model);
	}
}
