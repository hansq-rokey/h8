package com.ibaixiong.erp.dao.sysset;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpBatch;


public interface ErpBatchDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpBatch record);

    int insertSelective(ErpBatch record);

    ErpBatch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpBatch record);

    int updateByPrimaryKey(ErpBatch record);
    
    ErpBatch queryByBatch(@Param("categoryId")Long categoryId,@Param("categoryModelId")Long categoryModelId,@Param("categoryModelFormatId")Long categoryModelFormatId,@Param("invalid")int invalid);
    List<ErpBatch> getList();
}