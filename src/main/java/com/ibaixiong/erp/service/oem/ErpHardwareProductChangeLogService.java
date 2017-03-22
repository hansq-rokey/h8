package com.ibaixiong.erp.service.oem;

import java.util.List;

import com.ibaixiong.entity.ErpHardwareProductChangeLog;

public interface ErpHardwareProductChangeLogService {

	/**
	 * 
	 * @param hardwareId
	 * @return
	 */
	List<ErpHardwareProductChangeLog> queryLogsByHardwareId(Long hardwareId);
	void insert(ErpHardwareProductChangeLog log);
}
