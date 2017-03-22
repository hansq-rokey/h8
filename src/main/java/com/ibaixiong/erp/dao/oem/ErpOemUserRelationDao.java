package com.ibaixiong.erp.dao.oem;

import java.util.List;

import com.ibaixiong.entity.ErpOemUserRelation;

public interface ErpOemUserRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpOemUserRelation record);

    int insertSelective(ErpOemUserRelation record);

    ErpOemUserRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpOemUserRelation record);

    int updateByPrimaryKey(ErpOemUserRelation record);
    
    List<ErpOemUserRelation> getListByOemId(Long oemId);
    
    ErpOemUserRelation getErpOemUserRelationByAdminId(Long adminId);
    
    List<ErpOemUserRelation> getAlikeListByAdminId(Long adminId);
}