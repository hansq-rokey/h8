package com.ibaixiong.erp.dao.sysset;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpUserPermissionRelation;

public interface ErpUserPermissionRelationDao {
	int deleteByPrimaryKey(Long id);

	int insert(ErpUserPermissionRelation record);

	int insertSelective(ErpUserPermissionRelation record);

	ErpUserPermissionRelation selectByPrimaryKey(Long id);

	List<ErpUserPermissionRelation> selectByAdminId(@Param("adminId") Long adminId, @Param("invalid") Boolean invalid);

	List<ErpUserPermissionRelation> selectByAdminIdAndFormatId(@Param("adminId") Long adminId, @Param("formatId") Long formatId, @Param("invalid") Boolean invalid);

	List<Long> selectCategoryModelIdByAdminId(@Param("adminId") Long adminId, @Param("invalid") Boolean invalid);

	List<Long> selectCategoryModelId(@Param("invalid") Boolean invalid);
	
	List<Long> selectCategoryModelFormatIdByAdminId(@Param("adminId") Long adminId, @Param("categoryModelId") Long categoryModelId,
			@Param("invalid") Boolean invalid);
	
	List<Long> selectCategoryModelFormatId(@Param("categoryModelId") Long categoryModelId,@Param("invalid") Boolean invalid);

	int updateByPrimaryKeySelective(ErpUserPermissionRelation record);

	int updateByPrimaryKey(ErpUserPermissionRelation record);

	void updateInvalid(@Param("id") Long id, @Param("invalid") Byte invalid);

	ErpUserPermissionRelation getCheck(@Param("batchId") Long batchId, @Param("adminId") Long adminId);

	List<ErpUserPermissionRelation> selectByPurchaseOrderId(Long orderId);

	List<ErpUserPermissionRelation> selectByFormatId(@Param("formatId") Long formatId, @Param("invalid") Boolean invalid);
}