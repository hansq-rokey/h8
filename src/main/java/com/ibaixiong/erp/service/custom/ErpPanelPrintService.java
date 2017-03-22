package com.ibaixiong.erp.service.custom;

import java.util.List;

import com.ibaixiong.entity.ErpPanelPrint;

public interface ErpPanelPrintService {

	
	/**
	 * 私人订制 面板打印订单列表
	 * @param status
	 * @return
	 */
	List<ErpPanelPrint> queryPanelPrintByStatus(Byte status,int pageSize,int pageNo);
	
	int update(String ids,String logisticsCompany,String billNo,Long adminId);
}
