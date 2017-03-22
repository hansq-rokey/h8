/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.User;
import com.ibaixiong.erp.dao.sys.UserDao;
import com.ibaixiong.erp.service.sys.UserService;

/**
 * 
 * @author yaoweiguo
 * @email  280435353@qq.com
 * @date   2016年3月17日
 * @since  1.0.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;

	@Override
	public User getuUser(Long id) {
		
		return userDao.selectByPrimaryKey(id);
	}

}
