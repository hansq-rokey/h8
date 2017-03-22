package com.ibaixiong.erp.util;

/**
 * 
 *
 * 订单状态的枚举
 *
 */
public enum OrderStatusEnum {

	/* 待付款 */
	OBLIGATION(10),

	/* 已付款（交易中） */
	PAID(20),

	/** 私订制确认 */
	CUSTOM_MADE_CONFIRM(22),
	
	/** 面板打印中 */
	CUSTOM_PRINT(24),
	
	/** 私人订制中 */
	CUSTOM_MADING(26),

	/** 私人订制已入库 */
	CUSTOM_MADE_STORAGE(28),

	/* 配货（交易中） */
	PICKING(30),

	/* 发货（交易中） */
	SIPPING(40),

	/* 交易完成 */
	COMPLETED(50),

	/* 订单已关闭 */
	CLOSED(60);

	public Byte getCode() {
		return this.code;
	}

	private Byte code;

	private OrderStatusEnum(int code) {
		this.code = (byte) code;
	}

	public static OrderStatusEnum get(Byte code) {

		for (OrderStatusEnum ose : OrderStatusEnum.values()) {
			if (ose.getCode().intValue() == code.intValue()) {
				return ose;
			}
		}
		return null;
	}
}
