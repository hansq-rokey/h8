/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.notice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.ErpNoticeInfo;
import com.ibaixiong.erp.dao.notice.ErpNoticeInfoDao;
import com.ibaixiong.erp.service.notice.NoticeService;

/**
 * @description
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年10月25日-下午3:35:32
 */

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	ErpNoticeInfoDao noticeDao;

	@Override
	public List<ErpNoticeInfo> getAllNotice(Integer pageNo) {
		PageHelper page= new PageHelper();
		page.startPage(pageNo, PageConstant.pageSize, true);
		return noticeDao.selectAll();
	}

	@Override
	public ErpNoticeInfo getById(Long id) {
		return noticeDao.selectByPrimaryKey(id);
	}
	@Override
	public void insert(ErpNoticeInfo info) {
		noticeDao.insertSelective(info);
	}
}
