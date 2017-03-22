package com.deppon.dop.module.sdk.shared.domain.trace;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author 李鹏飞
 * @version V1.0
 * @Description 每条的货物跟踪记录
 * @Time 2013-5-13
 */
public class TraceStep {
	//货物跟踪时间
	@JSONField (format="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date acceptTime;
	//货物跟踪内容
	private String remark;
	
	public Date getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
