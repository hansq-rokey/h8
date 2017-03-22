/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.oem;

import java.util.List;

import com.ibaixiong.entity.ErpOemUserRelation;
import com.ibaixiong.entity.ErpSecurityCodeMacRelation;
import com.ibaixiong.entity.ErpUserPermissionRelation;
import com.ibaixiong.entity.SysAdmin;
import com.papabear.order.entity.MallOrderItem;
import com.papabear.product.entity.MallBasicCategoryModel;
import com.papabear.product.entity.MallBasicCategoryModelFormat;

/**
 * @description
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年9月29日-下午7:53:00
 */
public interface ErpOemScanPackageService {
	List<ErpUserPermissionRelation> getListErpUserPermissionRelation(Long adminId);

	List<MallBasicCategoryModel> getMallBasicCategoryModelByAdminId(Long adminId);

	List<MallBasicCategoryModelFormat> getMallBasicCategoryModelFormatByAdminId(Long adminId, Long categoryModelId);

	ErpUserPermissionRelation getErpUserPermissionRelationBySelectId(Long selectId);

	String scanPackage(String macNumber, ErpUserPermissionRelation selectErpUserPermissionRelation, ErpOemUserRelation erpOemUserRelation) throws Exception;

	List<ErpUserPermissionRelation> getListErpUserPermissionRelationByAdminIdAndFormatId(Long adminId, Long formatId);

	String scanPackageCustomMade(String macNumber, MallOrderItem orderItem, ErpOemUserRelation oem, String ip) throws Exception;

	List<MallBasicCategoryModel> getMallBasicCategoryModel();
	
	List<MallBasicCategoryModelFormat> getMallBasicCategoryModelFormat(Long categoryModelId);
	
	void deleteCustomMade(MallOrderItem orderItem);

	List<ErpUserPermissionRelation> getListErpUserPermissionRelationByFormatId(Long formatId);

	String scanPackageCode(String scanMacNumber,
			ErpSecurityCodeMacRelation relation,SysAdmin admin) throws Exception;

	String scanPackageCustom(String macNumber, MallOrderItem orderItem,
			ErpSecurityCodeMacRelation relation, SysAdmin admin, String ip)
			throws Exception;

	void deleteCustomMadeRelation(MallOrderItem orderItem);
}
