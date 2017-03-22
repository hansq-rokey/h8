package com.ibaixiong.erp.service.storage;

import java.util.List;
import java.util.Map;

import com.ibaixiong.entity.ErpPutStorage;
import com.ibaixiong.entity.SysAdmin;

public interface ErpPutStorageService {
	
	/**
	 * 查询入库详细记录
	 * @param admin
	 * @param storageCountId   统计ID   
	 * @return
	 */
	List<ErpPutStorage> queryErpPutStorages(SysAdmin admin,Long storageCountId);
	
	List<ErpPutStorage> queryErpPutStoragesByHardwareId(Long hardwareId);
	
	void insert(ErpPutStorage eps);
	
	List<ErpPutStorage> queryErpPutStoragesByUniqueCode(String code);
	
	Map<String, String> putSave(Map<String, Object> map);
}
