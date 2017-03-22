package com.ibaixiong.erp.dao.notice;

import com.ibaixiong.entity.ErpNoticeCategory;

public interface ErpNoticeCategoryDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpNoticeCategory record);

    int insertSelective(ErpNoticeCategory record);

    ErpNoticeCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpNoticeCategory record);

    int updateByPrimaryKey(ErpNoticeCategory record);
}