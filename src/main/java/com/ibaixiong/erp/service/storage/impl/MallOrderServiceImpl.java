package com.ibaixiong.erp.service.storage.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.deppon.dop.module.sdk.shared.domain.OrderInfo;
import com.deppon.dop.module.sdk.shared.domain.ResultInfo;
import com.deppon.dop.module.sdk.shared.domain.TransPeopleInfo;
import com.deppon.dop.module.sdk.shared.domain.cancel.CancelOrderReq;
import com.deppon.dop.module.sdk.shared.domain.common.DOPConstans;
import com.deppon.dop.module.sdk.shared.domain.common.StatusDatabaseMappingEnum;
import com.deppon.dop.module.sdk.shared.domain.query.QueryOrderReq;
import com.deppon.dop.module.sdk.shared.domain.query.QueryOrderResult;
import com.deppon.dop.module.sdk.shared.util.FastJsonUtil;
import com.deppon.dop.module.sdk.shared.util.HttpUtils;
import com.deppon.dop.module.sdk.shared.util.SecurityUtil;
import com.ibaixiong.constant.Constant.Status;
import com.ibaixiong.core.utils.CodeUtil;
import com.ibaixiong.core.utils.DateUtil;
import com.ibaixiong.entity.ErpExpressCompany;
import com.ibaixiong.entity.ErpHardwareProduct;
import com.ibaixiong.entity.ErpHardwareProductChangeLog;
import com.ibaixiong.entity.ErpLogisticsInformation;
import com.ibaixiong.entity.ErpLogisticsLog;
import com.ibaixiong.entity.ErpNoticeCategory;
import com.ibaixiong.entity.ErpNoticeInfo;
import com.ibaixiong.entity.ErpOutStorage;
import com.ibaixiong.entity.MallOrderItemHardwareRelation;
import com.ibaixiong.entity.MallOrderLogistics;
import com.ibaixiong.entity.MallPay;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.entity.SysRole;
import com.ibaixiong.erp.activemq.MallOrderProducer;
import com.ibaixiong.erp.dao.notice.ErpNoticeInfoDao;
import com.ibaixiong.erp.dao.oem.ErpHardwareProductChangeLogDao;
import com.ibaixiong.erp.dao.oem.ErpHardwareProductDao;
import com.ibaixiong.erp.dao.storage.ErpExpressCompanyDao;
import com.ibaixiong.erp.dao.storage.ErpLogisticsInformationDao;
import com.ibaixiong.erp.dao.storage.ErpLogisticsLogDao;
import com.ibaixiong.erp.dao.storage.ErpOutStorageDao;
import com.ibaixiong.erp.dao.storage.MallOrderItemHardwareRelationDao;
import com.ibaixiong.erp.dao.storage.MallOrderItemLogisticsRelationDao;
import com.ibaixiong.erp.dao.storage.MallOrderLogisticsDao;
import com.ibaixiong.erp.dao.storage.MallPayDao;
import com.ibaixiong.erp.dao.sys.SysAdminDao;
import com.ibaixiong.erp.dao.sys.SysRoleDao;
import com.ibaixiong.erp.dao.sysset.ScanRecodDao;
import com.ibaixiong.erp.exception.DepponException;
import com.ibaixiong.erp.exception.LowStocksException;
import com.ibaixiong.erp.service.storage.MallOrderService;
import com.ibaixiong.erp.util.HardwareProductNetStatusEnum;
import com.ibaixiong.erp.util.InvalidEnum;
import com.ibaixiong.erp.web.util.WebUtil;
import com.papabear.order.api.OrderService;
import com.papabear.order.entity.MallOrder;
import com.papabear.order.entity.MallOrderHistory;
import com.papabear.order.entity.MallOrderItem;
import com.papabear.order.entity.MallOrderRevicerInformation;
import com.papabear.order.entity.OrderConstant.OrderOperateTye;
import com.papabear.order.entity.OrderConstant.OrderStatus;
import com.papabear.product.api.CategoryCUDService;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.entity.MallBasicCategoryModelFormat;

@Service
public class MallOrderServiceImpl implements MallOrderService {
	@Resource
	SysAdminDao sysAdminDao;
	@Resource
	SysRoleDao sysRoleDao;
	@Resource
	MallOrderLogisticsDao mallOrderLogisticsDao;
	@Resource
	ErpExpressCompanyDao erpExpressCompanyDao;
	@Resource
	ErpLogisticsLogDao erpLogisticsLogDao;
	@Resource
	MallOrderItemLogisticsRelationDao mallOrderItemLogisticsRelationDao;
	@Resource
	ErpHardwareProductDao erpHardwareProductDao;
	@Resource
	MallOrderItemHardwareRelationDao mallOrderItemHardwareRelationDao;
	@Resource
	ErpHardwareProductChangeLogDao erpHardwareProductChangeLogDao;
	@Resource
	ErpOutStorageDao erpOutStorageDao;
	@Resource
	MallPayDao mallPayDao;
	@Resource
	CategoryQueryService categoryQueryService;
	@Resource
	CategoryCUDService categoryCUDService;
	@Resource
	ErpNoticeInfoDao noticeDao;
	@Resource
	ErpLogisticsInformationDao erpLogisticsInformationDao;
	@Autowired
	ScanRecodDao scanRecodDao;
	@Resource
	private OrderService orderService;
	@Resource
	private MallOrderProducer mallOrderProducer;
	/*
	 * static String createDPorderUrl="http://58.40.17.67/dop/order/newsaveOrder.action";//添加物流订单 static String
	 * updateDPorderUrl="http://58.40.17.67/dop/order/alterOrder.action";//修改物流订单 static String
	 * backoutDPorderUrl="http://58.40.17.67/dop/order/cancelOrder.action";//撤销物流订单 static String
	 * selectDPorderUrl="http://58.40.17.67/dop/order/queryOrder.action";//查询物流订单 static String
	 * trackDPorderUrl="http://58.40.17.67/dop/order/traceOrder.action";//追踪物流订单 static String companyCode="HZBXKJ"; static String appkey="deppontest"; static
	 * String sign="HZBX";
	 */
	public static String createDPorderUrl = "http://api.deppon.com/dop/order/newsaveOrder.action";// 添加物流订单
	public static String updateDPorderUrl = "http://api.deppon.com/dop/order/alterOrder.action";// 修改物流订单
	public static String backoutDPorderUrl = "http://api.deppon.com/dop/order/cancelOrder.action";// 撤销物流订单
	public static String selectDPorderUrl = "http://api.deppon.com/dop/order/queryOrder.action";// 查询物流订单
	public static String trackDPorderUrl = "http://api.deppon.com/dop/order/traceOrder.action";// 追踪物流订单
	public static String companyCode = "HZBXKJ";
	public static String appkey = "1d2b09d2e4b9ca2a082d59724fb553b0";
	public static String sign = "HZBX";
	public static String businessNetworkNo = "W09064511";
	public static String serviceType = "2";


