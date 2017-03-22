package com.deppon.dop.module.sdk.shared.domain;

import java.io.Serializable;

public class TransPeopleInfo implements Serializable{
	
	private static final long serialVersionUID = -3789679512116839628L;
	
	//公司
    private String companyName;
    //名称
    private String name;
    //邮编
    private String postCode;
    //电话
    private String phone;
    //手机
    private String mobile;
    //省份
    private String province;
    //城市
    private String city;
    //县/区
    private String county;
    //详细地址
    private String address;
    
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
    
}
