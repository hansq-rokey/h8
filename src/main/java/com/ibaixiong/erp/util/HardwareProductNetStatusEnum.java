package com.ibaixiong.erp.util;

/**
 * created by chenzehe on 2015/10/28
 *
 * 硬件物联网状态的枚举
 *
 */
public enum HardwareProductNetStatusEnum {

	/* 防伪码生成--生成防伪码 */
	QRCODE(1),

	/* 生产完毕--生成唯一码 */
	PRODUCTION(2),

	/* 出厂--生成运输码 */
	LEAVEFACTORY(3),

	/* 入库--确认入库） */
	STORAGE(4),

	/* 异常--入库标记和仓损标记 */
	EXCEPTION(5),

	/* 发货 */
	DELIVERGOOD(6);

	public Byte getCode() {
		return this.code;
	}

	private Byte code;

	private HardwareProductNetStatusEnum(int code) {
		this.code = (byte) code;
	}

	public static HardwareProductNetStatusEnum get(Byte code) {
		for (HardwareProductNetStatusEnum ose : HardwareProductNetStatusEnum.values()) {
			if (ose.getCode().intValue() == code.intValue()) {
				return ose;
			}
		}
		return null;
	}
}
