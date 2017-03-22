package com.ibaixiong.erp.service.storage;

import java.util.Date;
import java.util.List;

import com.ibaixiong.entity.ErpOutStorage;
import com.ibaixiong.entity.SysAdmin;

public interface ErpOutStorageService {

	List<ErpOutStorage> queryOutStorageList(SysAdmin admin,String keywords,Date startTime,Date endTime,int pageNo,int offset);
}
