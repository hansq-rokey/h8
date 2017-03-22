/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.web.notice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.ibaixiong.entity.ErpNoticeInfo;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.service.notice.NoticeService;
import com.ibaixiong.erp.web.util.WebUtil;

/**
 * @description 通知
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年9月29日-下午4:22:45
 */
@Controller
@RequestMapping("/notice")
public class NoticeAction {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	NoticeService noticeService;

	@RequestMapping("/list.html")
	public String list(
			@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			Model model, HttpServletRequest request) {
		SysAdmin admin = WebUtil.getLoginUser(request);
		List<ErpNoticeInfo> listErpNoticeInfo = noticeService.getAllNotice(pageNo);
		PageInfo<ErpNoticeInfo> pageInfo=new PageInfo<ErpNoticeInfo>(listErpNoticeInfo);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("listErpNoticeInfo", listErpNoticeInfo);
		return "/notice/list";
	}

	@RequestMapping("/detail.html")
	public String detail(Model model, HttpServletRequest request) {
		if (StringUtils.isNotBlank(request.getParameter("id"))) {
			Long id = Long.valueOf(request.getParameter("id"));
			ErpNoticeInfo erpNoticeInfo = noticeService.getById(id);
			model.addAttribute("erpNoticeInfo", erpNoticeInfo);
		}
		return "/notice/detail";
	}

}
