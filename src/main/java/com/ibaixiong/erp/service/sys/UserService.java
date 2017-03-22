/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.service.sys;

import com.ibaixiong.entity.User;

/**
 * 
 * @author yaoweiguo
 * @email  280435353@qq.com
 * @date   2016年3月17日
 * @since  1.0.0
 */
public interface UserService {

	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	User getuUser(Long id);
}
