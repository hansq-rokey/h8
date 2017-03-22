package com.deppon.dop.module.sdk.shared.domain.query;

import com.deppon.dop.module.sdk.shared.domain.OrderInfo;
import com.deppon.dop.module.sdk.shared.domain.ResultInfo;

/**
 * @Description 订单详情返回结果
 */
public class QueryOrderResult extends ResultInfo{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2928361831673760374L;
	//订单详情
	private OrderInfo responseParam;
	
	public OrderInfo getResponseParam() {
		return responseParam;
	}
	public void setResponseParam(OrderInfo responseParam) {
		this.responseParam = responseParam;
	}
	
}
