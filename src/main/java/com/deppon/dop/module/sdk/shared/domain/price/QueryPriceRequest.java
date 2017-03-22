package com.deppon.dop.module.sdk.shared.domain.price;

import java.io.Serializable;

/***
 * 价格时效查询请求对象
 * 
 * @author 118169
 * 
 */
public class QueryPriceRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1095107735459109970L;
	// 物流公司ID
	private String logisticCompanyID;
	//发货省份
	private String originalProvince;
	//发货城市
	private String originalCity;
	//发货区县
	private String originalDistrict;

	//收货省份
	private String destProvince;
	//收货城市
	private String destCity;
	//收货区县
	private String destDistrict;
	
	public String getLogisticCompanyID() {
		return logisticCompanyID;
	}
	public void setLogisticCompanyID(String logisticCompanyID) {
		this.logisticCompanyID = logisticCompanyID;
	}
	public String getOriginalProvince() {
		return originalProvince;
	}
	public void setOriginalProvince(String originalProvince) {
		this.originalProvince = originalProvince;
	}
	public String getOriginalCity() {
		return originalCity;
	}
	public void setOriginalCity(String originalCity) {
		this.originalCity = originalCity;
	}
	public String getOriginalDistrict() {
		return originalDistrict;
	}
	public void setOriginalDistrict(String originalDistrict) {
		this.originalDistrict = originalDistrict;
	}
	public String getDestProvince() {
		return destProvince;
	}
	public void setDestProvince(String destProvince) {
		this.destProvince = destProvince;
	}
	public String getDestCity() {
		return destCity;
	}
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}
	public String getDestDistrict() {
		return destDistrict;
	}
	public void setDestDistrict(String destDistrict) {
		this.destDistrict = destDistrict;
	}
	
	
	
}
