package com.ibaixiong.erp.dao.storage;

import java.util.List;

import com.ibaixiong.entity.MallPay;

public interface MallPayDao {
    int deleteByPrimaryKey(Long id);

    int insert(MallPay record);

    int insertSelective(MallPay record);

    MallPay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MallPay record);

    int updateByPrimaryKey(MallPay record);
    
    List<MallPay> getListByOrderNumber(String orderNumber);
}