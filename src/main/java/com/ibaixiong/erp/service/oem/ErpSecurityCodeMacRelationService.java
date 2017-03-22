package com.ibaixiong.erp.service.oem;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.ibaixiong.entity.ErpSecurityCodeMacRelation;

public interface ErpSecurityCodeMacRelationService {

	
	Set<String> createSecurityCode(String url,Integer num,Byte smart);
	
	int uploadFile(byte[] buf,String fileType)throws IOException;
	/**
	 * 防伪码与mac地址进行绑定操作
	 * @param url		防伪码url
	 * @param mac		mac地址
	 * @return
	 */
	int bindMac(String url,String mac);
	
	
	/**
	 * 防伪码与mac地址进行绑定操作
	 * @param fwm		防伪码16位
	 * @param mac		mac地址
	 * @return
	 */
	int bindMacByFwm(String fwm,String mac);
	/**
	 * 查询mac地址
	 * @param code		防伪码
	 * @return
	 */
	ErpSecurityCodeMacRelation getErpSecurityCodeMacRelation(String code);

	/**
	 * 生成防伪码
	 * @param url
	 * @param num
	 * @param smart
	 * @param modelId
	 * @param formatId
	 * @param modelName
	 * @param formatName
	 * @param batch
	 * @return
	 */
	Set<String> createSecurityCodeMac(String url, Integer num, Byte smart,
			Long modelId, Long formatId, String modelName, String formatName,String batch);

}
