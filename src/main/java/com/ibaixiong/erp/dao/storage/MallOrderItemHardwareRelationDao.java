package com.ibaixiong.erp.dao.storage;

import com.ibaixiong.entity.MallOrderItemHardwareRelation;

public interface MallOrderItemHardwareRelationDao {
	int deleteByPrimaryKey(Long id);

	int insert(MallOrderItemHardwareRelation record);

	int insertSelective(MallOrderItemHardwareRelation record);

	MallOrderItemHardwareRelation selectByPrimaryKey(Long id);

	MallOrderItemHardwareRelation selectByHardwareProductId(Long hardwareProductId);

	MallOrderItemHardwareRelation selectByOrderItemId(Long orderItemId);

	MallOrderItemHardwareRelation selectByUniqueCode(String uniqueCode);

	int updateByPrimaryKeySelective(MallOrderItemHardwareRelation record);

	int updateByPrimaryKey(MallOrderItemHardwareRelation record);
}