package com.ibaixiong.erp.dao.oem;

import java.util.List;

import com.ibaixiong.entity.ErpHardwareTransportRelation;

public interface ErpHardwareTransportRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpHardwareTransportRelation record);

    int insertSelective(ErpHardwareTransportRelation record);

    ErpHardwareTransportRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpHardwareTransportRelation record);

    int updateByPrimaryKey(ErpHardwareTransportRelation record);
    
    List<ErpHardwareTransportRelation> queryListByCodeId(Long codeId);
}