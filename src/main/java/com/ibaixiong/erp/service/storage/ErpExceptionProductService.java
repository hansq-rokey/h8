package com.ibaixiong.erp.service.storage;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ibaixiong.entity.ErpExceptionProduct;
import com.ibaixiong.entity.SysAdmin;

public interface ErpExceptionProductService {

	List<ErpExceptionProduct> queryExcetionList(SysAdmin admin,String keywords,Date startTime,Date endTime,int pageNo,int offset);
	
	List<ErpExceptionProduct> queryExcetionList(SysAdmin admin,int pageNo,int offset);
	
	List<ErpExceptionProduct> queryExcetionList(Map<String, Object> map);
	
	void insert(ErpExceptionProduct eep);
}
