package com.ibaixiong.erp.dao.sysset;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpBaseBatch;


public interface ErpBaseBatchDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpBaseBatch record);

    int insertSelective(ErpBaseBatch record);

    ErpBaseBatch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpBaseBatch record);

    int updateByPrimaryKey(ErpBaseBatch record);
    
    ErpBaseBatch selectByBatch(@Param("categoryId")Long categoryId,@Param("categoryModelId")Long categoryModelId,@Param("categoryModelFormatId")Long categoryModelFormatId);
    
    void updateIndexNum(@Param("categoryId")Long categoryId,@Param("categoryModelId")Long categoryModelId,@Param("categoryModelFormatId")Long categoryModelFormatId);
}