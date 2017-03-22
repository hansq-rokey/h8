package com.ibaixiong.erp.dao.oem;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpSecurityCodeMacRelation;

public interface ErpSecurityCodeMacRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpSecurityCodeMacRelation record);

    int insertSelective(ErpSecurityCodeMacRelation record);

    ErpSecurityCodeMacRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpSecurityCodeMacRelation record);

    int updateByPrimaryKey(ErpSecurityCodeMacRelation record);
    
    int insertBath(List<ErpSecurityCodeMacRelation> list);
    
    List<String> queryAll();
    /**
     * 根据防伪码查询
     * @param fwm
     * @return
     */
    ErpSecurityCodeMacRelation getSecurityCodeMacRelation(@Param("fwm")String fwm);
}