/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.sys;

import java.util.List;

import com.ibaixiong.entity.SysModel;

/**
 * @description
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年7月2日-下午1:19:30
 */
public interface SysModelService {
	List<SysModel> getAllModelList();

	List<SysModel> getModelListByIds(List<Long> ids);
	
	List<SysModel> getModelsByPid(Long id);
	
	void saveModel(SysModel model);
	
	void updateModel(SysModel model);
}
