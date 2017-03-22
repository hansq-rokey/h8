package com.ibaixiong.erp.service.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibaixiong.entity.DictCode;
import com.ibaixiong.erp.dao.sys.DictCodeDao;
import com.ibaixiong.erp.service.sys.DictCodeService;
import com.ibaixiong.erp.util.DictTypeEnum;

@Service
public class DictCodeServiceImpl implements DictCodeService {

	@Resource
	private DictCodeDao codeDao;
	
	@Override
	public List<DictCode> queryDictCodeList(DictTypeEnum dictType, Byte type) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("dictType", dictType.getDictType());
		map.put("type", type);
		map.put("isDisplay", true);
		return codeDao.queryDictCodeList(map);
	}

	@Override
	public List<DictCode> queryDictCodeList(Byte type) {

		return queryDictCodeList(null, type);
	}

	@Override
	public List<DictCode> queryDictCodeList() {

		return queryDictCodeList(null, null);
	}

	@Override
	public List<DictCode> queryDictCodeList(DictTypeEnum dictType) {

		return queryDictCodeList(dictType, null);
	}
	
}
