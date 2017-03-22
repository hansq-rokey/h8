package com.deppon.dop.module.sdk.shared.domain;

import java.io.Serializable;

public class ResultInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3163295288039834713L;

	// 返回结果，true false
	private String result;

	// 结果编码
	private String resultCode;

	// 错误原因 9999 系统异常
	private String reason;

	//唯一请求ID
	private String uniquerRequestNumber;
	
	

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getUniquerRequestNumber() {
		return uniquerRequestNumber;
	}

	public void setUniquerRequestNumber(String uniquerRequestNumber) {
		this.uniquerRequestNumber = uniquerRequestNumber;
	}
}
