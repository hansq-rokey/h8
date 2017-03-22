package com.ibaixiong.erp.service.storage.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.entity.ErpPutStorageCount;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.dao.storage.ErpPutStorageCountDao;
import com.ibaixiong.erp.service.storage.ErpPutStorageCountService;
import com.ibaixiong.erp.util.InvalidEnum;

@Service
public class ErpPutStorageCountServiceImpl implements ErpPutStorageCountService{
	
	@Resource
	private ErpPutStorageCountDao putStrorageCountDao;

	@SuppressWarnings("static-access")
	@Override
	public List<ErpPutStorageCount> queryPutStorageList(SysAdmin admin,String keywords,
			Date startTime,Date endTime,int pageNo,int offset) {
		
		PageHelper pageHelper=new PageHelper();
		pageHelper.startPage(pageNo, offset);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("adminId", admin==null?null:admin.getId());
		map.put("invalid", InvalidEnum.FALSE.getInvalidValue());
		map.put("keywords", keywords);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return putStrorageCountDao.queryPutStorageList(map);
	}

	@Override
	public ErpPutStorageCount getErpPutStorageCount(Long id) {
		return putStrorageCountDao.selectByPrimaryKey(id);
	}

	@Override
	public List<ErpPutStorageCount> queryPutStorageList(SysAdmin admin,
			int pageNo, int offset) {
		return this.queryPutStorageList(admin, null, null, null, pageNo, offset);
	}
	@Override
	public void insert(ErpPutStorageCount esc) {
		putStrorageCountDao.insertSelective(esc);
	}
}
