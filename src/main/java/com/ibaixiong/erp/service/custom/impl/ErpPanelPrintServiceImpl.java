package com.ibaixiong.erp.service.custom.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.entity.ErpPanelPrint;
import com.ibaixiong.erp.dao.custom.ErpPanelPrintDao;
import com.ibaixiong.erp.service.custom.ErpPanelPrintService;
import com.papabear.order.api.OrderService;
import com.papabear.order.entity.OrderConstant.OrderOperateTye;
import com.papabear.order.entity.OrderConstant.OrderStatus;

@Service
public class ErpPanelPrintServiceImpl implements ErpPanelPrintService {

	@Resource
	private ErpPanelPrintDao panelPrintDao;
//	@Resource
//	private MallOrderDao mallOrderDao;
//	@Resource
//	private MallOrderHistoryDao orderHistoryDao;
	@Resource
	private OrderService orderService;
	
	@Override
	public List<ErpPanelPrint> queryPanelPrintByStatus(Byte status,int pageSize,int pageNo) {
		PageHelper.startPage(pageNo, pageSize, "id desc");
		return panelPrintDao.queryPanelPrintByStatus(status);
	}

	@Override
	public int update(String ids, String logisticsCompany, String billNo,Long adminId) {
		if(StringUtils.isBlank(ids)){
			return 0;
		}
		List<String> list=Arrays.asList(ids.split(","));
		if(list==null||list.size()==0){
			return 0;
		}
		for(String id:list){
			ErpPanelPrint print=panelPrintDao.selectByPrimaryKey(Long.parseLong(id));
			if(print!=null){
//				mallOrderDao.updateStatus(print.getOrderNumber(), OrderStatusEnum.CUSTOM_PRINT.getCode());
				orderService.updateOrderStatus(OrderStatus.CUSTOM_PRINT.getStatus(), print.getOrderNumber());
//				MallOrderHistory history = new MallOrderHistory();
//				history.setOrderNumber(print.getOrderNumber());
//				history.setUpdateTime(new Date());
//				history.setOperatorType((byte) 1);
//				history.setUserId(adminId);
//				history.setOperatorIp(null);
//				history.setCreateDateTime(new Date());
//				history.setStatus(OrderStatusEnum.CUSTOM_PRINT.getCode());
//				orderHistoryDao.insert(history);
				orderService.addOrderHistory(print.getOrderNumber(), adminId, null, null, OrderStatus.CUSTOM_PRINT.getStatus(), OrderOperateTye.ADMINISTRATOR.getOperateType());
			}
		}
		
		return panelPrintDao.update(list, logisticsCompany, billNo);
	}

}
