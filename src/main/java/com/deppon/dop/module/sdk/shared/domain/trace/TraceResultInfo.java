package com.deppon.dop.module.sdk.shared.domain.trace;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李鹏飞
 * @version V1.0
 * @Description 货物跟踪返回实体
 * @Time 2013-5-13
 */
public class TraceResultInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1026410870961636128L;
	//物流公司ID
	private String logisticCompanyID;
	//多个运单的货物跟踪信息组成的list
	private List<TraceInfo> orders;
	
	
	
	public String getLogisticCompanyID() {
		return logisticCompanyID;
	}
	public void setLogisticCompanyID(String logisticCompanyID) {
		this.logisticCompanyID = logisticCompanyID;
	}
	public List<TraceInfo> getOrders() {
		return orders;
	}
	public void setOrders(List<TraceInfo> orders) {
		this.orders = orders;
	}
	
}