package com.ibaixiong.erp.service.sys;

import java.util.List;

import com.ibaixiong.entity.DictCode;
import com.ibaixiong.erp.util.DictTypeEnum;

public interface DictCodeService {
	/**
	 * 查询字典表数据
	 * @param dictType
	 * @param type 0：普通   1：私人订制    null：普通+私人定制
	 * @return
	 */
	public List<DictCode> queryDictCodeList(DictTypeEnum dictType,Byte type);
	
	public List<DictCode> queryDictCodeList(Byte type);
	/**
	 * 查询字典表数据
	 * @param dictType
	 * @return
	 */
	public List<DictCode> queryDictCodeList(DictTypeEnum dictType);
	
	public List<DictCode> queryDictCodeList();
}
