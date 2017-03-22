package com.ibaixiong.erp.service.oem;

import java.util.List;
import java.util.Map;

import com.ibaixiong.entity.ErpHardwareProduct;

public interface ErpHardwareProductService {

	/**
	 * 查询硬件信息
	 * 
	 * @param transportCodeId
	 *            运输码ID，关系表
	 * @return
	 */
	List<ErpHardwareProduct> queryErpHardwareProductByTransportCodeId(Long transportCodeId);

	/**
	 * 根据Mac地址查询硬件信息
	 * 
	 * @param macNumber
	 * @return
	 */
	ErpHardwareProduct getErpHardwareProductByMacNumber(String macNumber);

	/**
	 * 根据uniqueCode查询硬件信息
	 * 
	 * @param uniqueCode
	 * @return
	 */
	ErpHardwareProduct getErpHardwareProductByUniqueCode(String uniqueCode);

	ErpHardwareProduct get(Long id);

	List<ErpHardwareProduct> getErpHardwareProductByAdminId(Long adminId, String startDate, String endDate,Integer pageNo);
	/**
	 * 通过formatId查询
	 * @author zhaolei
	 * @date 2015年10月28日
	 * @param map
	 * @return
	 */
	List<ErpHardwareProduct> queryListByFormatId(Map<String, Object> map);
	void save(ErpHardwareProduct pruduct);
}
