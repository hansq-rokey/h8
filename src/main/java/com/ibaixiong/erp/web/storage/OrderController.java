package com.ibaixiong.erp.web.storage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.deppon.dop.module.sdk.shared.domain.ResultInfo;
import com.deppon.dop.module.sdk.shared.domain.common.StatusDatabaseMappingEnum;
import com.deppon.dop.module.sdk.shared.domain.query.QueryOrderResult;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.core.utils.DateUtil;
import com.ibaixiong.core.utils.ResponseResult;
import com.ibaixiong.entity.DictCode;
import com.ibaixiong.entity.ErpHardwareProduct;
import com.ibaixiong.entity.ErpLogisticsInformation;
import com.ibaixiong.entity.ErpLogisticsLog;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.entity.User;
import com.ibaixiong.erp.exception.DepponException;
import com.ibaixiong.erp.exception.LowStocksException;
import com.ibaixiong.erp.service.oem.ErpHardwareProductChangeLogService;
import com.ibaixiong.erp.service.oem.ErpHardwareProductService;
import com.ibaixiong.erp.service.storage.ErpExpressCompanyService;
import com.ibaixiong.erp.service.storage.ErpLogisticsInformationService;
import com.ibaixiong.erp.service.storage.ErpLogisticsLogService;
import com.ibaixiong.erp.service.storage.MallOrderItemHardwareRelationService;
import com.ibaixiong.erp.service.storage.MallOrderItemLogisticsRelationService;
import com.ibaixiong.erp.service.storage.MallOrderLogisticsService;
import com.ibaixiong.erp.service.storage.MallOrderService;
import com.ibaixiong.erp.service.sys.DictCodeService;
import com.ibaixiong.erp.service.sys.UserService;
import com.ibaixiong.erp.util.DateEditor;
import com.ibaixiong.erp.util.DictTypeEnum;
import com.ibaixiong.erp.util.HardwareProductNetStatusEnum;
import com.ibaixiong.erp.util.InvalidEnum;
import com.ibaixiong.erp.web.util.BarcodeUtil;
import com.ibaixiong.erp.web.util.WebUtil;
import com.papabear.commons.entity.Pager;
import com.papabear.commons.entity.enumentity.Constant;
import com.papabear.order.api.OrderService;
import com.papabear.order.entity.MallOrder;
import com.papabear.order.entity.MallOrderHistory;
import com.papabear.order.entity.MallOrderItem;
import com.papabear.order.entity.MallOrderRevicerInformation;
import com.papabear.order.entity.OrderConstant.OrderOperateTye;
import com.papabear.order.entity.OrderConstant.OrderStatus;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.api.ProductQueryService;
import com.sun.corba.se.impl.interceptors.PICurrent;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Resource
	private MallOrderService mallOrderService;
	@Resource
	private ErpLogisticsInformationService erpLogisticsInformationService;
	@Resource
	private ErpLogisticsLogService erpLogisticsLogService;

	@Resource
	private MallOrderLogisticsService mallOrderLogisticsService;
	@Resource
	private ErpExpressCompanyService erpExpressCompanyService;
	@Resource
	private ErpHardwareProductService erpHardwareProductService;
	@Resource
	private MallOrderItemLogisticsRelationService mallOrderItemLogisticsRelationService;
	@Resource
	private MallOrderItemHardwareRelationService mallOrderItemHardwareRelationService;
	@Resource
	private ErpHardwareProductChangeLogService logService;
	@Resource
	private DictCodeService dictCodeService;
	@Resource
	private OrderService orderService;
	@Resource
	private UserService userService;
	@Resource
	private CategoryQueryService categoryQueryService;
	@Resource
	private ProductQueryService productQueryService;
	
	

	@RequestMapping("/list.html")
	public String orderList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "start", required = false) Date startTime, @RequestParam(value = "end", required = false) Date endTime, ModelMap model,
			HttpServletRequest request) {
		Byte status = null;
		String statusStr = request.getParameter("status");
		String keywords = request.getParameter("keywords");
		String uniqueCode = request.getParameter("uniqueCode");
		if (StringUtils.isNotBlank(statusStr)) {
			status = Byte.valueOf(statusStr);
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(keywords)) {
			model.addAttribute("keywords", keywords);
		}
		if (startTime != null) {
			model.addAttribute("start", DateUtil.format(startTime, "yyyy-MM-dd"));
		}
		if (endTime != null) {
			model.addAttribute("end", DateUtil.format(endTime, "yyyy-MM-dd"));
			// endTime = DateUtil.getDateEndTime(endTime);
		}
		Pager<com.papabear.order.entity.MallOrder> pager=null;
		if (StringUtils.isBlank(uniqueCode)) {
//			list = mallOrderService.queryByParamters(keywords, startTime, endTime, status, pageNo, PageConstant.pageSize);
//			pager = orderService.queryCustomerOrder(keywords, startTime, endTime, null, null, pageNo, PageConstant.pageSize);
			pager = orderService.queryOrdersBysearch(keywords, startTime, endTime, status, pageNo, PageConstant.pageSize,(byte)0);

		} else {
			List<MallOrder> list = mallOrderService.queryByUniqueCode(uniqueCode);
			pager=new Pager<MallOrder>(list.size(), pageNo, PageConstant.pageSize);
			pager.setList(list);
		}
//		PageInfo<MallOrder> pageInfo = new PageInfo<MallOrder>(list);
		model.addAttribute("pageInfo", pager);
		model.addAttribute("status", status);
		Map<String, Object> map = new HashMap<String, Object>();
		for (MallOrder order : pager.getList()) {
//			MallOrderRevicerInformation information = mallOrderRevicerInformationService.getByOrderNumberAndUserId(order.getOrderNumber(), order.getUser().getId());
			order.setInformation(orderService.getRevicerByUserIdAndOrderNumber(order.getUserId(), order.getOrderNumber()));
			List<MallOrderItem> orderItems=orderService.queryOrderItems(order.getUserId(), order.getOrderNumber());
			for(MallOrderItem item:orderItems){
				item.setCategoryModelFormatNumber(categoryQueryService.getCategoryModelFormat(item.getProductModelFormatId()).getCategoryModelFormatNumber());
				item.setPics(productQueryService.queryPics(null, item.getProductModelFormatId(), (short)2));
			}
			order.setOrderItems(orderItems);
			User user=userService.getuUser(order.getUserId());
			if(user==null)continue;
			order.setEmail(user.getEmail());
			order.setBxNum(user.getBxNum());
			order.setPhone(user.getPhone());
//			order.setInformation(information);
		}
		map.put("orders", pager.getList());//js 用到
		model.addAttribute("orderList", pager.getList());//TODO
		model.addAttribute("json", JSON.toJSONString(ResponseResult.result(1, "", map)));

		// System.out.println(JSON.toJSONString(ResponseResult.result(1,"",map)));
		return "storage/order_list";
	}

	@RequestMapping("/list/search.html")
	public String searchOrderList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "start", required = false) Date startTime, @RequestParam(value = "end", required = false) Date endTime, ModelMap model,
			HttpServletRequest request) {
		int offset = 10;
		String keywords = request.getParameter("keywords");

//		List<MallOrder> list = mallOrderService.queryByParamters(keywords, startTime, endTime, null, pageNo, offset);
		Pager<com.papabear.order.entity.MallOrder> pager= orderService.queryOrdersBysearch(keywords, startTime, endTime, null, pageNo, PageConstant.pageSize,(byte)0);
//		PageInfo<MallOrder> pageInfo = new PageInfo<MallOrder>(list, offset);
		model.addAttribute("pageInfo", pager);
		model.addAttribute("orderList", pager.getList());
		model.addAttribute("status", null);
		return "storage/order_list";
	}

	@RequestMapping("/detail.html")
	public String orderDetail(@RequestParam(value = "orderNumber", required = false) String orderNumber, ModelMap model, HttpServletRequest request) {
		MallOrder order = orderService.getMallOrder(orderNumber);
		// 查询订单详细信息
		order.setOrderItems(orderService.queryOrderItems(null, orderNumber));
//		order.setOrderItems(mallOrderItemService.getListByOrderNumber(orderNumber));
//		MallOrderRevicerInformation reciver = mallOrderRevicerInformationService.getByOrderNumberAndUserId(order.getOrderNumber(), order.getUserId());
		MallOrderRevicerInformation reciver =orderService.getRevicerByUserIdAndOrderNumber(order.getUserId(), order.getOrderNumber());
//		List<MallOrderHistory> listHistory = mallOrderHistoryService.queryHistoryByOrderNumber(orderNumber);
		List<MallOrderHistory> listHistory = orderService.queryHistoryByOrderNumber(orderNumber);
		List<DictCode> dictCodes = null;
		if (order.getIsCustomMade() != null && order.getIsCustomMade().intValue() == 1) {
			dictCodes = dictCodeService.queryDictCodeList(DictTypeEnum.ORDER_STATUS);
		} else {
			dictCodes = dictCodeService.queryDictCodeList(DictTypeEnum.ORDER_STATUS, (byte) 0);
		}
		model.addAttribute("orderStatusList", dictCodes);
		for (DictCode code : dictCodes) {
			for (MallOrderHistory history : listHistory) {
				if (code.getDictCodeValue().trim().equals(history.getStatus().toString())) {
					code.setFlow(true);
					code.setOrderHistory(history);
				}
			}
		}
		model.addAttribute("order", order);
		model.addAttribute("reciver", reciver);
		return "storage/order_detail";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	@RequestMapping("/printOrder.html")
	public void printOrder(@RequestParam(value = "orderNumber", required = false) String orderNumber, // SF100010010212
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String msg = "";
		int code = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String filePath = request.getRealPath("") + "/images/orderCode/" + orderNumber + ".png";
		BarcodeUtil.createBarcode(orderNumber, filePath);
		String barcodeUrl = "../../../../images/orderCode/" + orderNumber + ".png";
		resultMap.put("orderCode", barcodeUrl);
		MallOrder order = orderService.getMallOrder(orderNumber);
//		List<MallOrderItem> list = mallOrderItemService.getListByOrderNumber(orderNumber);
		List<MallOrderItem> list = orderService.queryOrderItems(null, orderNumber);
		order.setOrderItems(list);
		resultMap.put("order", order);
		PrintWriter write = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write = response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(code, msg, resultMap)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送商品货物快递
	 * 
	 * @author zhaolei
	 * @date 2015年10月18日
	 * @param hardwares
	 *            硬件IDS
	 * @param orderItems
	 *            商品详细IDS
	 * @param expressId
	 *            快递公司ID
	 * @param expressNumber
	 *            快递单号如果快递走的是面单的形式则该参数为必传
	 * @param model
	 * @param request
	 * @return http://erp.ibaixiong.com/order/sendGoods.html?hardwares=45,46&orderItems=160,160&expressId=1
	 */
	@RequestMapping("/sendGoods.html")
	public void sendGoods(@RequestParam(value = "hardwares", required = true) String hardwares, // 50,55
			@RequestParam(value = "orderItems", required = true) String orderItems,// 20,20
			@RequestParam(value = "expressId", required = true) Long expressId, // 1
			@RequestParam(value = "expressNumber", required = false) String expressNumber, // SF100010010212
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String msg = "";
		int code = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = null;
		try {
			map = mallOrderService.sendGoods(hardwares, orderItems, expressId, expressNumber, request);
			msg = map.get("msg").toString();
			code = Integer.parseInt(map.get("code").toString());
			if (code == 0) {
				resultMap.put("number", map.get("number").toString());
				// 生成条形码
				String filePath = request.getRealPath("") + "/images/orderCode/" + map.get("number").toString() + ".png";
				BarcodeUtil.createBarcode(map.get("number").toString(), filePath);
				String barcodeUrl = "../../../../images/orderCode/" + map.get("number").toString() + ".png";
				resultMap.put("orderCode", barcodeUrl);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			msg = "系统出现未知错误!";
			code = 999;
			if (e1 instanceof DepponException) {
				msg = "德邦系统出现未知错误，发货失败!";
				code = 991;
			}
			if (e1 instanceof LowStocksException) {
				msg = "库存不足，发货失败!";
				code = 992;
			}
		}
		PrintWriter write = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write = response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(code, msg, resultMap)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改快递信息表信息
	 * 
	 * @author zhaolei
	 * @date 2015年10月18日
	 * @param LogisticsId
	 *            物流信息表主键
	 * @param orderId
	 *            订单ID
	 * @param userId
	 *            用户ID
	 * @param model
	 * @param request
	 * @return http://erp.ibaixiong.com/order/updateLogistics.html?LogisticsId=2&orderId=135&userId=1007
	 */
	@RequestMapping("/updateLogistics.html")
	public void updateLogistics(@RequestParam(value = "LogisticsId", required = true) Long LogisticsId,
			@RequestParam(value = "orderId", required = true) Long orderId, @RequestParam(value = "userId", required = true) Long userId, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		String msg = "";
		int code = 0;
		ErpLogisticsInformation eli = erpLogisticsInformationService.get(LogisticsId);
//		MallOrder order = mallOrderService.get(orderId);
		MallOrder order = orderService.getMallOrder(orderId);
//		eli.setRevicerInformation(mallOrderRevicerInformationService.getByOrderNumberAndUserId(order.getOrderNumber(), userId));// 收货人信息相关
		eli.setRevicerInformation(orderService.getRevicerByUserIdAndOrderNumber(userId, order.getOrderNumber()));// 收货人信息相关
		eli.setSenderAdmin(WebUtil.getLoginUser(request));// 发货人信息
		// 手动修改下数量
		eli.setTotalNumber(10);
		ResultInfo resultInfo = mallOrderService.updateOrderByDeBang(eli);
		if (resultInfo != null) {
			if (resultInfo.getResultCode().equals("1000")) {
				// 说明订单处理成功
				// 修改物流订单相关
				eli.setUpdateTime(new Date());
				erpLogisticsInformationService.update(eli);
				// 日志记录表插入
				insertLogisticsLog(eli, resultInfo, request);
			} else {
				code = Integer.parseInt(resultInfo.getResultCode());
				msg = getErrorMsg(resultInfo.getResultCode());
			}
		} else {
			code = 1;
			msg = "物流发送失败";
		}
		PrintWriter write = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write = response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(code, msg, null)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 撤销快递信息表信息
	 * 
	 * @author zhaolei
	 * @date 2015年10月18日
	 * @param LogisticsId
	 *            物流信息表主键
	 * @param orderId
	 *            订单ID
	 * @param userId
	 *            用户ID
	 * @param model
	 * @param request
	 * @return http://erp.ibaixiong.com/order/backoutLogistics.html?LogisticsId=6&remark=错误的订单
	 */
	@RequestMapping("/backoutLogistics.html")
	public void backoutLogistics(@RequestParam(value = "LogisticsId", required = true) Long LogisticsId,
			@RequestParam(value = "remark", required = true) String remark, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String msg = "";
		int code = 0;
		ErpLogisticsInformation eli = erpLogisticsInformationService.get(LogisticsId);
		ResultInfo resultInfo = mallOrderService.backoutOrderByDeBang(eli, remark);
		if (resultInfo != null) {
			if (resultInfo.getResultCode().equals("1000")) {
				// 说明订单处理成功
				// 修改物流订单相关
				// 撤销订单更改状态
				eli.setStatus(StatusDatabaseMappingEnum.ORDER_STATUS_CANCELLED.getCode());
				eli.setUpdateTime(new Date());
				erpLogisticsInformationService.update(eli);
				// 日志记录表插入
				insertLogisticsLog(eli, resultInfo, request);
			} else {
				code = Integer.parseInt(resultInfo.getResultCode());
				msg = getErrorMsg(resultInfo.getResultCode());
			}
		} else {
			code = 1;
			msg = "物流发送失败";
		}
		PrintWriter write = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write = response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(code, msg, null)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertLogisticsLog(ErpLogisticsInformation eli, ResultInfo resultInfo, HttpServletRequest request) {
		ErpLogisticsLog log = new ErpLogisticsLog();
		log.setCreateDateTime(new Date());
		log.setStatus(StatusDatabaseMappingEnum.ORDER_STATUS_WAITACCEPT.getCode());
		log.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		log.setAdminId(WebUtil.getLoginUser(request).getId());
		log.setLogisticsId(eli.getId());
		log.setResult(JSON.toJSONString(resultInfo, true));
		log.setRequestType(Byte.parseByte("1"));// 白熊发起
		erpLogisticsLogService.insert(log);
	}

	/**
	 * 更新订单状态
	 * 
	 * @param orderNumber
	 * @param orderStatus
	 * @param response
	 */
	@RequestMapping("/update/status")
	public void updateOrderStatus(@RequestParam("orderNumber") String orderNumber, @RequestParam("orderStatus") byte orderStatus, HttpServletResponse response,
			HttpServletRequest request) {
		String msg = "";
		int code = 0;
		MallOrder order = orderService.getMallOrder(orderNumber);
		OrderStatus status = OrderStatus.get(orderStatus);
//		int flag = mallOrderService.updateStatus(orderNumber, status, WebUtil.getLoginUser(request), order.getUserId(), request);
		orderService.addOrderHistory(orderNumber, WebUtil.getLoginUser(request).getId(),order.getUserId(), request.getRemoteAddr(), status.getStatus(), OrderOperateTye.ADMINISTRATOR.getOperateType());
		int flag=orderService.updateOrderStatus(status.getStatus(), orderNumber);
		if (flag > 0) {
			code = 1;
		}
		PrintWriter write = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write = response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(code, msg, null)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 货物追踪查询
	 * 
	 * @param mailNo
	 * @param response
	 *            数据格式{ "responseParam":{ "logisticCompanyID":"DEPPON", "orders":[{ "mailNo":"92189788", "orderStatus":"SIGNSUCCESS", "traceCode":"1000",
	 *            "steps":[ {"acceptTime":"2013-04-16T10:53:46.000+0800", "remark":"已收取货物，[盐城]营业网点库存中"}, {"acceptTime":"2013-04-17T09:13:25.000+0800",
	 *            "remark":"离开  [盐城]营业网点  发往  [南通]运输中心"}, {"acceptTime":"2013-04-18T19:53:46.000+0800", "remark":"已到达  [南通]运输中心"}, …
	 *            {"acceptTime":"2012-11-30 10:59:11", "remark":"已到达  目的地,[西安雁塔丁白路]"}, {"acceptTime":"2013-05-16 09:26:19", "remark":"签收人：赵**"}] }] },
	 *            "result":"true", "resultCode":"1000", "reason":"" }
	 * 
	 * 
	 *            http://erp.ibaixiong.com/order/trace.html?logisticsId=O1007941445415653832
	 */
	@RequestMapping("/trace.html")
	public void queryLogisticsTrace(@RequestParam("logisticsId") String logisticsId, HttpServletResponse response) {
		String json = erpLogisticsInformationService.logisticsTrace(logisticsId);

		PrintWriter write = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write = response.getWriter();
			write.print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询物流信息
	 * 
	 * @author zhaolei
	 * @date 2015年10月19日
	 * @param LogisticsId
	 * @param model
	 * @param request
	 * @param response
	 *            http://erp.ibaixiong.com/order/selectLogistics.html?LogisticsId=2
	 */
	@RequestMapping("/selectLogistics.html")
	public void selectLogistics(@RequestParam(value = "LogisticsId", required = true) Long LogisticsId, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		// String msg = ""; 183.129.149.82
		// int code = 0;
		String result = "";
		ErpLogisticsInformation eli = erpLogisticsInformationService.get(LogisticsId);
		QueryOrderResult resultInfo = (QueryOrderResult) mallOrderService.selectOrderByDeBang(eli);
		if (resultInfo != null) {
			if (resultInfo.getResultCode().equals("1000")) {
				// 说明订单处理成功
				result = JSON.toJSONString(resultInfo, true);
			} else {
				Integer code = Integer.parseInt(resultInfo.getResultCode());
				String msg = getErrorMsg(resultInfo.getResultCode());
				result = JSON.toJSONString(ResponseResult.result(code, msg, null));
			}
		} else {
			result = JSON.toJSONString(ResponseResult.result(1, "物流发送失败", null));
		}
		PrintWriter write = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write = response.getWriter();
			write.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1000 成功 处理成功 正常处理 1001 订单已存在 订单已经存在 1002 订单不存在 订单不存在 1003 订单已存在 订单已存在 1004 操作不允许 只能操作本公司订单 1005 运单不存在 运单不存在 1006 流水号不存在 货物跟踪，流水号不存在 1007 订单不允许撤销
	 * 当前状态下订单不允许撤销 2001 白名单验证失败 2002 摘要验证失败 2003 时间戳验证失败 2004 公司编码验证失败 2005 参数转换失败 2006 参数校验失败 校验失败的参数 3001 接口调用失败 调用接口失败 重发 9000 未知异常 9999 系统异常 重试
	 */
	private String getErrorMsg(String code) {
		if (code.equals("1001")) {
			return "订单已存在";
		}
		if (code.equals("1002")) {
			return "订单不存在";
		}
		if (code.equals("1003")) {
			return "订单已存在";
		}
		if (code.equals("1004")) {
			return "操作不允许";
		}
		if (code.equals("1005")) {
			return "运单不存在";
		}
		if (code.equals("1006")) {
			return "流水号不存在";
		}
		if (code.equals("1007")) {
			return "订单不允许撤销";
		}
		if (code.equals("2001")) {
			return "白名单验证失败";
		}
		if (code.equals("2002")) {
			return "摘要验证失败";
		}
		if (code.equals("2003")) {
			return "时间戳验证失败";
		}
		if (code.equals("2004")) {
			return "公司编码验证失败";
		}
		if (code.equals("2005")) {
			return "参数转换失败";
		}
		if (code.equals("2006")) {
			return "参数校验失败";
		}
		if (code.equals("3001")) {
			return "接口调用失败";
		}
		if (code.equals("9000")) {
			return "未知异常";
		}
		if (code.equals("9999")) {
			return "系统异常";
		}
		return "";
	}

	@RequestMapping("/hardware")
	public void queryHardwareByID(@RequestParam("uniqueCode") String uniqueCode, HttpServletResponse response) {

		ErpHardwareProduct hardware = erpHardwareProductService.getErpHardwareProductByUniqueCode(uniqueCode);
		int code = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		if (hardware.getStatus().intValue() == HardwareProductNetStatusEnum.STORAGE.getCode().intValue()) {
			map.put("hardware", hardware);
			code = 1;
		}
		PrintWriter write = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write = response.getWriter();
			write.print(JSON.toJSONString(ResponseResult.result(code, "", map)));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("/testTran.html")
	public void testTran(Model model, HttpServletResponse response, HttpServletRequest request) {
		SysAdmin admin = com.ibaixiong.erp.web.util.WebUtil.getLoginUser(request);
		// 通过跟数据库的密码对比原密码，比对一致后修改
		String msg = "";
		int code = 0;
		try {
			mallOrderService.insertBatch();
		} catch (Exception e1) {
			e1.printStackTrace();
			code = 1;
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JSON.toJSONString(ResponseResult.result(code, msg)));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
}
