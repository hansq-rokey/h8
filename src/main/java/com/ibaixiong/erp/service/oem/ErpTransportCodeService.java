package com.ibaixiong.erp.service.oem;

import java.util.List;
import java.util.Map;

import com.ibaixiong.entity.ErpTransportCode;

public interface ErpTransportCodeService {

	/**
	 * 获取运输信息
	 * @param code   运输码
	 * @return
	 */
	public ErpTransportCode getErpTransportCodeByCode(String code);
	
	Map<String, Object> getErpTransportCode(String code);
	
	void insert(ErpTransportCode etc);
	
	void update(ErpTransportCode etc);
	
	List<ErpTransportCode> queryList(Map<String, Object> map,Integer pageNo);
	
	ErpTransportCode get(Long id);
	
	Map<String,Object> saveTransportCode(Map<String,Object> map);
}
