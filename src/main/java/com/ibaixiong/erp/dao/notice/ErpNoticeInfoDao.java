package com.ibaixiong.erp.dao.notice;

import java.util.List;

import com.ibaixiong.entity.ErpNoticeInfo;

public interface ErpNoticeInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpNoticeInfo record);

    int insertSelective(ErpNoticeInfo record);

    ErpNoticeInfo selectByPrimaryKey(Long id);
    
    List<ErpNoticeInfo> selectAll();

    int updateByPrimaryKeySelective(ErpNoticeInfo record);

    int updateByPrimaryKeyWithBLOBs(ErpNoticeInfo record);

    int updateByPrimaryKey(ErpNoticeInfo record);
}