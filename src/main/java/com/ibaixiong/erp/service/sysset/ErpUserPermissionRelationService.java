package com.ibaixiong.erp.service.sysset;

import java.util.List;

import com.ibaixiong.entity.ErpUserPermissionRelation;

public interface ErpUserPermissionRelationService {
	List<ErpUserPermissionRelation> selectByAdminId(Long adminId,Integer pageNo);
	void insert(ErpUserPermissionRelation relation);
	void updateInvalid(Long id, Byte invalid);
	ErpUserPermissionRelation getCheck(Long batchId,Long adminId);
	/**
	 * 根据采购订单ID查询权限表列表
	 * @author zhaolei
	 * @date 2015年10月27日
	 * @param orderId
	 * @return
	 */
	List<ErpUserPermissionRelation> selectByPurchaseOrderId(Long orderId);
}
