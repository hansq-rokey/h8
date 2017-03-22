package com.deppon.dop.module.sdk.shared.domain.addAndUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.dop.module.sdk.shared.domain.TransPeopleInfo;

/**
 * @Description 订单实体
 */
public class AddAndUpdateOrderReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9079111798051890410L;

	// 物流公司ID
	private String logisticCompanyID;

	// 单号
	private String logisticID;

	// 客户ID
	private String customerID;

	// 始发网点编码
	private String businessNetworkNo;

	// 到达网点编码
	private String toNetworkNo;

	// 发货人信息
	private TransPeopleInfo sender;

	// 收货人信息
	private TransPeopleInfo receiver;

	// 订单提交时间
	private Date gmtCommit;

	// 货物名称
	private String cargoName;

	// 货物性质，普通[0]、易碎[1]、液态[2]、化学品[3]、白色粉末状[4]、香烟[5]
	private String special;

	// 总件数
	private int totalNumber;

	// 总重量
	private double totalWeight;

	// 总体积
	private double totalVolume;

	// 支付方式
	private String payType;

	// 运输方式
	private String transportType;

	// 保价金额
	private BigDecimal insuranceValue;

	// 代收货款类型
	private String codType;

	// 代收货款
	private BigDecimal codValue;

	// 上门接货 是Y 否N
	private String vistReceive;

	// 提货方式
	private String deliveryType;

	// 包装
	private String packageService;

	// 短信通知，Y：需要 N: 不需要
	private String smsNotify;

	// 备注
	private String remark;

	// 上门接货开始时间
	private Date sendStartTime;

	// 上门接货结束时间
	private Date sendEndTime;

	// 返单方式
	private String backSignBill;

	// 订单来源 company-->companyCode
	private String orderSource;

	// 服务类型 线上 线下 0-自己联系 1-在线下单
	private String serviceType;
	
	
	

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

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getBusinessNetworkNo() {
		return businessNetworkNo;
	}

	public void setBusinessNetworkNo(String businessNetworkNo) {
		this.businessNetworkNo = businessNetworkNo;
	}

	public String getToNetworkNo() {
		return toNetworkNo;
	}

	public void setToNetworkNo(String toNetworkNo) {
		this.toNetworkNo = toNetworkNo;
	}

	public TransPeopleInfo getSender() {
		return sender;
	}

	public void setSender(TransPeopleInfo sender) {
		this.sender = sender;
	}

	public TransPeopleInfo getReceiver() {
		return receiver;
	}

	public void setReceiver(TransPeopleInfo receiver) {
		this.receiver = receiver;
	}

	public Date getGmtCommit() {
		return gmtCommit;
	}

	public void setGmtCommit(Date gmtCommit) {
		this.gmtCommit = gmtCommit;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public double getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(double totalVolume) {
		this.totalVolume = totalVolume;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public BigDecimal getInsuranceValue() {
		return insuranceValue;
	}

	public void setInsuranceValue(BigDecimal insuranceValue) {
		this.insuranceValue = insuranceValue;
	}

	public String getCodType() {
		return codType;
	}

	public void setCodType(String codType) {
		this.codType = codType;
	}

	public BigDecimal getCodValue() {
		return codValue;
	}

	public void setCodValue(BigDecimal codValue) {
		this.codValue = codValue;
	}

	public String getVistReceive() {
		return vistReceive;
	}

	public void setVistReceive(String vistReceive) {
		this.vistReceive = vistReceive;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getPackageService() {
		return packageService;
	}

	public void setPackageService(String packageService) {
		this.packageService = packageService;
	}

	public String getSmsNotify() {
		return smsNotify;
	}

	public void setSmsNotify(String smsNotify) {
		this.smsNotify = smsNotify;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getSendStartTime() {
		return sendStartTime;
	}

	public void setSendStartTime(Date sendStartTime) {
		this.sendStartTime = sendStartTime;
	}

	public Date getSendEndTime() {
		return sendEndTime;
	}

	public void setSendEndTime(Date sendEndTime) {
		this.sendEndTime = sendEndTime;
	}

	public String getBackSignBill() {
		return backSignBill;
	}

	public void setBackSignBill(String backSignBill) {
		this.backSignBill = backSignBill;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
}