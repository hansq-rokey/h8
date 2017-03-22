package com.ibaixiong.erp.dao.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpPanelPrint;

public interface ErpPanelPrintDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpPanelPrint record);

    int insertSelective(ErpPanelPrint record);

    ErpPanelPrint selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpPanelPrint record);

    int updateByPrimaryKey(ErpPanelPrint record);
    
    List<ErpPanelPrint> queryPanelPrintByStatus(@Param("status")Byte status);
    
    int update(@Param("list")List<String> list,@Param("logisticsCompany")String logisticsCompany,@Param("billNo")String billNo);
}