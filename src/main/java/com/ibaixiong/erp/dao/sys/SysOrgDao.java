/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.dao.sys;

import java.util.List;

import com.ibaixiong.entity.SysOrg;

public interface SysOrgDao {
    Long deleteByPrimaryKey(Long id);

    Long insertSelective(SysOrg record);

    SysOrg selectByPrimaryKey(Long id);

    Long updateByPrimaryKeySelective(SysOrg record);

    /**
     * 获取部门列表
     * @author zhaolei
     * @date 2015年7月9日
     * @return
     */
    List<SysOrg> getAllOrgList();
}