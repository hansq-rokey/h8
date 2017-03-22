package com.ibaixiong.erp.service.storage;

import java.util.Date;
import java.util.List;

import com.ibaixiong.entity.ErpPutStorageCount;
import com.ibaixiong.entity.SysAdmin;

public interface ErpPutStorageCountService {

	
	
	List<ErpPutStorageCount> queryPutStorageList(SysAdmin admin,String keywords,Date startTime,Date endTime,int pageNo,int offset);
	List<ErpPutStorageCount> queryPutStorageList(SysAdmin admin,int pageNo,int offset);
	ErpPutStorageCount getErpPutStorageCount(Long id);
	void insert(ErpPutStorageCount esc);
}
