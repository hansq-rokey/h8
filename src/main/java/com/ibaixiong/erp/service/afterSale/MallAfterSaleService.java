package com.ibaixiong.erp.service.afterSale;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ibaixiong.entity.MallAfterSalesService;

public interface MallAfterSaleService {
//	List<MallAfterSalesService> queryList(Map<String, Object> map);
//	MallAfterSalesService get(Long id);
//	void update(MallAfterSalesService order);
	/**
	 * 发货操作
	 * @author zhaolei
	 * @date 2015年11月4日
	 * @param hardwares 硬件IDS
	 * @param orderId 订单
	 * @param expressId 快递公司ID
	 * @param expressNumber 快递单号如果快递走的是面单的形式则该参数为必传
	 * @param request
	 * @return
	 */
	Map<String, Object> sendGoods(String hardwares,String orderId,Long expressId,String expressNumber,HttpServletRequest request);

}
