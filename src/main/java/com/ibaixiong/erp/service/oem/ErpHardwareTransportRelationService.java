package com.ibaixiong.erp.service.oem;

import java.util.List;

import com.ibaixiong.entity.ErpHardwareTransportRelation;

public interface ErpHardwareTransportRelationService {
	void insert(ErpHardwareTransportRelation ehtr);
	
	List<ErpHardwareTransportRelation> queryListByCodeId(Long codeId);
}
