package com.ibaixiong.erp.service.oem.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.ibaixiong.constant.Constant.Status;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.core.utils.CodeUtil;
import com.ibaixiong.entity.ErpHardwareProduct;
import com.ibaixiong.entity.ErpHardwareProductChangeLog;
import com.ibaixiong.entity.ErpHardwareTransportRelation;
import com.ibaixiong.entity.ErpTransportCode;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.dao.oem.ErpHardwareProductChangeLogDao;
import com.ibaixiong.erp.dao.oem.ErpHardwareProductDao;
import com.ibaixiong.erp.dao.oem.ErpHardwareTransportRelationDao;
import com.ibaixiong.erp.dao.oem.ErpTransportCodeDao;
import com.ibaixiong.erp.service.oem.ErpTransportCodeService;
import com.ibaixiong.erp.util.HardwareProductNetStatusEnum;
import com.ibaixiong.erp.util.InvalidEnum;
import com.ibaixiong.erp.web.util.WebUtil;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.api.ProductQueryService;
import com.papabear.product.entity.MallBasicCategoryModel;
import com.papabear.product.entity.MallBasicCategoryModelFormat;
import com.papabear.product.entity.MallProductPic;

@Service
public class ErpTransportCodeServiceImpl implements ErpTransportCodeService{

	@Resource
	private ErpTransportCodeDao erpTransportCodeDao;
	@Resource
	private ProductQueryService productQueryService;
	@Resource
	private ErpHardwareProductDao erpHardwareProductDao;
	@Resource
	private ErpHardwareProductChangeLogDao erpHardwareProductChangeLogDao;
	@Resource
	private ErpHardwareTransportRelationDao erpHardwareTransportRelationDao;
	@Resource
	private CategoryQueryService categoryQueryService;
	@Override
	public ErpTransportCode getErpTransportCodeByCode(String code) {
		ErpTransportCode codeRes =  erpTransportCodeDao.getErpTransportCode(code, InvalidEnum.FALSE.getInvalidValue());
		if(codeRes != null){
			codeRes.setFormat(categoryQueryService.getCategoryModelFormat(codeRes.getCategoryModelFormatId()));
		}
		return codeRes;
	}

