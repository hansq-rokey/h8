package com.deppon.dop.module.sdk.shared.domain.trace;

import com.deppon.dop.module.sdk.shared.domain.ResultInfo;

/**
 * @Description 货物跟踪返回结果
 */
public class TraceResult extends ResultInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1210702897034145540L;
	//货物跟踪结果
	private TraceResultInfo responseParam;
	public TraceResultInfo getResponseParam() {
		return responseParam;
	}
	public void setResponseParam(TraceResultInfo responseParam) {
		this.responseParam = responseParam;
	}
}
