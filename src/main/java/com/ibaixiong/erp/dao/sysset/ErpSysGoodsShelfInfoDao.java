package com.ibaixiong.erp.dao.sysset;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpSysGoodsShelfInfo;

public interface ErpSysGoodsShelfInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpSysGoodsShelfInfo record);

    int insertSelective(ErpSysGoodsShelfInfo record);

    ErpSysGoodsShelfInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpSysGoodsShelfInfo record);

    int updateByPrimaryKey(ErpSysGoodsShelfInfo record);
    
    List<ErpSysGoodsShelfInfo> getListByParentId(@Param("pid")Long pid,@Param("storageId")Long storageId);
}