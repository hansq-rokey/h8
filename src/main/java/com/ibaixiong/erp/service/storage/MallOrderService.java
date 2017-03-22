package com.ibaixiong.erp.service.storage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deppon.dop.module.sdk.shared.domain.ResultInfo;
import com.deppon.dop.module.sdk.shared.domain.query.QueryOrderResult;
import com.ibaixiong.entity.ErpLogisticsInformation;
import com.ibaixiong.entity.SysAdmin;
import com.papabear.order.entity.MallOrder;
import com.papabear.order.entity.MallOrderItem;
import com.papabear.order.entity.OrderConstant.OrderStatus;
import com.papabear.product.entity.MallBasicCategoryModelFormat;

public interface MallOrderService {

	/**
	 * 获取订单
	 * 
	 * @param status
	 * @param pageNo
	 * @param offset
	 * @return
	 */
//	List<MallOrder> queryAll(Byte status, int pageNo, int offset);

	/**
	 * 搜索订单
	 * 
	 * @param keywords
	 *            用户名或订单号
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param status
	 *            业务状态
	 * @return
	 */
//	List<MallOrder> queryByParamters(String keywords, Date startTime, Date endTime, Byte status, int pageNo, int offset);

	/**
	 * 更新状态
	 * 
	 * @param orderNumber
	 * @param status
	 * @return
	 */
//	int updateStatus(String orderNumber, OrderStatusEnum status, SysAdmin loginUser, Long userId, HttpServletRequest request);

	/**
	 * 德邦物流创建订单编号
	 * 
	 * @return
	 */
	ResultInfo createOrderByDeBang(ErpLogisticsInformation info);

	/**
	 * 德邦物流修改订单
	 * 
	 * @return
	 */
	ResultInfo updateOrderByDeBang(ErpLogisticsInformation info);

	/**
	 * 撤回订单
	 * 
	 * @author zhaolei
	 * @date 2015年10月19日
	 * @param info
	 * @return
	 */
	ResultInfo backoutOrderByDeBang(ErpLogisticsInformation info, String remark);

	/**
	 * 撤回订单
	 * 
	 * @author zhaolei
	 * @date 2015年10月19日
	 * @param info
	 * @return
	 */
	QueryOrderResult selectOrderByDeBang(ErpLogisticsInformation info);

	ResultInfo trackOrderByDeBang(ErpLogisticsInformation info);

	/**
	 * 通过订单号查询订单对象
	 * 
	 * @author zhaolei
	 * @date 2015年10月19日
	 * @param orderNumber
	 * @return
	 */
//	MallOrder getByOrderNumber(String orderNumber);

	/**
	 * 拆单业务
	 * 
	 * @author zhaolei
	 * @date 2015年10月29日
	 * @param map
	 *            集合包括以orderItem表的ID主键为键，本次发货的数量为值的键值对[{1:20},{3:20}]
	 * @return 返回的是以本次拆单业务执行之后的orderNumber,orderItem的主键集合字符串[{orderNumber:1},{orderItem:1,2,3,4}]
	 */
	Map<String, Object> splitOrder(Map<Long, Float> map, String orderItems);

	void insertBatch();

	/**
	 * 发货操作
	 * 
	 * @author zhaolei
	 * @date 2015年11月4日
	 * @param hardwares
	 *            硬件IDS
	 * @param orderItems
	 *            商品详细IDS
	 * @param expressId
	 *            快递公司ID
	 * @param expressNumber
	 *            快递单号如果快递走的是面单的形式则该参数为必传
	 * @param request
	 * @return
	 */
	Map<String, Object> sendGoods(String hardwares, String orderItems, Long expressId, String expressNumber, HttpServletRequest request);

//	MallOrder get(Long id);

	public String getErrorMsg(String code);

	public void updateProductStatus(String hardwares, SysAdmin loginUser);

//	public void updateRevicerInformationExpressNo(String orderNumber, String expressNumber);

	public void updateOrderLogisticsExpressNo(String orderNumber, String expressNumber);

	public void insertNotice(SysAdmin loginAdmin, MallBasicCategoryModelFormat modelFormat, Integer newStock, Integer oldStock);

	public void updateStock(long id, Integer stock);

//	MallOrder getOrderAndItemByOrderNumber(String orderNumber);

//	int updateStatus(String orderNumber, OrderStatusEnum status);

	void changeCustomMadeOrder(OrderStatus orderStatus, Long hardwareProductId, Long adminId, String ip);

//	List<MallOrder> queryByCustomMade(int pageNo, int offset, String orderNumber, String status);

	List<MallOrder> queryByUniqueCode(String uniqueCode);

	void changeCustomMadeOrder(OrderStatus orderStatus, MallOrderItem orderItem, Long adminId, String ip);
}
