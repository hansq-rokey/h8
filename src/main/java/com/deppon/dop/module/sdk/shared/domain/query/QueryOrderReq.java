package com.deppon.dop.module.sdk.shared.domain.query;

import java.io.Serializable;

/***
 * 
 * @Description 查询订单的请求实体
 */
public class QueryOrderReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -423773324877272374L;
	private String logisticCompanyID;// 物流公司编码
	private String logisticID;// 物流ID，渠道单号
	private String orderSource;  //订单来源
	
	
	public String getLogisticCompanyID() {
		return logisticCompanyID;
	}
	public void setLogisticCompanyID(String logisticCompanyID) {
		this.logisticCompanyID = logisticCompanyID;
	}
	public String getLogisticID() {
		return logisticID;
	}
	public void setLogisticID(String logisticID) {
		this.logisticID = logisticID;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

}
