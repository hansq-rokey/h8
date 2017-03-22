package com.ibaixiong.erp.dao.storage;

import java.util.List;

import com.ibaixiong.entity.ErpStorageUserRelation;

public interface ErpStorageUserRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpStorageUserRelation record);

    int insertSelective(ErpStorageUserRelation record);

    ErpStorageUserRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpStorageUserRelation record);

    int updateByPrimaryKey(ErpStorageUserRelation record);
    List<ErpStorageUserRelation> getListByStorageId(Long storageId);
}