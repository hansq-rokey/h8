package com.ibaixiong.erp.util;

/*字典表类型*/
public enum DictTypeEnum {
	/*产品图片类型*/
	PRODUCT_PIC_TYPE("PRODUCT_PIC_TYPE"),
	/*支付方式*/
	PAY_TYPE("PAY_TYPE"),
	/*订单状态*/
	ORDER_STATUS("ORDER_STATUS"),
	/*送货时间*/
	DELIVER_TIME("DELIVER_TIME"),
	/*购物车产品状态*/
	CAR_STATUS("CAR_STATUS"),
	/*退货状态*/
	BACK_STATUS("BACK_STATUS"),
	/*换货状态*/
	EXCHANGE_STATUS("EXCHANGE_STATUS"),
	/*维修状态*/
	REPAIR_STATUS("REPAIR_STATUS"),
	/*售后类型*/
	SERVICE_TYPE("SERVICE_TYPE"),
	/*支付状态*/
	PAY_STATUS("PAY_STATUS"),
	/*产品状态*/
	PRODUCT_STATUS("PRODUCT_STATUS");
	
	private String dictType;

	private DictTypeEnum(String dictType) {
		this.dictType = dictType;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	
	
	
}