	@Override
	public List<MallOrder> queryByUniqueCode(String uniqueCode) {
		List<MallOrder> list = new ArrayList<MallOrder>();
		MallOrderItemHardwareRelation mallOrderItemHardwareRelation = mallOrderItemHardwareRelationDao.selectByUniqueCode(uniqueCode);
		if (mallOrderItemHardwareRelation == null) {
			return list;
		}
//		MallOrderItem orderItem = orderItemDao.selectByPrimaryKey(mallOrderItemHardwareRelation.getOrderItemId());
		MallOrderItem orderItem = orderService.getMallOrderItem(mallOrderItemHardwareRelation.getOrderItemId());
		if (orderItem == null) {
			return list;
		}
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("orderNumber", orderItem.getOrderNumber());
//		list = orderDao.queryByParamters(map);
		MallOrder order=orderService.getMallOrder(orderItem.getOrderNumber());
		list.add(order);
		return list;
	}

	private static OrderInfo createOrderInfo(ErpLogisticsInformation info) {
		OrderInfo orderInfo = new OrderInfo();
		// 物流公司ID 长度 32 必填
		orderInfo.setLogisticCompanyID(info.getLogisticCompanyId());
		// 渠道单号 长度32位 必填
		orderInfo.setLogisticID(sign + info.getLogisticId());
		// 运单号 长度32位，选填，当为线下订单时，运单号为必填项
		orderInfo.setMailNo(info.getMailNo());

		orderInfo.setOrderSource(info.getOrderSource());

		orderInfo.setServiceType(info.getServiceType());// ？？？？

		orderInfo.setCustomerID(info.getCustomerId());

		orderInfo.setBusinessNetworkNo(info.getBusinessNetworkNo());

		orderInfo.setToNetworkNo(info.getToNetworkNo());
		// 发货人信息
		SysAdmin senderAdmin = info.getSenderAdmin();
		TransPeopleInfo sender = new TransPeopleInfo();
		sender.setCompanyName("杭州白熊科技有限公司");
		sender.setName(senderAdmin.getUserName());// 发货人姓名
		sender.setPostCode("");
		sender.setPhone("057186621901");// 默认公司的座机
		sender.setMobile(senderAdmin.getPhone());
		sender.setProvince("浙江省");
		sender.setCity("杭州市");
		sender.setCounty("余杭区");
		sender.setAddress("望梅路578号德雅金座");

		// 收货人信息
		MallOrderRevicerInformation revicerInfo = info.getRevicerInformation();
		TransPeopleInfo receiver = new TransPeopleInfo();
		receiver.setCompanyName("");
		receiver.setName(revicerInfo.getReveiveUserName());// 收货人姓名
		receiver.setPostCode("");
		receiver.setPhone(revicerInfo.getTelPhone());
		receiver.setMobile(revicerInfo.getMobilePhone());
		receiver.setProvince(revicerInfo.getProvinceName());
		receiver.setCity(revicerInfo.getCityName());
		receiver.setCounty(revicerInfo.getDistrictName());
		receiver.setAddress(revicerInfo.getDetailAddress());

		orderInfo.setReceiver(receiver);
		orderInfo.setSender(sender);

		orderInfo.setGmtCommit(new Date());

		orderInfo.setCargoName(info.getCargoName());

		orderInfo.setSpecial(info.getSpecial());

		orderInfo.setTotalNumber(info.getTotalNumber());

		orderInfo.setTotalWeight(info.getTotalWeight());

		orderInfo.setTotalVolume(info.getTotalVolume());

		orderInfo.setPayType(info.getPayType());

		orderInfo.setTransportType(info.getTransportType());
		if (info.getInsuranceValue() != null) {
			BigDecimal insurance = new BigDecimal(info.getInsuranceValue());
			orderInfo.setInsuranceValue(insurance);// 保价金额 ???
		} else {
			orderInfo.setInsuranceValue(BigDecimal.ZERO);// 保价金额 ???
		}
		orderInfo.setCodType(info.getCodType());

		if (info.getCodValue() != null) {
			BigDecimal codValue = new BigDecimal(info.getCodValue());
			orderInfo.setCodValue(codValue);// 代收货款金额
		} else {
			orderInfo.setCodValue(BigDecimal.ZERO);// 代收货款金额
		}

		orderInfo.setVistReceive(info.getVistReceive());

		orderInfo.setDeliveryType(info.getDeliveryType());

		orderInfo.setBackSignBill(info.getBackSignBill());

		orderInfo.setPackageService(info.getPackageService());

		orderInfo.setSmsNotify(info.getSmsNotify());
		// orderInfo.setRemark(info.getRemark());
		orderInfo.setRemark("浙江省杭州市余杭区临平大道839温格科技");// 直接定死当做真实的发货地址使用

		return orderInfo;
	}

	private static CancelOrderReq createCancelOrder(ErpLogisticsInformation info, String remark) {
		CancelOrderReq cancelOrderReq = new CancelOrderReq();
		cancelOrderReq.setLogisticCompanyID(info.getLogisticCompanyId());
		cancelOrderReq.setCancelTime(DateUtil.getDateNow("yyyy-MM-dd hh:mm:ss"));
		cancelOrderReq.setLogisticID(sign + info.getLogisticId());
		cancelOrderReq.setRemark(remark);
		return cancelOrderReq;
	}

	private static QueryOrderReq createQueryOrder(ErpLogisticsInformation info) {
		QueryOrderReq cancelOrderReq = new QueryOrderReq();
		cancelOrderReq.setLogisticCompanyID(info.getLogisticCompanyId());
		cancelOrderReq.setLogisticID(sign + info.getLogisticId());
		return cancelOrderReq;
	}

