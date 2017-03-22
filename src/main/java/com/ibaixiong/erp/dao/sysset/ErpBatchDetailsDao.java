package com.ibaixiong.erp.dao.sysset;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpBatchDetails;

public interface ErpBatchDetailsDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpBatchDetails record);

    int insertSelective(ErpBatchDetails record);

    ErpBatchDetails selectByPrimaryKey(Long id);
    
    ErpBatchDetails selectBaseInfoByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpBatchDetails record);

    int updateByPrimaryKey(ErpBatchDetails record);
    
    void deleteByOrderId(Long id);

    List<ErpBatchDetails> getListByBatchId(@Param("batchId")Long batchId,@Param("invalid")Byte invalid);
    
    void updateInvalid(@Param("id")Long id,@Param("invalid")Byte invalid);
    
    ErpBatchDetails getByOrderId(Long id);
    
}