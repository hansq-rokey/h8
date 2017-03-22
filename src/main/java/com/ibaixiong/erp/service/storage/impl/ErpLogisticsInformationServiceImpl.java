package com.ibaixiong.erp.service.storage.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.dop.module.sdk.shared.domain.common.ResultCode;
import com.deppon.dop.module.sdk.shared.domain.common.StatusDatabaseMappingEnum;
import com.deppon.dop.module.sdk.shared.domain.sync.UpdateStatusRequest;
import com.deppon.dop.module.sdk.shared.util.FastJsonUtil;
import com.deppon.dop.module.sdk.shared.util.HttpUtils;
import com.deppon.dop.module.sdk.shared.util.SecurityUtil;
import com.ibaixiong.entity.ErpLogisticsInformation;
import com.ibaixiong.entity.ErpLogisticsLog;
import com.ibaixiong.entity.MallOrderLogistics;
import com.ibaixiong.erp.dao.storage.ErpLogisticsInformationDao;
import com.ibaixiong.erp.dao.storage.ErpLogisticsLogDao;
import com.ibaixiong.erp.dao.storage.ErpOutStorageDao;
import com.ibaixiong.erp.dao.storage.MallOrderLogisticsDao;
import com.ibaixiong.erp.service.storage.ErpLogisticsInformationService;
import com.ibaixiong.erp.util.InvalidEnum;
import com.papabear.order.api.OrderService;
@Service
public class ErpLogisticsInformationServiceImpl implements
		ErpLogisticsInformationService {
	@Resource
	private ErpLogisticsInformationDao erpLogisticsInformationDao;
	@Resource
	private ErpLogisticsLogDao erpLogisticsLogDao;
	@Resource
	private MallOrderLogisticsDao mallOrderLogisticsDao;

	@Resource 
	private ErpOutStorageDao erpOutStorageDao;
	@Resource
	private OrderService orderService;
	@Override
	public void insert(ErpLogisticsInformation information) {
		erpLogisticsInformationDao.insertSelective(information);
	}

	@Override
	public void update(ErpLogisticsInformation information) {
		erpLogisticsInformationDao.updateByPrimaryKeySelective(information);
	}
	 @Override
	public ErpLogisticsInformation get(Long id) {
		return erpLogisticsInformationDao.selectByPrimaryKey(id);
	}
	@Transactional
	@Override
	public Map<String, Object> updateState(String params) {
		/*obj.put("logisticCompanyID", "");
		obj.put("logisticID", "");
		obj.put("result", "true");
		obj.put("resultCode",ResultCode.DIGEST_ERROR.getNumber());
		obj.put("reason", ResultCode.DIGEST_ERROR.getMessage());
		*/
		
		Map<String, Object> obj = new HashMap<String, Object>();
		if(StringUtils.isBlank(params)){
			obj.put("logisticCompanyID", "");
			obj.put("logisticID", "");
			obj.put("result", "true");
			obj.put("resultCode",ResultCode.PARAMS_CONVERT_ERROR.getNumber());
			obj.put("reason", ResultCode.PARAMS_CONVERT_ERROR.getMessage());
		}else{
			UpdateStatusRequest updateState=FastJsonUtil.parseObject(params, UpdateStatusRequest.class);
			String logisticID=updateState.getLogisticID();
			if(StringUtils.isNotBlank(logisticID)){
				//计算拆分logisticId
				logisticID = logisticID.substring(MallOrderServiceImpl.sign.length(), logisticID.length());
				ErpLogisticsInformation logistics=erpLogisticsInformationDao.getErpLogisticsInformationBylogisticID(logisticID);
				if(logistics != null && logistics.getId()!=null){
					logistics.setStatus(StatusDatabaseMappingEnum.getCode(updateState.getStatusType()));
					if(updateState.getStatusType().equals("GOT")){
						//如果为已开单
						logistics.setMailNo(updateState.getMailNo());//录入运单号
					}
					erpLogisticsInformationDao.updateByPrimaryKeySelective(logistics);
					//插入日志表信息
					insertLogisticsLog(logistics, params);
					if(updateState.getStatusType().equals("GOT")){
						MallOrderLogistics mallOrderLogistics = mallOrderLogisticsDao.getByLogisticsId(logistics.getLogisticId());
						mallOrderLogistics.setExpressNo(updateState.getMailNo());
						mallOrderLogisticsDao.updateByPrimaryKeySelective(mallOrderLogistics);
						//修改出库记录表相关信息
						erpOutStorageDao.updateWaybillNumberByOrderId(logisticID, updateState.getMailNo());
						//修改收货人信息表
//						mallOrderRevicerInformationDao.updateExpressNoByOrderNumber(logisticID, updateState.getMailNo());
						orderService.updateMallOrderRevicerInformation(logisticID, updateState.getMailNo());
					}
					obj.put("logisticCompanyID", updateState.getLogisticCompanyID());
					obj.put("logisticID", updateState.getLogisticID());
					obj.put("result", "true");
					obj.put("resultCode",ResultCode.SUCCESS.getNumber());
					obj.put("reason", ResultCode.SUCCESS.getMessage());
				}else{
					obj.put("logisticCompanyID", updateState.getLogisticCompanyID());
					obj.put("logisticID", updateState.getLogisticID());
					obj.put("result", "true");
					obj.put("resultCode",ResultCode.ORDER_NOT_EXIST.getNumber());
					obj.put("reason", ResultCode.ORDER_NOT_EXIST.getMessage());
				}
			}else{
				obj.put("logisticCompanyID", updateState.getLogisticCompanyID());
				obj.put("logisticID", updateState.getLogisticID());
				obj.put("result", "true");
				obj.put("resultCode",ResultCode.ORDER_NOT_EXIST.getNumber());
				obj.put("reason", ResultCode.ORDER_NOT_EXIST.getMessage());
			}
		}
		return obj;
	}
	private void insertLogisticsLog(ErpLogisticsInformation eli,String resultInfo){
		ErpLogisticsLog log = new ErpLogisticsLog();
		log.setCreateDateTime(new Date());
		log.setStatus(eli.getStatus());
		log.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		log.setAdminId(null);//没有这个操作人
		log.setLogisticsId(eli.getId());
		log.setResult(resultInfo);
		log.setRequestType(Byte.parseByte("2"));//物流公司发起
		erpLogisticsLogDao.insert(log);
	}
	public static void main(String[] args) {
		//	String params="开单前：{\"logisticID\":\"AL00012239673409\",\"logisticCompanyID\":\"DEPPON\",\"comments\":\"用户要求延时揽收\",\"statusType\":\"NOGET\",\"gmtUpdated\":1361496717029}开单后：{\"logisticID\":\"AL00012239673409\",\"logisticCompanyID\":\"DEPPON\",\"comments\":\"\",\"statusType\":\"GOT\",\"gmtUpdated\":1361496717029,\"backSignBill\":\"0\",\"businessNetworkNo\":\"W011302020515\",\"cargoName\":\"干果\",\"codPrice\":100.0,\"codType\":\"1\",\"codValue\":10000.0,\"deliveryPrice\":\"\",\"deliveryType\":\"0\",\"insurancePrice\":100.0,\"insuranceValue\":3000,\"logisticID”:” ALI00100013”,\"mailNo”:”31350606”,\"payType”:”0”,\"receiver”:{\"address\":\"明珠路 1018 号\",\"city\":\"上海市\",\"county\":\"青浦区\",\"mobile\":\"15800000000\",\"name\":\"宝某某\",\"phone\":\"\",\"province\":\"上海\"},\"sender\":{ \"address\":\"北京中路 100 号\",\"city\":\"贵阳市\",\"county\":\"花溪区\",\"mobile\":\"15800000001\",\"name\":\"淘某某\",\"phone\":\"\",\"province\":\"贵州省\"},\"smsNotify\":\"Y\",\"smsNotifyPrice\":1.0,\"toNetworkNo\":\"W0013541\",\"totalNumber\":12,\"totalPrice\":100.0,\"totalVolume\":63.0,\"totalWeight\":52.0,\"vistReceive\":\"Y\",\"vistReceivePrice\":10.0}";
		//
		//    String befor=params.substring(4, params.indexOf("开单后"));
		//	String after=params.substring(params.indexOf("开单后")+4, params.length());
		//	System.out.println(befor);
		//	UpdateStatusRequest orderInfo=FastJsonUtil.parseObject(befor, UpdateStatusRequest.class);
		//	System.out.println(orderInfo.getStatusType());
		String st = MallOrderServiceImpl.sign+"saR111112112112";
		
		System.out.println(st.substring(MallOrderServiceImpl.sign.length(), st.length()));
	
		
	}

@Override
public String logisticsTrace(String logisticsId) {
	
	ErpLogisticsInformation logistics=erpLogisticsInformationDao.getErpLogisticsInformationBylogisticID(logisticsId);
	
	Map<String, Object> map=new HashMap<String, Object>();
	//Map<String, Object> ordersMap=new HashMap<String, Object>();
	map.put("logisticCompanyID", logistics.getLogisticCompanyId());
	List<Map<String, Object>> ms = new ArrayList<Map<String,Object>>();
	Map<String, Object> s = new HashMap<String, Object>();
	s.put("mailNo", logistics.getMailNo());
	ms.add(s);
	//ordersMap.put("mailNo",ms);
	map.put("orders", ms);
	String params1=FastJsonUtil.toJSONString(map);
	String timestamp=SecurityUtil.getTimestamp();
	String digest=SecurityUtil.getDigest(params1+MallOrderServiceImpl.appkey+timestamp);
	NameValuePair[] params=new NameValuePair[4];
	params[0]=new NameValuePair("params", params1);
	params[1]=new NameValuePair("digest", digest);
	params[2]=new NameValuePair("timestamp", timestamp);
	params[3]=new NameValuePair("companyCode", MallOrderServiceImpl.companyCode);
	try {
		return HttpUtils.sendRequest(MallOrderServiceImpl.trackDPorderUrl, params, "UTF-8", 5000);
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	return "";
}
	
}
