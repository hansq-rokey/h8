package com.ibaixiong.erp.service.afterSale.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.deppon.dop.module.sdk.shared.domain.ResultInfo;
import com.deppon.dop.module.sdk.shared.domain.common.DOPConstans;
import com.deppon.dop.module.sdk.shared.domain.common.StatusDatabaseMappingEnum;
import com.ibaixiong.constant.Constant;
import com.ibaixiong.constant.Constant.Status;
import com.ibaixiong.entity.ErpExpressCompany;
import com.ibaixiong.entity.ErpLogisticsInformation;
import com.ibaixiong.entity.ErpLogisticsLog;
import com.ibaixiong.entity.ErpOutStorage;
import com.ibaixiong.entity.MallOrderLogistics;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.dao.storage.ErpExpressCompanyDao;
import com.ibaixiong.erp.dao.storage.ErpLogisticsInformationDao;
import com.ibaixiong.erp.dao.storage.ErpLogisticsLogDao;
import com.ibaixiong.erp.dao.storage.ErpOutStorageDao;
import com.ibaixiong.erp.dao.storage.MallOrderLogisticsDao;
import com.ibaixiong.erp.exception.DepponException;
import com.ibaixiong.erp.exception.LowStocksException;
import com.ibaixiong.erp.service.afterSale.MallAfterSaleService;
import com.ibaixiong.erp.service.storage.MallOrderService;
import com.ibaixiong.erp.service.storage.impl.MallOrderServiceImpl;
import com.ibaixiong.erp.util.InvalidEnum;
import com.ibaixiong.erp.web.util.WebUtil;
import com.papabear.order.api.AfterSaleService;
import com.papabear.order.api.OrderService;
import com.papabear.order.entity.MallAfterSalesService;
import com.papabear.order.entity.MallAfterSalesServiceItem;
import com.papabear.order.entity.MallAfterSalesServiceLog;
import com.papabear.order.entity.MallOrderRevicerInformation;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.entity.MallBasicCategoryModelFormat;
@Service
public class MallAfterSaleServiceImpl implements MallAfterSaleService {
	@Resource
	private MallOrderService mallOrderService;
	@Resource
	ErpLogisticsInformationDao erpLogisticsInformationDao;
	@Resource
	ErpLogisticsLogDao erpLogisticsLogDao;
	@Resource
	ErpExpressCompanyDao erpExpressCompanyDao;
	@Resource
	MallOrderLogisticsDao mallOrderLogisticsDao;
	@Resource
	ErpOutStorageDao erpOutStorageDao;
	@Resource
	private AfterSaleService afterSaleService;
	@Resource
	private OrderService orderService;
	@Resource
	private CategoryQueryService categoryQueryService;
//	@Resource
//	BasicCategoryModelFormatDao basicCategoryModelFormatDao;
	@Transactional
	@Override
	public Map<String, Object> sendGoods(String hardwares, String orderId,
			Long expressId, String expressNumber, HttpServletRequest request) {
		String msg = "";
		int code = 0;
		String resultNumber = "";
		Map<String, Object> mapResult = new HashMap<String, Object>();
		System.out.println("hardwares:"+hardwares);
		System.out.println("orderId:"+orderId);
		System.out.println("expressId:"+expressId.intValue());
		System.out.println("expressNumber:"+expressNumber);
		if(expressId !=null){
			if(expressId.intValue() != 1 && StringUtils.isBlank(expressNumber)){
				//当页面选的不是德邦物流时，
				code = 3;
				msg = "非德邦物流必须要输入运单号";
			}else{
				if(StringUtils.isNotBlank(orderId)){
					//商品详情IDS
					Long userId = 0L;
					String orderNumber = "";
					Map<String, Integer> pruductMap = new HashMap<String, Integer>();
					Map<Long, Integer> itemMap = new HashMap<Long, Integer>();
					int i = 0 ;
					//通过售后订单ID查询出来订单详细
//					List<MallAfterSalesServiceItem> list = mallAfterSalesServiceItemDao.queryListByServiceId(Long.parseLong(orderId));
					List<MallAfterSalesServiceItem> list = afterSaleService.queryAfterSalesServiceItems(Long.parseLong(orderId));
					for (MallAfterSalesServiceItem mallAfterSalesServiceItem : list) {
						if(mallAfterSalesServiceItem != null && mallAfterSalesServiceItem.getId() != null){
							if(itemMap.containsKey(mallAfterSalesServiceItem.getProductModelFormatId())){
								Integer count = itemMap.get(mallAfterSalesServiceItem.getProductModelFormatId())+1;
								pruductMap.put(mallAfterSalesServiceItem.getProductTitle(), count);
								itemMap.put(mallAfterSalesServiceItem.getProductModelFormatId(), count);
							}else{
								pruductMap.put(mallAfterSalesServiceItem.getProductTitle(), 1);
								itemMap.put(mallAfterSalesServiceItem.getProductModelFormatId(), 1);
							}
							i++;
						}
					}
					//查询相关售后订单
//					MallAfterSalesService order = mallAfterSalesServiceDao.selectByPrimaryKey(Long.parseLong(orderId));
					MallAfterSalesService order = afterSaleService.getAfterSalesService(Long.parseLong(orderId));
					orderNumber = order.getServiceNumber();
					resultNumber = order.getServiceNumber();
					userId = order.getUserId();
					String cargoName = pruductMap.keySet().toString();
					if(expressId.intValue() == 1){//走德邦相关业务
						ErpLogisticsInformation eli = new ErpLogisticsInformation();
						eli = initInfo(order,eli, expressId, userId,cargoName,i,request);
						ResultInfo resultInfo = mallOrderService.createOrderByDeBang(eli);
						//判断是否发送成功了
						/*响应格式	
						{
							"logisticID":"AL353453253",
							"reason":"成功",
							"result":"true", 
							"resultCode":"1000",
							"uniquerRequestNumber":"3600275537833690"
						}*/
						if(resultInfo != null ){
							if( resultInfo.getResultCode().equals("1000")){
								//说明订单处理成功
								//插入物流订单相关
								erpLogisticsInformationDao.insertSelective(eli);
								//日志记录表插入
								insertLogisticsLog(eli, resultInfo, request);
								//订单物流信息表插入
								insertOrderLogistics(eli,order,userId);
								//订单详细商品与物流信息关系表
								//insertMallOrderItemLogisticsRelation(eli, orderItemIds, request);
								//订单详细商品与硬件关联表
								//insertMallOrderItemHardwareRelation(orderItems, hardwares, request);
								//修改订单发货数量
								//updateOrderItemSendNum(itemMap);
							}else{
								code = Integer.parseInt(resultInfo.getResultCode());
								msg = mallOrderService.getErrorMsg(resultInfo.getResultCode());
								//这里比较特殊当推送给第三方德邦物流公司失败时需要抛出异常便于事物回滚
								throw new DepponException();
							}
						}else{
							code = 1;
							msg = "物流发送失败";
							//这里比较特殊当推送给第三方德邦物流公司失败时需要抛出异常便于事物回滚
							throw new DepponException();
						}
					}else{
						//其他的物流方式
						MallOrderLogistics log = new MallOrderLogistics();
						log.setCreateDateTime(new Date());
						log.setInvalid(InvalidEnum.FALSE.getInvalidValue());
						log.setExpressCompanyId(expressId);
						ErpExpressCompany erpExpressCompany = erpExpressCompanyDao.selectByPrimaryKey(expressId);
						if(erpExpressCompany != null){
							log.setExpressName(erpExpressCompany.getName());
						}
						log.setOrderId(order.getId());
						log.setUserId(userId);
						log.setOrderNumber(order.getServiceNumber());
						log.setExpressNo(expressNumber);
						//log.setLogisticId(eli.getLogisticId());
						mallOrderLogisticsDao.insertSelective(log);
					}
					//修改硬件相关状态信息
					mallOrderService.updateProductStatus(hardwares,WebUtil.getLoginUser(request));
					//修改订单状态为发货
					//updateStatus(neworderNumber, OrderStatusEnum.SIPPING);
					order.setStatus(Byte.parseByte("50"));
//					mallAfterSalesServiceDao.updateByPrimaryKeySelective(order);
					afterSaleService.updateAfterSalesService(Byte.parseByte("50"), null, order.getId());
					//出库记录添加
					insertOutStorage(order, hardwares, WebUtil.getLoginUser(request), expressId, expressNumber, userId);
					//插入订单history记录
					//insertOrderHistory(orderNumber,OrderStatusEnum.SIPPING.getCode(), WebUtil.getLoginUser(request), userId, request);
					insertAfterSaleServiceLog(WebUtil.getLoginUser(request), order);
					if(expressId.intValue()!=1){
//						orderService.updateRevicerInformationExpressNo(orderNumber, expressNumber);
						orderService.updateMallOrderRevicerInformation(expressNumber, orderNumber);
						mallOrderService.updateOrderLogisticsExpressNo(orderNumber, expressNumber);
						
					}
					//修改库存
					
					Set<Long> set = itemMap.keySet();
					for (Long formatId : set) {
						int count = itemMap.get(formatId);
//						MallBasicCategoryModelFormat modelFormat = basicCategoryModelFormatDao.selectByPrimaryKey(formatId);
						MallBasicCategoryModelFormat modelFormat = categoryQueryService.getCategoryModelFormat(formatId);
						
						if(modelFormat!=null && modelFormat.getId()!=null){
							int stock = modelFormat.getRealStock();
							//库存大于等于当前需要发货的数量
							if(stock>=count){
								stock=stock-count;
								int warnCount = modelFormat.getWarnCount();
								//判断是否发出预警通知
								if(warnCount>stock){
									//说明设置的下线已超过库存量发出预警通知
									mallOrderService.insertNotice( WebUtil.getLoginUser(request), modelFormat, stock, modelFormat.getRealStock());
								}
								mallOrderService.updateStock(modelFormat.getId(), -count);
							}else{
								//库存不足发货失败
								throw new LowStocksException();
							}
						}
					}
				}
			}
		}else{
			code = 2;
			msg = "物流ID不可为空";
		}
		mapResult.put("code",code);
		mapResult.put("msg",msg);
		mapResult.put("number", resultNumber);
		return mapResult;
	}
	private ErpLogisticsInformation initInfo(MallAfterSalesService order,ErpLogisticsInformation eli,Long expressId,Long userId,String cargoName,int i,HttpServletRequest request){
		eli.setCreateDateTime(new Date());
		eli.setStatus(StatusDatabaseMappingEnum.ORDER_STATUS_WAITACCEPT.getCode());
		eli.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		eli.setExpressId(expressId);
		eli.setLogisticCompanyId(DOPConstans.LOGISTIC_PROVIDERID_DEFAULT);
		eli.setLogisticId(order.getServiceNumber());
		eli.setMailNo(null);
		eli.setOrderSource(MallOrderServiceImpl.companyCode);// 与companyCode保持一致需要德邦开发人员提供   ???
		eli.setServiceType(MallOrderServiceImpl.serviceType);
		eli.setCustomerId(userId.toString());
		eli.setBusinessNetworkNo(MallOrderServiceImpl.businessNetworkNo);
		eli.setToNetworkNo(null);
		eli.setCargoName(cargoName);
		eli.setSpecial(null);
		eli.setTotalNumber(i);
		eli.setTotalWeight(0.0);
		eli.setTotalVolume(0.0);
		eli.setPayType("1");
		eli.setTransportType("PACKAGE");//运输方式 HK_JZKY:精准空运 QC_JZKH:精准卡航 QC_JZQYC:精准汽运（长） QC_JZQYD:精准汽运（短） QC_JZCY:精准城运PACKAGE：经济快递（快递，签订快递合同时使用，于零担物流区分开）  ???
		eli.setInsuranceValue(null);
		eli.setCodType(null);
		eli.setCodValue(null);
		eli.setVistReceive(null);//是否上门接货 ???
		eli.setSendStartTime(null);
		eli.setSendEndTime(null);
		eli.setDeliveryType("3");//0:自提 1:送货（不含上楼） 2:机场自提 3:送货上楼  ??
		eli.setBackSignBill("0");//0:无需返单 2:客户签收单传真返回 4: 运单到达联传真返回 ??
		eli.setPackageService(null);
		eli.setSmsNotify("1");//短信通知需要
		eli.setRemark(null);
		eli.setBackResult(null);
		eli.setAdminId(WebUtil.getLoginUser(request).getId());
		eli.setRevicerInformation(orderService.getRevicerByUserIdAndOrderNumber(userId, order.getServiceNumber()));//收货人信息相关
		eli.setSenderAdmin(WebUtil.getLoginUser(request));//发货人信息
		return eli;
	}
	private void insertLogisticsLog(ErpLogisticsInformation eli,ResultInfo resultInfo,HttpServletRequest request){
		ErpLogisticsLog log = new ErpLogisticsLog();
		log.setCreateDateTime(new Date());
		log.setStatus(StatusDatabaseMappingEnum.ORDER_STATUS_WAITACCEPT.getCode());
		log.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		log.setAdminId(WebUtil.getLoginUser(request).getId());
		log.setLogisticsId(eli.getId());
		log.setResult(JSON.toJSONString(resultInfo, true));
		log.setRequestType(Byte.parseByte("1"));//白熊发起
		erpLogisticsLogDao.insertSelective(log);
	}
	private void insertOrderLogistics(ErpLogisticsInformation eli,MallAfterSalesService order,Long userId){
		MallOrderLogistics log = new MallOrderLogistics();
		log.setCreateDateTime(new Date());
		log.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		log.setExpressCompanyId(eli.getExpressId());
		ErpExpressCompany erpExpressCompany = erpExpressCompanyDao.selectByPrimaryKey(eli.getExpressId());
		if(erpExpressCompany != null){
			log.setExpressName(erpExpressCompany.getName());
		}
		log.setOrderId(order.getId());
		log.setUserId(userId);
		log.setOrderNumber(order.getServiceNumber());
		log.setLogisticId(eli.getLogisticId());
		mallOrderLogisticsDao.insertSelective(log);
	}
	/**
	 * 出库信息插入
	 * @author zhaolei
	 * @date 2015年11月4日
	 */
	private void insertOutStorage(MallAfterSalesService order,String hardwares,SysAdmin loginUser,Long exId,String exStr,Long userId){
//		MallOrderRevicerInformation information = mallOrderRevicerInformationDao.getByOrderNumberAndUserId(order.getServiceNumber(),userId);
		MallOrderRevicerInformation information = orderService.getRevicerByUserIdAndOrderNumber(userId, order.getServiceNumber());
		
		if(StringUtils.isNotBlank(hardwares)){
			String[] hardwareIds = org.springframework.util.StringUtils.tokenizeToStringArray(hardwares, ",");
			for (String string : hardwareIds) {
				if(StringUtils.isNotBlank(string)){
					ErpOutStorage eos = new ErpOutStorage();
					eos.setCreateDateTime(new Date());
					eos.setInvalid(InvalidEnum.FALSE.getInvalidValue());
					eos.setStatus(Status.NORMAL.getStatus());
					eos.setAdminId(loginUser.getId());
					eos.setOrderId(order.getId());
					eos.setOrderNumber(order.getServiceNumber());
					eos.setLogisticsCompanyId(exId);
					if(StringUtils.isNotBlank(exStr)){
						//运单号不为空插入
						eos.setWaybillNumber(exStr);
					}
					if(information != null){
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
	private void insertAfterSaleServiceLog(SysAdmin loginAdmin,MallAfterSalesService service){
//		MallAfterSalesServiceLog log = new MallAfterSalesServiceLog();
//		log.setAdmin(loginAdmin);//处理人是当前登陆人
//		log.setService(service);
//		log.setCreateDateTime(new Date());
//		log.setServiceStatus(Byte.parseByte("50"));
//		log.setStatus(Constant.Status.NORMAL.getStatus());
//		mallAfterSaleLogDao.insertSelective(log);
		afterSaleService.addAfterSalesServiceLog(loginAdmin.getId(), service.getId(), Byte.parseByte("50"));
	}
}
