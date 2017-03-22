package com.ibaixiong.erp.service.custom;

import java.util.List;
import java.util.Map;

import com.ibaixiong.entity.MallCustomPic;

public interface MallCustomPicService {

	List<MallCustomPic> queryMallCustomPic(Map<String, Object> map);
	
	MallCustomPic getmCustomPic (Long id);
		
	List<MallCustomPic> queryMallCustomPicByOrderNumber(String orderNumber);
	
	MallCustomPic getMallCustomPicByOrderNumberAndType(String orderNumber,Byte type);
	
}
