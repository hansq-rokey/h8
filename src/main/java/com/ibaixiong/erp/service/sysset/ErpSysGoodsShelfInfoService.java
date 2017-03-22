package com.ibaixiong.erp.service.sysset;

import java.util.List;

import com.ibaixiong.entity.ErpSysGoodsShelfInfo;

public interface ErpSysGoodsShelfInfoService {
	List<ErpSysGoodsShelfInfo> getListByParentId(Long pid,Long storageId);
	List<ErpSysGoodsShelfInfo> getListPageByParentId(Long pid,Long storageId,Integer pageNo);
	void insert(ErpSysGoodsShelfInfo shelfInfo);
	void update(ErpSysGoodsShelfInfo shelfInfo);
	ErpSysGoodsShelfInfo get(Long id);
}
