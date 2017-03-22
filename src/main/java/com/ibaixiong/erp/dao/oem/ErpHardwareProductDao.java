package com.ibaixiong.erp.dao.oem;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpHardwareProduct;

public interface ErpHardwareProductDao {
	int deleteByPrimaryKey(Long id);

	int insert(ErpHardwareProduct record);

	int insertSelective(ErpHardwareProduct record);

	ErpHardwareProduct selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ErpHardwareProduct record);

	int updateByPrimaryKey(ErpHardwareProduct record);

	/**
	 * 运输码查询硬件信息
	 * 
	 * @param transportCodeId
	 * @param invaild
	 * @return
	 */
	List<ErpHardwareProduct> queryErpHardwareProductByTransportId(@Param("transportCodeId") Long transportCodeId, @Param("invalid") boolean invalid);

	List<ErpHardwareProduct> getErpHardwareProductByAdminId(@Param("adminId") Long adminId, @Param("startDate") String startDate,
			@Param("endDate") String endDate);

	ErpHardwareProduct getErpHardwareProductByMacNumber(String macNumber);

	ErpHardwareProduct getErpHardwareProductByUniqueCode(String uniqueCode);
	List<ErpHardwareProduct> queryListByFormatId(Map<String, Object> map);
}