package com.deppon.dop.module.sdk.shared.domain.cancel;

import java.io.Serializable;

/***
 * @Description 撤销订单的请求实体
 */
public class CancelOrderReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1322239464648388961L;
	private String logisticCompanyID;// 物流公司编码
	private String logisticID;// 物流ID，渠道单号
	private String cancelTime;// 撤销时间
	private String remark;// 备注
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
	public String getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

}
