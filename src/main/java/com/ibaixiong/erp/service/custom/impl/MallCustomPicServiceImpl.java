package com.ibaixiong.erp.service.custom.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.InvalidEnum;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.entity.MallCustomPic;
import com.ibaixiong.erp.dao.custom.MallCustomPicDao;
import com.ibaixiong.erp.service.custom.MallCustomPicService;

@Service
public class MallCustomPicServiceImpl implements MallCustomPicService {

	@Resource
	private MallCustomPicDao customPicDao;

	@Override
	public List<MallCustomPic> queryMallCustomPic(Map<String, Object> map) {
		map.put("invalid", InvalidEnum.FALSE.getInvalidValue());
		PageHelper.startPage((Integer) map.get("pageNo"), PageConstant.pageSize, true);
		return customPicDao.queryCustomPicList(map);
	}

	@Override
	public MallCustomPic getmCustomPic(Long id) {
		return customPicDao.selectByPrimaryKey(id);
	}

	@Override
	public List<MallCustomPic> queryMallCustomPicByOrderNumber(String orderNumber) {
		return customPicDao.queryMallCustomPicByOrderNumber(orderNumber);
	}

	@Override
	public MallCustomPic getMallCustomPicByOrderNumberAndType(String orderNumber, Byte type) {
		
		return customPicDao.getMallCustomPicByOrderNumberAndType(orderNumber, type);
	}

}
