package com.ibaixiong.erp.dao.storage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpExceptionProduct;

public interface ErpExceptionProductDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpExceptionProduct record);

    int insertSelective(ErpExceptionProduct record);

    ErpExceptionProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpExceptionProduct record);

    int updateByPrimaryKey(ErpExceptionProduct record);
    
    
    List<ErpExceptionProduct> queryExcetionList(Map<String, Object> map);
}