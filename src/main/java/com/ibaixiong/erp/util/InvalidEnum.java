package com.ibaixiong.erp.util;

/**
 * 标记该记录是否有效
 * 
 * @author yaoweiguo
 * @Email yaoweiguo@ibaixiong.com
 * @Description TODO
 * @date 2015年9月29日
 *
 */
public enum InvalidEnum {
	/**无效*/
	TRUE(true), 
	/**有效*/
	FALSE(false);

	private InvalidEnum(Boolean invalidValue) {
		this.invalidValue = invalidValue;
	}

	private Boolean invalidValue;

	public Boolean getInvalidValue() {
		return invalidValue;
	}

	public void setInvalidValue(Boolean invalidValue) {
		this.invalidValue = invalidValue;
	}

}
