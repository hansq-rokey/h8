/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.notice;

import java.util.List;

import com.ibaixiong.entity.ErpNoticeInfo;

/**
 * @description
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年10月25日-下午3:33:48
 */
public interface NoticeService {
	List<ErpNoticeInfo> getAllNotice(Integer pageNo);

	ErpNoticeInfo getById(Long id);
	
	void insert(ErpNoticeInfo info);
}
