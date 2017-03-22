package com.deppon.dop.module.sdk.shared.domain.trace;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李鹏飞
 * @version V1.0
 * @Description 每个运单的货物跟踪信息
 * @Time 2013-5-13
 */
public class TraceInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 704291821628811611L;
	//运单号
	private String mailNo;
	//运单状态
	private String orderStatus;
	//每条货物跟踪记录组成的list
	private List<TraceStep> steps; 
	//查询结果Code
	private String traceCode;
	//查询结果
	private String traceResult;
	
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public List<TraceStep> getSteps() {
		return steps;
	}
	public void setSteps(List<TraceStep> steps) {
		this.steps = steps;
	}
	public String getTraceCode() {
		return traceCode;
	}
	public void setTraceCode(String traceCode) {
		this.traceCode = traceCode;
	}
	public String getTraceResult() {
		return traceResult;
	}
	public void setTraceResult(String traceResult) {
		this.traceResult = traceResult;
	}
	
}