	public static void main(String[] args) {
		// String params=FastJsonUtil.toJSONString(createOrderInfo());
		// System.out.println(params);
		String t = "1,11,25,1,2,11";
		System.out.println(t);
		t = "," + t + ",";
		t = t.replaceAll("," + 1 + ",", "," + 26 + ",");// .replaceAll(","+11+",", ","+27+",").replaceAll(","+25+",", ","+28+",").replaceAll(","+2+",",
														// ","+29+",");
		System.out.println(t);
		t = t.substring(1);
		System.out.println(t);
		t = t.substring(0, t.length() - 1);
		System.out.println(t);
	}

	@SuppressWarnings("static-access")
	@Override
	public ResultInfo createOrderByDeBang(ErpLogisticsInformation info) {
		String params = FastJsonUtil.toJSONString(this.createOrderInfo(info));
		String result = sendDB(params, createDPorderUrl);
		return FastJsonUtil.parseObject(result, ResultInfo.class);
	}

	@SuppressWarnings("static-access")
	@Override
	public ResultInfo updateOrderByDeBang(ErpLogisticsInformation info) {
		String params = FastJsonUtil.toJSONString(this.createOrderInfo(info));
		String result = sendDB(params, updateDPorderUrl);
		return FastJsonUtil.parseObject(result, ResultInfo.class);
	}

	@SuppressWarnings("static-access")
	@Override
	public ResultInfo backoutOrderByDeBang(ErpLogisticsInformation info, String remark) {
		String params = FastJsonUtil.toJSONString(this.createCancelOrder(info, remark));
		String result = sendDB(params, backoutDPorderUrl);
		return FastJsonUtil.parseObject(result, ResultInfo.class);
	}

	@SuppressWarnings("static-access")
	@Override
	public QueryOrderResult selectOrderByDeBang(ErpLogisticsInformation info) {
		String params = FastJsonUtil.toJSONString(this.createQueryOrder(info));
		String result = sendDB(params, selectDPorderUrl);
		return FastJsonUtil.parseObject(result, QueryOrderResult.class);
	}

	@Override
	public ResultInfo trackOrderByDeBang(ErpLogisticsInformation info) {
		return null;
	}

