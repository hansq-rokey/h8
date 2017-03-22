package com.deppon.dop.module.sdk.shared.domain.price;

import java.io.Serializable;
import java.util.List;

public class QueryPriceResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3231552293483564297L;
	// 物流公司ID
	private String logisticCompanyID;
	// 价格时效信息
	private List<DOPPriceInfo> priceInfo;
	
	public String getLogisticCompanyID() {
		return logisticCompanyID;
	}
	public void setLogisticCompanyID(String logisticCompanyID) {
		this.logisticCompanyID = logisticCompanyID;
	}
	public List<DOPPriceInfo> getPriceInfo() {
		return priceInfo;
	}
	public void setPriceInfo(List<DOPPriceInfo> priceInfo) {
		this.priceInfo = priceInfo;
	}
	
}