	@Override
	public Map<String, Object> getErpTransportCode(String code) {
//		Map<String, Object> result=erpTransportCodeDao.getErpTransportCodeBycode(code, InvalidEnum.FALSE.getInvalidValue());
		ErpTransportCode codeRes =erpTransportCodeDao.getErpTransportCode(code, InvalidEnum.FALSE.getInvalidValue());
		if(codeRes==null){
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", codeRes.getId());
		result.put("code", codeRes.getCode());
		MallBasicCategoryModelFormat format=categoryQueryService.getCategoryModelFormat(codeRes.getCategoryModelFormatId());
		MallBasicCategoryModel categoryModel=categoryQueryService.getCategoryModelById(format.getCategoryModelId());
		if(format==null||categoryModel==null){
			return null;
		}
		result.put("formatName", format.getName());
		result.put("category_model_format_number", format.getCategoryModelFormatNumber());
		result.put("formatId", format.getId());
		result.put("productName", categoryModel.getName());
		List<MallProductPic>pics=productQueryService.queryPics(null, format.getId(), (short)2);
		result.put("pics", pics);
		return result;
	}
	
	@Override
	public void insert(ErpTransportCode etc) {
		erpTransportCodeDao.insertSelective(etc);
	}
	
	@Override
	public void update(ErpTransportCode etc) {
		erpTransportCodeDao.updateByPrimaryKeySelective(etc);
	}
	@Override
	public List<ErpTransportCode> queryList(Map<String, Object> map,Integer pageNo) {
		PageHelper page= new PageHelper();
		page.startPage(pageNo, PageConstant.pageSize);
		List<ErpTransportCode> list = erpTransportCodeDao.queryList(map);
		if(list != null){
			for (ErpTransportCode erpTransportCode : list) {
				erpTransportCode.setFormat(categoryQueryService.getCategoryModelFormat(erpTransportCode.getCategoryModelFormatId()));
			}
		}
		return list;
	}
	
	@Override
	public ErpTransportCode get(Long id) {
		ErpTransportCode code = erpTransportCodeDao.selectByPrimaryKey(id);
		code.setFormat(categoryQueryService.getCategoryModelFormat(code.getCategoryModelFormatId()));
		return code;
	}
	@Transactional
	@Override
	public Map<String, Object> saveTransportCode(Map<String, Object> map) {
		String normalPros = map.get("normalPros").toString();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String transtortCode="";
		if(StringUtils.isNotBlank(normalPros)){
			//正常产品入库的记录
			String[] normalProducts=org.springframework.util.StringUtils.tokenizeToStringArray(normalPros, ",");
			int i=0;
			Long pcId = 0l;
			SysAdmin loginUser = WebUtil.getLoginUser(request);
			Map<String, String> mapCheck = new HashMap<String, String>();
			for (String string : normalProducts) {
				if(!mapCheck.containsKey(string)){//查找是否保存过，防止页面发送数据有问题
					//获取硬件
					ErpHardwareProduct edp = erpHardwareProductDao.selectByPrimaryKey(Long.parseLong(string));
					if(edp != null){
						//先插入 汇总记录表
						if(i==0){
							ErpTransportCode erpTransportCode = new ErpTransportCode();
							erpTransportCode.setAdminId(loginUser.getId());
							erpTransportCode.setCreateDateTime(new Date());
							erpTransportCode.setInvalid(InvalidEnum.FALSE.getInvalidValue());
							erpTransportCode.setStatus(Status.NORMAL.getStatus());
							erpTransportCode.setCategoryModelFormatId(edp.getCategoryModelFormatId());
							transtortCode = CodeUtil.getTransportCode();
							erpTransportCode.setCode(transtortCode);//运输码
							erpTransportCodeDao.insertSelective(erpTransportCode);
							pcId=erpTransportCode.getId();
							insertTransoprtCodeRelation(Long.parseLong(string),pcId);
						}else{
							insertTransoprtCodeRelation(Long.parseLong(string),pcId);
						}
						//更改硬件状态
					    edp.setStatus(HardwareProductNetStatusEnum.LEAVEFACTORY.getCode());
					    erpHardwareProductDao.updateByPrimaryKeySelective(edp);
						//插入物联网相关信息
						ErpHardwareProductChangeLog log = new ErpHardwareProductChangeLog();
						log.setHardwareProductId(edp.getId());
						log.setCreateDateTime(new Date());
						log.setInvalid(InvalidEnum.FALSE.getInvalidValue());
						log.setStatus(Status.NORMAL.getStatus());
						log.setUserId(loginUser.getId());
						log.setUserName(loginUser.getUserName());
						log.setOperateId(HardwareProductNetStatusEnum.LEAVEFACTORY.getCode().longValue());
						log.setOperateExplain("生成运输码");
						erpHardwareProductChangeLogDao.insertSelective(log);
						i++;
					}
					mapCheck.put(string, string);
				}
			}
			ErpTransportCode erpTransportCode = new ErpTransportCode();
			erpTransportCode.setId(pcId);
			erpTransportCode.setCountSum(i);
			erpTransportCodeDao.updateByPrimaryKeySelective(erpTransportCode);
		}
		resultMap.put("transtortCode", transtortCode);
		return resultMap;
	}
	private void insertTransoprtCodeRelation(Long productId,Long codeId){
		ErpHardwareTransportRelation ehtr = new ErpHardwareTransportRelation();
		ehtr.setCreateDateTime(new Date());
		ehtr.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		ehtr.setStatus(Status.NORMAL.getStatus());
		ehtr.setHardwareProductId(productId);
		ehtr.setTransportCodeId(codeId);
		erpHardwareTransportRelationDao.insertSelective(ehtr);
	}
}