	/**
	 * 德邦公共发送接口
	 * 
	 * @author zhaolei
	 * @date 2015年10月19日
	 * @param params
	 * @return
	 */
	private String sendDB(String params, String url) {
		// 订单内容 json字符串，SDK提供FastJsonUtil转Json
		// String params=FastJsonUtil.toJSONString(this.createOrderInfo(info));
		// companyCode与appkey为双方约定
		// 时间戳 SDK提供SecurityUtil获取时间戳
		String timestamp = SecurityUtil.getTimestamp();
		// 摘要 SDK提供SecurityUtil生成摘要
		String digest = SecurityUtil.getDigest(params + appkey + timestamp);
		// post请求参数
		NameValuePair[] data = new NameValuePair[4];
		data[0] = new NameValuePair("companyCode", companyCode);
		data[1] = new NameValuePair("digest", digest);
		data[2] = new NameValuePair("timestamp", timestamp);
		data[3] = new NameValuePair("params", params);
		System.out.println(params);
		// 请求url
		// String url="this is request address";
		String response = null;
		try {
			// 返回结果
			response = HttpUtils.sendRequest(url, data, "UTF-8", 5000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 根据返回结果进行后续操作
		return response;
	}

	@Override
	public Map<String, Object> splitOrder(Map<Long, Float> map, String orderItems) {
		// 首先判断是否需要拆单
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Set<Long> set = map.keySet();
		// 是否需要拆单条件是，本次发送出去的产品必须与订单所采购的所有产品种类一致，数量一致，
		boolean isNext = true;// 是否进行下去
		String orderNumber = "";
		for (Long id : set) {
//			MallOrderItem item = orderItemDao.selectByPrimaryKey(id);
			MallOrderItem item = orderService.getMallOrderItem(id);
			// 说明发送过来的商品详情item表中的订单不是来自于同一订单
			if (StringUtils.isNotBlank(orderNumber) && !orderNumber.equals(item.getOrderNumber())) {
				isNext = false;// 发现了下一步就不用执行了返回为空的集合直接处理失败
				break;
			} else if (StringUtils.isBlank(orderNumber)) {
				orderNumber = item.getOrderNumber();
			}
		}
		if (!isNext) {
			return null;
		}
		boolean isSplit = false;// 是否拆单
		// 查看子订单集合
//		List<MallOrderItem> list = orderItemDao.queryListByOrderNumber(orderNumber);
		List<MallOrderItem> list = orderService.queryOrderItems(orderNumber);
		// 如果item的个数与本次发送过来的item的个数不等说明直接是拆单
		if (list == null) {
			return null;
		}
		if (list.size() != set.size()) {
			isSplit = true;
		}
		if (!isSplit) {
			for (Long id : set) {
//				MallOrderItem item = orderItemDao.selectByPrimaryKey(id);
				MallOrderItem item = orderService.getMallOrderItem(id);
				// 判断本次发货的数量是否与item的数量相等
				if (item.getNum().intValue() != map.get(id).intValue()) {
					isSplit = true;
					break;
				}
			}
		}
		if (!isSplit) {
			// 不进行拆单,说明发送过来的数量都是对的
			returnMap.put("orderNumber", orderNumber);
			returnMap.put("orderItems", orderItems);
		} else {
			String[] arr = orderItems.split(",");
			List<String> listItemsIds = Arrays.asList(arr);
			List<String> listItemsIdsNew = new ArrayList<String>();
			// 拆单
			// int i=0;
//			MallOrder moOld = orderDao.getByOrderNumber(orderNumber);
			MallOrder moOld=orderService.getMallOrder(orderNumber);
			MallOrder moNew = moOld.clone();
			orderNumber = CodeUtil.getOrderNumber(moOld.getUserId());
			moNew.setId(null);
			moNew.setOrderNumber(orderNumber);
			moNew.setParentOrderNumber(moOld.getOrderNumber());
			Map<String, String> mapTemp = new HashMap<String, String>();
			// 插入一条
			// 发生拆单之后需要计算本次拆出去多少个商品，商品总价格
			float num = 0;
			float discountTotal = 0;
			float total = 0;
			for (Long id : set) {
//				MallOrderItem item = orderItemDao.selectByPrimaryKey(id);
				MallOrderItem item = orderService.getMallOrderItem(id);
				// 判断本次发货的数量是否与item的数量不相等
				if (item.getNum().intValue() != map.get(id).intValue()) {
					// 新增一条item记录
					MallOrderItem itemNew = item.clone();
					itemNew.setId(null);
					itemNew.setOrderNumber(orderNumber);
					itemNew.setNum(map.get(id));
//					orderItemDao.insertSelective(itemNew);
					orderService.addMallOrderItem(itemNew);
					MallOrderItem orderItem = orderService.getMallOrderItem(orderNumber, item.getProductModelFormatId());
					// 修改本条item
					float count = item.getNum().intValue() - map.get(id).intValue();
					item.setNum(count);
//					orderItemDao.updateByPrimaryKeySelective(item);
					orderService.updateOrderItem(item);
					// 说明发生了替换动作需要将之前的ItemId换为新的ItemId
					mapTemp.put(id.toString(), orderItem.getId().toString());
				} else {
					// 说明是直接将一个item的货物发完了
					item.setOrderNumber(orderNumber);
//					orderItemDao.updateByPrimaryKeySelective(item);
					orderService.updateOrderItem(item);
					// i=map.get(id).intValue()+1;//商品件数
				}
				num = num + map.get(id);// 本次发货的数量
				discountTotal = discountTotal + (item.getDiscountUnitPrice() * map.get(id));// 本次发货的优惠总金额
				total = total + (item.getUnitPrice() * map.get(id));// 本次发货的总价
			}
			moNew.setTotalNum(num);
			moNew.setTotalPrice(total);
			moNew.setDiscountPrice(discountTotal);
//			orderDao.insertSelective(moNew);
			orderService.addMallOrder(moNew);
			moOld.setTotalNum(moOld.getTotalNum() - num);
			moOld.setTotalPrice(moOld.getTotalPrice() - total);
			moOld.setDiscountPrice(moOld.getDiscountPrice() - discountTotal);
//			orderDao.updateByPrimaryKeySelective(moOld);
			orderService.updateOrder(moOld);
			// orderItems=listItemsIdsNew.toString();
			for (String string : listItemsIds) {
				if (mapTemp.containsKey(string)) {
					listItemsIdsNew.add(mapTemp.get(string));
				} else {
					listItemsIdsNew.add(string);
				}
			}
			orderItems = StringUtils.join(listItemsIdsNew, ",");
			returnMap.put("orderNumber", orderNumber);
			returnMap.put("orderItems", orderItems);
		}
		return returnMap;
	}

	@Transactional
	@Override
	public void insertBatch() {
		SysAdmin admin1 = new SysAdmin();
		admin1.setLoginName("test");
		admin1.setPhone("11111111");
		sysAdminDao.insertSelective(admin1);
		System.out.println(admin1.getId());
		SysRole role = new SysRole();
		role.setName("test1111");
		sysRoleDao.insertSelective(role);
		SysAdmin admin2 = new SysAdmin();
		admin2.setLoginName("test1");
		admin2.setPhone("11111111111111111111111111111111111");
		sysAdminDao.insertSelective(admin2);
		SysRole role2 = new SysRole();
		role2.setName("test11112111111111211111111112222222");
	}

	@Transactional
	@Override
	public Map<String, Object> sendGoods(String hardwares, String orderItems, Long expressId, String expressNumber, HttpServletRequest request) {
		String msg = "";
		int code = 0;
		String resultNumber = "";
		Map<String, Object> mapResult = new HashMap<String, Object>();
		System.out.println("hardwares:" + hardwares);
		System.out.println("orderItems:" + orderItems);
		System.out.println("expressId:" + expressId.intValue());
		System.out.println("expressNumber:" + expressNumber);
		if (expressId != null) {
			if (expressId.intValue() != 1 && StringUtils.isBlank(expressNumber)) {
				// 当页面选的不是德邦物流时，
				code = 3;
				msg = "非德邦物流必须要输入运单号";
			} else {
				if (StringUtils.isNotBlank(orderItems)) {
					// 商品详情IDS
					String[] orderItemIds = org.springframework.util.StringUtils.tokenizeToStringArray(orderItems, ",");
					Long userId = 0L;
					String orderNumber = "";
					Map<String, Float> pruductMap = new HashMap<String, Float>();
					Map<Long, Float> itemMap = new HashMap<Long, Float>();
					int i = 0;
					for (String string : orderItemIds) {
//						MallOrderItem mallOrderItem = orderItemDao.selectByPrimaryKey(Long.parseLong(string));
						MallOrderItem mallOrderItem = orderService.getMallOrderItem(Long.parseLong(string));
						if (mallOrderItem != null && mallOrderItem.getId() != null) {
							if (itemMap.containsKey(mallOrderItem.getId())) {
								Float count = itemMap.get(mallOrderItem.getId()) + 1;
								pruductMap.put(mallOrderItem.getProductTitle(), count);
								itemMap.put(mallOrderItem.getId(), count);
							} else {
								userId = mallOrderItem.getUserId();
								orderNumber = mallOrderItem.getOrderNumber();
								pruductMap.put(mallOrderItem.getProductTitle(), 1f);
								itemMap.put(mallOrderItem.getId(), 1f);
							}
							i++;
						}
					}
					// 执行拆单,返回的是map集合,里面包括拆单后的orderNumber,orderItems
					Map<String, Object> map = splitOrder(itemMap, orderItems);
					if (map == null) {
						code = 5;
						msg = "所选的子订单不是来自于同一订单";
					} else {
						String neworderNumber = map.get("orderNumber").toString();
						resultNumber = neworderNumber;
						orderItems = map.get("orderItems").toString();
//						MallOrder order = getByOrderNumber(orderNumber);// 老的订单
						MallOrder order = orderService.getMallOrder(orderNumber);// 老的订单
						if (!neworderNumber.equals(orderNumber)) {
							// 说明进行了拆单重新生成了订单
							// 判断如果不是德邦的接口直接生成两条记录后完成处理
							// 订单收货人信息表复制一份
//							MallOrder orderNew = getByOrderNumber(neworderNumber);// 新的订单
							MallOrder orderNew = orderService.getMallOrder(neworderNumber);// 新的订单
							insertRevicerInformation(orderNew, order, userId, expressId, expressNumber);
							// 订单状态变更记录
							insertOrderHistoryBatch(orderNumber, neworderNumber);
							// 支付信息
							// insertPay(orderNumber, neworderNumber);//当为拆单时支付信息不需要复制了
							order = orderNew.clone();
						}
						String cargoName = pruductMap.keySet().toString();
						if (expressId.intValue() == 1) {// 走德邦相关业务
							ErpLogisticsInformation eli = new ErpLogisticsInformation();
							eli = initInfo(order, eli, expressId, userId, cargoName, i, request);
							ResultInfo resultInfo = createOrderByDeBang(eli);
							// 判断是否发送成功了
							/*
							 * 响应格式 { "logisticID":"AL353453253", "reason":"成功", "result":"true", "resultCode":"1000", "uniquerRequestNumber":"3600275537833690"
							 * }
							 */
							if (resultInfo != null) {
								if (resultInfo.getResultCode().equals("1000")) {
									// 说明订单处理成功
									// 插入物流订单相关
									erpLogisticsInformationDao.insertSelective(eli);
									// 日志记录表插入
									insertLogisticsLog(eli, resultInfo, request);
									// 订单物流信息表插入
									insertOrderLogistics(eli, order, userId);
									// 订单详细商品与物流信息关系表
									// insertMallOrderItemLogisticsRelation(eli, orderItemIds, request);
									// 订单详细商品与硬件关联表
									insertMallOrderItemHardwareRelation(orderItems, hardwares, request);
									// 修改订单发货数量
									// updateOrderItemSendNum(itemMap);
								} else {
									code = Integer.parseInt(resultInfo.getResultCode());
									msg = getErrorMsg(resultInfo.getResultCode());
									// 这里比较特殊当推送给第三方德邦物流公司失败时需要抛出异常便于事物回滚
									throw new DepponException();
								}
							} else {
								code = 1;
								msg = "物流发送失败";
								// 这里比较特殊当推送给第三方德邦物流公司失败时需要抛出异常便于事物回滚
								throw new DepponException();
							}
						} else {
							// 其他的物流方式
							MallOrderLogistics log = new MallOrderLogistics();
							log.setCreateDateTime(new Date());
							log.setInvalid(InvalidEnum.FALSE.getInvalidValue());
							log.setExpressCompanyId(expressId);
							ErpExpressCompany erpExpressCompany = erpExpressCompanyDao.selectByPrimaryKey(expressId);
							if (erpExpressCompany != null) {
								log.setExpressName(erpExpressCompany.getName());
							}
							log.setOrderId(order.getId());
							log.setUserId(userId);
							log.setOrderNumber(order.getOrderNumber());
							log.setExpressNo(expressNumber);
							// log.setLogisticId(eli.getLogisticId());
							mallOrderLogisticsDao.insertSelective(log);
						}
						// 修改硬件相关状态信息
						updateProductStatus(hardwares, WebUtil.getLoginUser(request));
						// 修改订单状态为发货
//						updateStatus(neworderNumber, OrderStatusEnum.SIPPING);
						orderService.updateOrderStatus(OrderStatus.SIPPING.getStatus(), neworderNumber);
						// 出库记录添加
						insertOutStorage(order, hardwares, WebUtil.getLoginUser(request), expressId, expressNumber, userId);
						// 插入订单history记录
//						insertOrderHistory(neworderNumber, OrderStatusEnum.SIPPING.getCode(), WebUtil.getLoginUser(request).getId(), userId,request.getRemoteAddr());
						orderService.addOrderHistory(neworderNumber, WebUtil.getLoginUser(request).getId(), userId, request.getRemoteAddr(), OrderStatus.SIPPING.getStatus(), OrderOperateTye.ADMINISTRATOR.getOperateType());
						if (expressId.intValue() != 1) {
//							updateRevicerInformationExpressNo(neworderNumber, expressNumber);
							orderService.updateMallOrderRevicerInformation(expressNumber, neworderNumber);
							updateOrderLogisticsExpressNo(neworderNumber, expressNumber);
						}
						// 修改库存
						Map<Long, Integer> mapTemp = new HashMap<Long, Integer>();
						orderItemIds = org.springframework.util.StringUtils.tokenizeToStringArray(orderItems, ",");
						// 重新计算发货后的种类
						for (String string : orderItemIds) {
//							MallOrderItem mallOrderItem = orderItemDao.selectByPrimaryKey(Long.parseLong(string));
							MallOrderItem mallOrderItem = orderService.getMallOrderItem(Long.parseLong(string));
							if (mallOrderItem != null && mallOrderItem.getId() != null) {
								if (mapTemp.containsKey(mallOrderItem.getProductModelFormatId())) {
									Integer count = mapTemp.get(mallOrderItem.getProductModelFormatId()) + 1;
									mapTemp.put(mallOrderItem.getProductModelFormatId(), count);
								} else {
									mapTemp.put(mallOrderItem.getProductModelFormatId(), 1);
								}
							}
						}
						Set<Long> set = mapTemp.keySet();
						for (Long formatId : set) {
							int count = mapTemp.get(formatId);
							MallBasicCategoryModelFormat modelFormat = categoryQueryService.getCategoryModelFormat(formatId);
							if (modelFormat != null && modelFormat.getId() != null) {
								int stock = modelFormat.getRealStock();
								// 库存大于等于当前需要发货的数量
								if (stock >= count) {
									stock = stock - count;
									int warnCount = modelFormat.getWarnCount();
									// 判断是否发出预警通知
									if (warnCount > stock) {
										// 说明设置的下线已超过库存量发出预警通知
										insertNotice(WebUtil.getLoginUser(request), modelFormat, stock, modelFormat.getRealStock());
									}
									updateStock(modelFormat.getId(), -count);
								} else {
									// 库存不足发货失败
									throw new LowStocksException();
								}
							}
						}
						//如果发货成功，发送一条短信给代理商对应的业务员
						mallOrderProducer.send("queue.ready", neworderNumber);
						//如果发货成功，发送一条短信给代理商
						mallOrderProducer.send("queue.merchant", neworderNumber);
					}
				} else {
					code = 4;
					msg = "未选中任何发货子菜单";
				}
			}
		} else {
			code = 2;
			msg = "物流ID不可为空";
		}
		mapResult.put("code", code);
		mapResult.put("msg", msg);
		mapResult.put("number", resultNumber);
		return mapResult;
	}

	private ErpLogisticsInformation initInfo(MallOrder order, ErpLogisticsInformation eli, Long expressId, Long userId, String cargoName, int i,
			HttpServletRequest request) {
		eli.setCreateDateTime(new Date());
		eli.setStatus(StatusDatabaseMappingEnum.ORDER_STATUS_WAITACCEPT.getCode());
		eli.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		eli.setExpressId(expressId);
		eli.setLogisticCompanyId(DOPConstans.LOGISTIC_PROVIDERID_DEFAULT);
		eli.setLogisticId(order.getOrderNumber());
		eli.setMailNo(null);
		eli.setOrderSource(companyCode);// 与companyCode保持一致需要德邦开发人员提供 ???
		eli.setServiceType(serviceType);
		eli.setCustomerId(userId.toString());
		eli.setBusinessNetworkNo(businessNetworkNo);
		eli.setToNetworkNo(null);
		eli.setCargoName(cargoName);
		eli.setSpecial(null);
		eli.setTotalNumber(i);
		eli.setTotalWeight(0.0);
		eli.setTotalVolume(0.0);
		eli.setPayType("1");
		eli.setTransportType("PACKAGE");// 运输方式 HK_JZKY:精准空运 QC_JZKH:精准卡航 QC_JZQYC:精准汽运（长） QC_JZQYD:精准汽运（短） QC_JZCY:精准城运PACKAGE：经济快递（快递，签订快递合同时使用，于零担物流区分开） ???
		eli.setInsuranceValue(null);
		eli.setCodType(null);
		eli.setCodValue(null);
		eli.setVistReceive(null);// 是否上门接货 ???
		eli.setSendStartTime(null);
		eli.setSendEndTime(null);
		eli.setDeliveryType("3");// 0:自提 1:送货（不含上楼） 2:机场自提 3:送货上楼 ??
		eli.setBackSignBill("0");// 0:无需返单 2:客户签收单传真返回 4: 运单到达联传真返回 ??
		eli.setPackageService(null);
		eli.setSmsNotify("1");// 短信通知需要
		eli.setRemark(null);
		eli.setBackResult(null);
		eli.setAdminId(WebUtil.getLoginUser(request).getId());
		eli.setRevicerInformation(orderService.getRevicerByUserIdAndOrderNumber(userId, order.getOrderNumber()));// 收货人信息相关
		eli.setSenderAdmin(WebUtil.getLoginUser(request));// 发货人信息
		return eli;
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
		erpLogisticsLogDao.insertSelective(log);
	}

	private void insertOrderLogistics(ErpLogisticsInformation eli, MallOrder order, Long userId) {
		MallOrderLogistics log = new MallOrderLogistics();
		log.setCreateDateTime(new Date());
		log.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		log.setExpressCompanyId(eli.getExpressId());
		ErpExpressCompany erpExpressCompany = erpExpressCompanyDao.selectByPrimaryKey(eli.getExpressId());
		if (erpExpressCompany != null) {
			log.setExpressName(erpExpressCompany.getName());
		}
		log.setOrderId(order.getId());
		log.setUserId(userId);
		log.setOrderNumber(order.getOrderNumber());
		log.setLogisticId(eli.getLogisticId());
		mallOrderLogisticsDao.insertSelective(log);
	}

	/*
	 * private void insertMallOrderItemLogisticsRelation(ErpLogisticsInformation eli,String[] orderItemIds,HttpServletRequest request){ Map<String, Integer>
	 * pruductMap = new HashMap<String, Integer>(); for (String string : orderItemIds) { //归纳总结货物中有哪些货物是同一个 //MallOrderItem mallOrderItem =
	 * mallOrderItemService.get(Long.parseLong(string)); if(pruductMap.containsKey(string)){ Integer count = pruductMap.get(string)+1; pruductMap.put(string,
	 * count); }else{ pruductMap.put(string, 1); } } Set<String> oids = pruductMap.keySet(); for (String string : oids) { int count = pruductMap.get(string);
	 * MallOrderItem mallOrderItem = orderItemDao.selectByPrimaryKey(Long.parseLong(string)); MallOrderItemLogisticsRelation moir = new
	 * MallOrderItemLogisticsRelation(); moir.setCreateDateTime(new Date()); moir.setInvalid(InvalidEnum.FALSE.getInvalidValue());
	 * moir.setAdminId(WebUtil.getLoginUser(request).getId()); moir.setOrderItemId(Long.parseLong(string)); moir.setOrderNumber(mallOrderItem.getOrderNumber());
	 * moir.setCategoryModelFormatId(mallOrderItem.getProductModelFormatId()); moir.setLogisticsId(eli.getId()); moir.setSendNum(count);
	 * mallOrderItemLogisticsRelationDao.insertSelective(moir); } }
	 */
	private void insertMallOrderItemHardwareRelation(String orderItems, String hardwares, HttpServletRequest request) {
		if (StringUtils.isNotBlank(orderItems)) {
			// 商品详情IDS
			String[] orderItemIds = org.springframework.util.StringUtils.tokenizeToStringArray(orderItems, ",");
			String[] hardwareIds = org.springframework.util.StringUtils.tokenizeToStringArray(hardwares, ",");
			for (int i = 0; i < orderItemIds.length; i++) {
				MallOrderItemHardwareRelation moir = new MallOrderItemHardwareRelation();
				moir.setCreateDateTime(new Date());
				moir.setInvalid(InvalidEnum.FALSE.getInvalidValue());
				moir.setAdminId(WebUtil.getLoginUser(request).getId());
				moir.setOrderItemId(Long.parseLong(orderItemIds[i]));
				moir.setHardwareProductId(Long.parseLong(hardwareIds[i]));
//				MallOrderItem mallOrderItem = orderItemDao.selectByPrimaryKey(Long.parseLong(orderItemIds[i]));
				MallOrderItem mallOrderItem = orderService.getMallOrderItem(Long.parseLong(orderItemIds[i]));
				ErpHardwareProduct erpHardwareProduct = erpHardwareProductDao.selectByPrimaryKey(Long.parseLong(hardwareIds[i]));
				moir.setUniqueCode(erpHardwareProduct.getUniqueCode());
				moir.setCategoryModelFormatId(mallOrderItem.getProductModelFormatId());
				moir.setIsCustomMade(mallOrderItem.getIsCustomMade());
				mallOrderItemHardwareRelationDao.insertSelective(moir);
			}
		}
	}

	/*
	 * private void updateOrderItemSendNum(Map<Long, Integer> map){ Set<Long> set = map.keySet(); for (Long id : set) { MallOrderItem moi =
	 * orderItemDao.selectByPrimaryKey(id); if(moi != null && moi.getId() != null){ int sendCount = moi.getSendedNum()+map.get(id); moi.setSendedNum(sendCount);
	 * orderItemDao.updateByPrimaryKeySelective(moi); } } }
	 */
	/**
	 * 修改硬件信息
	 * 
	 * @author zhaolei
	 * @date 2015年10月29日
	 * @param hardwares
	 * @param loginUser
	 */
	public void updateProductStatus(String hardwares, SysAdmin loginUser) {
		if (StringUtils.isNotBlank(hardwares)) {
			String[] hardwareIds = org.springframework.util.StringUtils.tokenizeToStringArray(hardwares, ",");
			for (String string : hardwareIds) {
				if (StringUtils.isNotBlank(string)) {
					ErpHardwareProduct edp = erpHardwareProductDao.selectByPrimaryKey(Long.parseLong(string));
					if (edp != null && edp.getId() != null) {
						edp.setStatus(HardwareProductNetStatusEnum.DELIVERGOOD.getCode());
						erpHardwareProductDao.updateByPrimaryKeySelective(edp);
						// 插入物联网信息
						insertProductLog(loginUser, edp.getId());
						// 清除防伪扫描记录
						scanRecodDao.deleteByMacCode(edp.getMacAddress());
					}
				}
			}

		}
	}

	/**
	 * 插入物联网相关信息
	 * 
	 * @author zhaolei
	 * @date 2015年10月29日
	 * @param loginUser
	 * @param productId
	 */
	private void insertProductLog(SysAdmin loginUser, Long productId) {
		ErpHardwareProductChangeLog log = new ErpHardwareProductChangeLog();
		log.setHardwareProductId(productId);
		log.setCreateDateTime(new Date());
		log.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		log.setStatus(Status.NORMAL.getStatus());
		log.setUserId(loginUser.getId());
		log.setUserName(loginUser.getUserName());
		log.setOperateId(HardwareProductNetStatusEnum.DELIVERGOOD.getCode().longValue());
		log.setOperateExplain("发货");
		erpHardwareProductChangeLogDao.insertSelective(log);
	}

	/**
	 * 1000 成功 处理成功 正常处理 1001 订单已存在 订单已经存在 1002 订单不存在 订单不存在 1003 订单已存在 订单已存在 1004 操作不允许 只能操作本公司订单 1005 运单不存在 运单不存在 1006 流水号不存在 货物跟踪，流水号不存在 1007 订单不允许撤销
	 * 当前状态下订单不允许撤销 2001 白名单验证失败 2002 摘要验证失败 2003 时间戳验证失败 2004 公司编码验证失败 2005 参数转换失败 2006 参数校验失败 校验失败的参数 3001 接口调用失败 调用接口失败 重发 9000 未知异常 9999 系统异常 重试
	 */
	public String getErrorMsg(String code) {
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

	/**
	 * 出库信息插入
	 * 
	 * @author zhaolei
	 * @date 2015年11月4日
	 */
	private void insertOutStorage(MallOrder order, String hardwares, SysAdmin loginUser, Long exId, String exStr, Long userId) {
//		MallOrderRevicerInformation information = mallOrderRevicerInformationDao.getByOrderNumberAndUserId(order.getOrderNumber(), userId);
		MallOrderRevicerInformation information = orderService.getRevicerByUserIdAndOrderNumber(userId, order.getOrderNumber());
		if (StringUtils.isNotBlank(hardwares)) {
			String[] hardwareIds = org.springframework.util.StringUtils.tokenizeToStringArray(hardwares, ",");
			for (String string : hardwareIds) {
				if (StringUtils.isNotBlank(string)) {
					ErpOutStorage eos = new ErpOutStorage();
					eos.setCreateDateTime(new Date());
					eos.setInvalid(InvalidEnum.FALSE.getInvalidValue());
					eos.setStatus(Status.NORMAL.getStatus());
					eos.setAdminId(loginUser.getId());
					eos.setOrderId(order.getId());
					eos.setOrderNumber(order.getOrderNumber());
					eos.setLogisticsCompanyId(exId);
					if (StringUtils.isNotBlank(exStr)) {
						// 运单号不为空插入
						eos.setWaybillNumber(exStr);
					}
					if (information != null) {
						eos.setBuyId(userId);
						eos.setBuyAddress(information.getDetailAddress());
						eos.setBuyNickname(information.getReveiveUserName());
						eos.setPhone(information.getMobilePhone());
						eos.setLinkman(information.getReveiveUserName());
					}
					eos.setHardwareProductId(Long.parseLong(string));
					erpOutStorageDao.insertSelective(eos);
				}
			}

		}
	}


	/**
	 * 支付信息复制新增拆单发生后使用 订单记录表插入
	 * 
	 * @author zhaolei
	 * @date 2015年11月5日
	 * @param oldOrderNumber
	 * @param newOrderNumber
	 * @param loginUser
	 * @param userId
	 * @param request
	 */
	private void insertOrderHistoryBatch(String oldOrderNumber, String newOrderNumber) {
		// 说明是拆单的
		// 通过老的订单号查询相关记录列表
//		List<MallOrderHistory> list = mallorderHistoryDao.selectByOrderNumber(oldOrderNumber);
		List<MallOrderHistory> list = orderService.queryHistoryByOrderNumber(oldOrderNumber);
		if (list != null && list.size() > 0) {
			for (MallOrderHistory mallOrderHistory : list) {
//				mallOrderHistory.setId(null);
//				mallOrderHistory.setOrderNumber(newOrderNumber);
//				mallorderHistoryDao.insertSelective(mallOrderHistory);
				orderService.addOrderHistory(newOrderNumber, mallOrderHistory.getAdminId(), mallOrderHistory.getUserId(), mallOrderHistory.getOperatorIp(), mallOrderHistory.getStatus(), mallOrderHistory.getOperatorType());
			}
		}
	}

	/**
	 * 复制新增拆单发生后使用 复制插入一份收货人信息表信息
	 * 
	 * @author zhaolei
	 * @date 2015年11月5日
	 * @param orderNew
	 * @param order
	 * @param userId
	 * @param expressId
	 * @param expressNumber
	 */
	private void insertRevicerInformation(MallOrder orderNew, MallOrder order, Long userId, Long expressId, String expressNumber) {
//		MallOrderRevicerInformation orderRevicerInformation = mallOrderRevicerInformationDao.getByOrderNumberAndUserId(order.getOrderNumber(), userId);
		MallOrderRevicerInformation orderRevicerInformation = orderService.getRevicerByUserIdAndOrderNumber(userId, order.getOrderNumber());
		orderRevicerInformation.setId(null);
		orderRevicerInformation.setOrderId(orderNew.getId());
		orderRevicerInformation.setOrderNumber(orderNew.getOrderNumber());
		if (expressId.intValue() != 1 && StringUtils.isNotBlank(expressNumber)) {
			orderRevicerInformation.setExpressNo(expressNumber);
		}
//		mallOrderRevicerInformationDao.insertSelective(orderRevicerInformation);
		orderService.saveMallOrderRevicerInformation(orderRevicerInformation);
	}

	/**
	 * 支付信息复制新增拆单发生后使用
	 * 
	 * @author zhaolei
	 * @date 2015年11月5日
	 * @param oldOrderNumber
	 * @param newOrderNumber
	 */
	private void insertPay(String oldOrderNumber, String newOrderNumber) {
		List<MallPay> list = mallPayDao.getListByOrderNumber(oldOrderNumber);
		if (list != null && list.size() > 0) {
			for (MallPay mallPay : list) {
				mallPay.setId(null);
				mallPay.setOrderNumber(newOrderNumber);
				mallPayDao.insertSelective(mallPay);
			}
		}
	}

	/**
	 * 修改快递信息的快递单号
	 * 
	 * @author zhaolei
	 * @date 2015年11月5日
	 * @param orderNumber
	 * @param expressNumber
	 */
	public void updateOrderLogisticsExpressNo(String orderNumber, String expressNumber) {
		mallOrderLogisticsDao.updateExpressNoByOrderNumber(orderNumber, expressNumber);
	}

	/**
	 * 插入通知
	 * 
	 * @author zhaolei
	 * @date 2015年11月6日
	 * @param loginAdmin
	 * @param modelFormat
	 * @param newStock
	 * @param oldStock
	 */
	public void insertNotice(SysAdmin loginAdmin, MallBasicCategoryModelFormat modelFormat, Integer newStock, Integer oldStock) {
		ErpNoticeInfo info = new ErpNoticeInfo();
		info.setAdminId(loginAdmin.getId());
		info.setCreateDateTime(new Date());
		info.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		ErpNoticeCategory erpNoticeCategory = new ErpNoticeCategory();
		erpNoticeCategory.setId(2L);
		info.setErpNoticeCategory(erpNoticeCategory);
		info.setName("库存预警:" + modelFormat.getBasicCategoryModel().getName() + ">" + modelFormat.getName() + "现库存[" + newStock + "]严重不足请补仓");
		info.setDescription("产品:" + modelFormat.getBasicCategoryModel().getName() + ">" + modelFormat.getName() + "<br/>现库存量:" + newStock + "<br/>原库存量:"
				+ oldStock + "<br/>已低于预设值下线请相关人员及时补仓.");
		noticeDao.insertSelective(info);
	}

	/**
	 * 修改库存量
	 * 
	 * @author zhaolei
	 * @date 2015年11月6日
	 * @param id
	 * @param stock
	 */
	public void updateStock(long id, Integer stock) {
		categoryCUDService.updateStockById(id, stock);
	}


	// 更新私人定制订单状态为26，并插入订单history记录
	@Override
	@Transactional
	public void changeCustomMadeOrder(OrderStatus orderStatus, Long hardwareProductId, Long adminId, String ip) {
		MallOrderItemHardwareRelation mallOrderItemHardwareRelation = mallOrderItemHardwareRelationDao.selectByHardwareProductId(hardwareProductId);
//		MallOrderItem orderItem = orderItemDao.selectByPrimaryKey(mallOrderItemHardwareRelation.getOrderItemId());
		MallOrderItem orderItem =orderService.getMallOrderItem(mallOrderItemHardwareRelation.getOrderItemId());
		if (orderItem == null) {
			return;
		}
//		orderDao.updateStatus(orderItem.getOrderNumber(), orderStatus.getCode());
		orderService.updateOrderStatus(orderStatus.getStatus(), orderItem.getOrderNumber());
//		insertOrderHistory(orderItem.getOrderNumber(), orderStatus.getCode(), adminId, null, ip);
		orderService.addOrderHistory(orderItem.getOrderNumber(), adminId, null, ip, orderStatus.getStatus(), OrderOperateTye.ADMINISTRATOR.getOperateType());
	}

	@Override
	@Transactional
	public void changeCustomMadeOrder(OrderStatus orderStatus, MallOrderItem orderItem, Long adminId, String ip) {
//		orderDao.updateStatus(orderItem.getOrderNumber(), orderStatus.getCode());
		orderService.updateOrderStatus(orderStatus.getStatus(), orderItem.getOrderNumber());
//		insertOrderHistory(orderItem.getOrderNumber(), orderStatus.getCode(), adminId, null, ip);
		orderService.addOrderHistory(orderItem.getOrderNumber(), adminId, null, ip, orderStatus.getStatus(), OrderOperateTye.ADMINISTRATOR.getOperateType());
	}

}
