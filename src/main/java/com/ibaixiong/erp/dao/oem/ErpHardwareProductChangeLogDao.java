package com.ibaixiong.erp.dao.oem;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpHardwareProductChangeLog;

public interface ErpHardwareProductChangeLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpHardwareProductChangeLog record);

    int insertSelective(ErpHardwareProductChangeLog record);

    ErpHardwareProductChangeLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpHardwareProductChangeLog record);

    int updateByPrimaryKey(ErpHardwareProductChangeLog record);
    /**
     * 查询某个硬件产品日志，
     * @param hardwareId
     * @param invalid
     * @return
     */
    List<ErpHardwareProductChangeLog> queryLogsByHardwareId(@Param("hardwareId")Long hardwareId,@Param("invalid") boolean invalid);
}