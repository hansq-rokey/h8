package com.ibaixiong.erp.service.storage;

import java.util.Map;

import com.deppon.dop.module.sdk.shared.domain.common.ResultCode;
import com.ibaixiong.entity.ErpLogisticsInformation;

public interface ErpLogisticsInformationService {
	void insert(ErpLogisticsInformation information);
	void update(ErpLogisticsInformation information);

	ErpLogisticsInformation get(Long id);

	Map<String, Object> updateState(String params);	
    
    /**
     * 物流追踪
     * @param logisticsId  渠道单号
     */
    String logisticsTrace(String logisticsId);

}
