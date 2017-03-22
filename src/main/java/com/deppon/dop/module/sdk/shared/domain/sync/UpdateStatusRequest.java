package com.deppon.dop.module.sdk.shared.domain.sync;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.dop.module.sdk.shared.domain.TransPeopleInfo;


/**
 * @Description 状态推送请求实体
 */
public class UpdateStatusRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3089858700924929509L;

	private  String logisticCompanyID;		//物流公司ID
	
	private  String logisticID;		//渠道单号
	
	private  Date gmtUpdated;					//状态更新时间
	
	private  String statusType;			//订单状态
	
	private  String comments;					//反馈原因
	
	/*==============以下内容为开单之后状态推送同时返回的内容==========================*/
	//运单号
	private String mailNo;
	
	//发货人
	private TransPeopleInfo sender;
	
	//收货人
	private TransPeopleInfo receiver;
	
    //3、货物名称
    private String cargoName;
    
    //4、总重量
    private double totalWeight;
    
    //5、总体积
    private double totalVolume;
    
    //6、总件数
    private int totalNumber;
    
    //7、总价格
    private BigDecimal totalPrice;     
    
    //8、支付方式,0:发货人付款（现付）1:收货人付款（到付）
    private String payType;
    
    //9、运输方式
    //HK_JZHK:精准空运
    //QC_JZKH:精准卡航
    //QC_JZQYC:精准汽运（长）
    //QC_JZQYD:精准汽运（短）
    //QC_JZCY:精准城运
    private String transportType;
    
    //10、保价金额
    private BigDecimal insuranceValue;
    
    //11、保价费
    private BigDecimal insurancePrice;
    
    //12、代收货款类型(3：三日退    1：即日退)
    private String codType;
    
    //13、代收货款
    private BigDecimal codValue;
    
    //14、代收货款费
    private	BigDecimal codPrice;
    
    //15、上门接货
    //Y:需要上门接货
    //N:客户自送
    private String vistReceive;
    
    //16、上门接货费
    private BigDecimal vistReceivePrice;  
    
    //17、送货方式
    //自提 1 
    //送货（不含上楼）0
    //机场自提 4
    //送货上楼 6
    private String deliveryType;
    
    //18、送货费用
    private BigDecimal deliveryPrice;     
    
    //19、签收回单
    //0 :无需返单 
    //1:客户签收单原件返回
    //2:客户签收单传真返回
    //3 :运单签收联原件返回
    //4 :运单签收联传真返回
    private String backSignBill;
    
    //20、签收回单费
    private BigDecimal backSignBillPrice;  
    
    //21、包装        (纸、 纤、 木箱、 木架、托膜、 托木)
    private String packageService;
    
    //22、包装服务费
    private BigDecimal packageServicePrice;   
    
    //23、等通知发货
    //Y：等通知发货
    //N：不需要等通知发货
    private String waitNotifySend;
    
    //24、等通知发货费用
    private BigDecimal waitNotifySendPrice; 
    
    //25、短信通知
    //Y：需要
    //N: 不需要
    private String smsNotify;
    
    //28、备注
    private String remark;
    
    //26、短信通知费用
    private BigDecimal smsNotifyPrice; 
    
    //27、其他费用
    private BigDecimal otherPrice;   
    
    //29、燃油附加
    private String fuelSurcharge;
    
    //30、燃油附加费
    private BigDecimal fuelSurchargePrice;
    
    //31、营业网点
    private String businessNetworkNo;
    
    //增加到达部门编码
    private String toNetworkNo;
    
    //运费
    private BigDecimal transportPrice;

    
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

	public Date getGmtUpdated() {
		return gmtUpdated;
	}

	public void setGmtUpdated(Date gmtUpdated) {
		this.gmtUpdated = gmtUpdated;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
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

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
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

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
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

	public BigDecimal getInsurancePrice() {
		return insurancePrice;
	}

	public void setInsurancePrice(BigDecimal insurancePrice) {
		this.insurancePrice = insurancePrice;
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

	public BigDecimal getCodPrice() {
		return codPrice;
	}

	public void setCodPrice(BigDecimal codPrice) {
		this.codPrice = codPrice;
	}

	public String getVistReceive() {
		return vistReceive;
	}

	public void setVistReceive(String vistReceive) {
		this.vistReceive = vistReceive;
	}

	public BigDecimal getVistReceivePrice() {
		return vistReceivePrice;
	}

	public void setVistReceivePrice(BigDecimal vistReceivePrice) {
		this.vistReceivePrice = vistReceivePrice;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public BigDecimal getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(BigDecimal deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	public String getBackSignBill() {
		return backSignBill;
	}

	public void setBackSignBill(String backSignBill) {
		this.backSignBill = backSignBill;
	}

	public BigDecimal getBackSignBillPrice() {
		return backSignBillPrice;
	}

	public void setBackSignBillPrice(BigDecimal backSignBillPrice) {
		this.backSignBillPrice = backSignBillPrice;
	}

	public String getPackageService() {
		return packageService;
	}

	public void setPackageService(String packageService) {
		this.packageService = packageService;
	}

	public BigDecimal getPackageServicePrice() {
		return packageServicePrice;
	}

	public void setPackageServicePrice(BigDecimal packageServicePrice) {
		this.packageServicePrice = packageServicePrice;
	}

	public String getWaitNotifySend() {
		return waitNotifySend;
	}

	public void setWaitNotifySend(String waitNotifySend) {
		this.waitNotifySend = waitNotifySend;
	}

	public BigDecimal getWaitNotifySendPrice() {
		return waitNotifySendPrice;
	}

	public void setWaitNotifySendPrice(BigDecimal waitNotifySendPrice) {
		this.waitNotifySendPrice = waitNotifySendPrice;
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

	public BigDecimal getSmsNotifyPrice() {
		return smsNotifyPrice;
	}

	public void setSmsNotifyPrice(BigDecimal smsNotifyPrice) {
		this.smsNotifyPrice = smsNotifyPrice;
	}

	public BigDecimal getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(BigDecimal otherPrice) {
		this.otherPrice = otherPrice;
	}

	public String getFuelSurcharge() {
		return fuelSurcharge;
	}

	public void setFuelSurcharge(String fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}

	public BigDecimal getFuelSurchargePrice() {
		return fuelSurchargePrice;
	}

	public void setFuelSurchargePrice(BigDecimal fuelSurchargePrice) {
		this.fuelSurchargePrice = fuelSurchargePrice;
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

	public BigDecimal getTransportPrice() {
		return transportPrice;
	}

	public void setTransportPrice(BigDecimal transportPrice) {
		this.transportPrice = transportPrice;
	}

}
