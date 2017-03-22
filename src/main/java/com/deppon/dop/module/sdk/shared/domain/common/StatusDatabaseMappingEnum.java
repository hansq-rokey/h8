package com.deppon.dop.module.sdk.shared.domain.common;

public enum StatusDatabaseMappingEnum {
	
	ORDER_STATUS_WAITACCEPT((byte)1,"WAITACCEPT"),// 待受理
	ORDER_STATUS_ACCEPT((byte)2,"ACCEPT"),// 已受理
	ORDER_STATUS_GOT((byte)3,"GOT"),// 已开单
	ORDER_STATUS_CANCELLED((byte)4,"CANCELLED"),// 已撤销
	ORDER_STATUS_UNACCEPT((byte)5,"UNACCEPT"),// 已拒绝
	ORDER_STATUS_NOGET((byte)6,"NOGET"),// 揽货失败
	ORDER_STATUS_SIGNSUCCESS((byte)7,"SIGNSUCCESS"),// 签收成功
	ORDER_STATUS_SIGNFAILED((byte)8,"SIGNFAILED"); // 异常签收
	
	private Byte code;
	private String value;

	public Byte getCode() {
		return code;
	}
	public void setCode(Byte code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private StatusDatabaseMappingEnum(Byte code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public static String getValue(Byte code){
		for(StatusDatabaseMappingEnum sdme:StatusDatabaseMappingEnum.values()){
			if(sdme.getCode().intValue()==code.intValue()){
				return sdme.getValue();
			}
		}
		return null;
	}
	
	public static Byte getCode(String value){
		for(StatusDatabaseMappingEnum sdme:StatusDatabaseMappingEnum.values()){
			if(sdme.getValue().equalsIgnoreCase(value)){
				return sdme.getCode();
			}
		}
		return null;
	}
	
}
