package com.deppon.dop.module.sdk.shared.domain.price;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * DOP暴露出去的价格时效对象
 * @author 118169
 *
 */
public class DOPPriceInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -663814807661081256L;
	// 产品代码
	private String productCode;
	// 产品名称
	private String productName;

	// 承诺到达时间
	private String promiseArriveTime;

	// 重货费率
	private BigDecimal heavyRate;

	// 轻货费率
	private BigDecimal lightRate;

	// 最低一票(最低消费)
	private BigDecimal lowestPrice;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPromiseArriveTime() {
		return promiseArriveTime;
	}

	public void setPromiseArriveTime(String promiseArriveTime) {
		this.promiseArriveTime = promiseArriveTime;
	}

	public BigDecimal getHeavyRate() {
		return heavyRate;
	}

	public void setHeavyRate(BigDecimal heavyRate) {
		this.heavyRate = heavyRate;
	}

	public BigDecimal getLightRate() {
		return lightRate;
	}

	public void setLightRate(BigDecimal lightRate) {
		this.lightRate = lightRate;
	}

	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	
	
}
