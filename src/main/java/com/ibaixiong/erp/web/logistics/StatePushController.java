/*
 * baixiong.com Inc.
 * Copyright (c) 1999-2001 All Rights Reserved.
 * 
 */
package com.ibaixiong.erp.web.logistics;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.deppon.dop.module.sdk.shared.domain.common.ResultCode;
import com.deppon.dop.module.sdk.shared.util.SecurityUtil;
import com.ibaixiong.entity.ErpLogisticsPush;
import com.ibaixiong.erp.service.storage.ErpLogisticsInformationService;
import com.ibaixiong.erp.service.storage.ErpLogisticsPushService;
import com.ibaixiong.erp.service.storage.impl.MallOrderServiceImpl;

/**
 * 物流状态推送
 * @author yaoweiguo
 * @Email  yaoweiguo@ibaixiong.com
 * @Description TODO
 * @date 2015年10月18日
 *
 */
@Controller
@RequestMapping("/services")
public class StatePushController {
	
	@Resource
	private ErpLogisticsInformationService logisticsInformationService;
	@Resource
	ErpLogisticsPushService erpLogisticsPushService;
	/*@RequestMapping("/state.do")
	public void statePush(HttpServletResponse response){
//	public void statePush(@RequestParam("params")String params,@RequestParam("digest")String digest,
//			@RequestParam("timestamp")Long timestamp,@RequestParam("companyCode")String companyCode,HttpServletResponse response){
		String params="开单前：{\"logisticID\":\"AL00012239673409\",\"logisticCompanyID\":\"DEPPON\",\"comments\":\"用户要求延时揽收\",\"statusType\":\"NOGET\",\"gmtUpdated\":1361496717029}开单后：{\"logisticID\":\"AL00012239673409\",\"logisticCompanyID\":\"DEPPON\",\"comments\":\"\",\"statusType\":\"GOT\",\"gmtUpdated\":1361496717029,\"backSignBill\":\"0\",\"businessNetworkNo\":\"W011302020515\",\"cargoName\":\"干果\",\"codPrice\":100.0,\"codType\":\"1\",\"codValue\":10000.0,\"deliveryPrice\":\"\",\"deliveryType\":\"0\",\"insurancePrice\":100.0,\"insuranceValue\":3000,\"logisticID”:”ALI00100013”,\"mailNo”:”31350606”,\"payType”:”0”,\"receiver”:{\"address\":\"明珠路1018号\",\"city\":\"上海市\",\"county\":\"青浦区\",\"mobile\":\"15800000000\",\"name\":\"宝某某\",\"phone\":\"\",\"province\":\"上海\"},\"sender\":{\"address\":\"北京中路100号\",\"city\":\"贵阳市\",\"county\":\"花溪区\",\"mobile\":\"15800000001\",\"name\":\"淘某某\",\"phone\":\"\",\"province\":\"贵州省\"},\"smsNotify\":\"Y\",\"smsNotifyPrice\":1.0,\"toNetworkNo\":\"W0013541\",\"totalNumber\":12,\"totalPrice\":100.0,\"totalVolume\":63.0,\"totalWeight\":52.0,\"vistReceive\":\"Y\",\"vistReceivePrice\":10.0}";
		String securityDigest=SecurityUtil.getDigest(params);
//		if(!securityDigest.equals(digest)){
//			//todo   返回错误信息
//		}
		
		ResultCode result=logisticsInformationService.updateState(params);
		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("logisticCompanyID", DOPConstans.LOGISTIC_PROVIDERID_DEFAULT);
		map.put("logisticID", "123456");
		map.put("result", true);		
		map.put("resultCode", result.getNumber());
		map.put("reason", result.getMessage());
		PrintWriter write=null;
		try {
			response.setContentType("text/html; charset=utf-8");
			write=response.getWriter();
			write.print(JSON.toJSONString(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	//http://183.129.149.82:8068/services/dbPush.html
	@RequestMapping("/dbPush.html")
	public void dbPush(
			@RequestParam(value="digest",required=false) String digest,
			@RequestParam(value="timestamp",required=false) String timestamp,
			@RequestParam(value="companyCode",required=false) String companyCode,
			@RequestParam(value="params",required=false) String params,
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response){
		//System.out.println(params);
		String s = "digest:"+digest+";timestamp:"+timestamp+";companyCode:"+companyCode+";params:"+params;
		ErpLogisticsPush push = new ErpLogisticsPush();
		push.setContent(s);
		push.setType("dbPush");
		erpLogisticsPushService.insert(push);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String digestService=SecurityUtil.getDigest(params+MallOrderServiceImpl.appkey+timestamp);
		if(digestService.equals(digest)){//验证成功放过
			//params = "{\"backSignBill\":\"0\",\"backSignBillPrice\":10,\"businessNetworkNo\":\"W011302030412\",\"cargoName\":\"你猜是什么\",\"codPrice\":10,\"codType\":\"1\",\"codValue\":200,\"comments\":\"this is a test request\",\"deliveryPrice\":30,\"deliveryType\":\"0\",\"fuelSurcharge\":\"Y\",\"fuelSurchargePrice\":3,\"gmtUpdated\":1445406693673,\"insurancePrice\":20.199999999999999289457264239899814128875732421875,\"insuranceValue\":3000,\"logisticCompanyID\":\"DEPPON\",\"logisticID\":\"O1007321445393965134\",\"mailNo\":\"201409129\",\"otherPrice\":1,\"packageService\":\"木架\",\"packageServicePrice\":30,\"payType\":\"0\",\"receiver\":{\"address\":\"南京路123号\",\"city\":\"上海市\",\"county\":\"黄浦区\",\"mobile\":\"96542365412\",\"name\":\"i'm receiver\",\"phone\":\"\",\"province\":\"上海\"},\"sender\":{\"address\":\"朝阳公园路123号\",\"city\":\"北京市\",\"county\":\"朝阳区\",\"mobile\":\"12563254521\",\"name\":\"i'm sender\",\"phone\":\"\",\"province\":\"北京\"},\"smsNotify\":\"Y\",\"smsNotifyPrice\":2,\"statusType\":\"GOT\",\"toNetworkNo\":\"W01061502\",\"totalNumber\":500,\"totalPrice\":100.099999999999994315658113919198513031005859375,\"totalVolume\":400,\"totalWeight\":300,\"transportType\":\"QC_JZKH\",\"vistReceive\":\"Y\",\"vistReceivePrice\":40,\"waitNotifySend\":\"Y\",\"waitNotifySendPrice\":1}";
			//params = "{\"comments\":\"this is a test request\",\"gmtUpdated\":1445407221773,\"logisticCompanyID\":\"DEPPON\",\"logisticID\":\"O1007321445393965134\",\"statusType\":\"ACCEPT\"}";
			resultMap = logisticsInformationService.updateState(params);
		}else{
			//秘钥对不上返回错误码处理返回德邦告知
			resultMap.put("logisticCompanyID", "");
			resultMap.put("logisticID", "");
			resultMap.put("result", "true");
			resultMap.put("resultCode",ResultCode.DIGEST_ERROR.getNumber());
			resultMap.put("reason", ResultCode.DIGEST_ERROR.getMessage());
		}
		PrintWriter write=null;
		String result = JSON.toJSONString(resultMap);
		try {
			response.setContentType("text/html; charset=utf-8");
			write=response.getWriter();
			write.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
