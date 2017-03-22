package com.ibaixiong.erp.web.storage.HelpBean;

import java.util.ArrayList;
import java.util.List;

import com.ibaixiong.entity.ErpExceptionProduct;

public class ErpExceptionProductList {
	private List<ErpExceptionProduct> products = new ArrayList<ErpExceptionProduct>();

	public List<ErpExceptionProduct> getProducts() {
		return products;
	}

	public void setProducts(List<ErpExceptionProduct> products) {
		this.products = products;
	}
	
}
