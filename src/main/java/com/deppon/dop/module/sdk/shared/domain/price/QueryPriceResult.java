package com.deppon.dop.module.sdk.shared.domain.price;

import com.deppon.dop.module.sdk.shared.domain.ResultInfo;

public class QueryPriceResult extends ResultInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1399196352828842896L;
	//
	private QueryPriceResponse responseParam;
	
	public QueryPriceResponse getResponseParam() {
		return responseParam;
	}
	public void setResponseParam(QueryPriceResponse responseParam) {
		this.responseParam = responseParam;
	} 
}
