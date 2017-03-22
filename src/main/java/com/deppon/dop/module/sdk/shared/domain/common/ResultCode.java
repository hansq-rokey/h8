package com.deppon.dop.module.sdk.shared.domain.common;

/**
 * @Description 返回结果编码枚举
 */
public enum ResultCode{
	
	/**
	 * 结果代码		说明			备注
	 * 1000		成功
	 * 1001		订单已存在
	 * 1002		订单不存在
	 * 1003		运单已存在
	 * 1004		操作不允许		只能对本公司订单进行操作
	 * 1007		订单不允许撤销		
	 * 2001		白名单验证失败
	 * 2002		摘要验证失败
	 * 2003		时间戳验证失败
	 * 2004		公司编码验证失败
	 * 2005		参数转换失败
	 * 2006		参数校验失败
	 * 3001		接口调用失败
	 * 9000		未知异常
	 * 9999		系统异常
	 */

	SUCCESS("1000", "成功"),
	ORDER_EXIST("1001", "订单已存在"),
	ORDER_NOT_EXIST("1002", "订单不存在"),
	WAYBILL_NUMBER_EXIST("1003", "运单已存在"),
	OPERATE_NOT_ALLOW("1004", "操作不允许"),
	WAYBILL_NUMBER_NOT_EXIST("1005","运单不存在"),
	TRADE_NUMBER_NOT_EXIST("1006","流水号不存在"),
	CANCEL_NOT_ALLOW("1007","订单不允许撤销"),
	IP_NOT_IN_WHITE_LIST("2001", "白名单验证失败"),
	DIGEST_ERROR("2002", "摘要验证失败"),
	TIMESTAMP_ERROR("2003", "时间戳验证失败"),
	ECCOMPANYID_ERROR("2004", "公司编码验证失败"),
	PARAMS_CONVERT_ERROR("2005", "参数转换失败"),
	PARAMS_VALIDATE_ERROR("2006", "参数校验失败"),
	CALL_REMOTE_ERROR("3001", "接口调用失败"),
	UNKNOWN_ERROR("9000", "未知异常"),
	SYSTEM_ERROR("9999", "系统异常");

	private String number;

	private String message;

	private ResultCode(String number, String message) {
		this.number = number;
		this.message = message;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}