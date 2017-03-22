package com.ibaixiong.erp.service.oem.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ibaixiong.entity.ErpSecurityCodeMacRelation;
import com.ibaixiong.erp.dao.oem.ErpSecurityCodeMacRelationDao;
import com.ibaixiong.erp.service.oem.ErpSecurityCodeMacRelationService;
import com.ibaixiong.erp.util.InvalidEnum;
import com.papabear.commons.entity.enumentity.Constant.Status;

@Service
public class ErpSecurityCodeMacRelationServiceImpl implements ErpSecurityCodeMacRelationService {

	@Resource
	ErpSecurityCodeMacRelationDao securityCodeMacRelationDao;
	private Logger logger=LoggerFactory.getLogger(getClass());
	@Value("${erp.fwm.file.path}")
	private String path;
	
	@Override
	public Set<String> createSecurityCode(String url, Integer num, Byte smart) {
		List<String> list=securityCodeMacRelationDao.queryAll();
		Set<String> set=new HashSet<String>();
		while(set.size()<num){
			String code=this.getCode()+smart.toString();
			if(!list.contains(code)){
				set.add(code);				
			}
		}
		List<ErpSecurityCodeMacRelation> relations=this.add(set, url, smart);
		securityCodeMacRelationDao.insertBath(relations);
		return set;
	}
	
	@Override
	public Set<String> createSecurityCodeMac(String url, Integer num, Byte smart,Long modelId,Long formatId,String modelName,String formatName,String batch) {
		List<String> list=securityCodeMacRelationDao.queryAll();
		Set<String> set=new HashSet<String>();
		while(set.size()<num){
			String code=this.getCode()+smart.toString();
			if(!list.contains(code)){
				set.add(code);				
			}
		}
		List<ErpSecurityCodeMacRelation> relations=this.add(set, url, smart,modelId,formatId,modelName,formatName,batch);
		securityCodeMacRelationDao.insertBath(relations);
		return set;
	}
	
	private List<ErpSecurityCodeMacRelation> add(Set<String> set,String url,Byte smart){
		Iterator<String> it=set.iterator();
		List<ErpSecurityCodeMacRelation> list=new ArrayList<ErpSecurityCodeMacRelation>();
		while(it.hasNext()){
			String code=it.next();
			list.add(this.add(code, url, smart));
		}
		return list;
	}
	
	private List<ErpSecurityCodeMacRelation> add(Set<String> set,String url,Byte smart,Long modelId,Long formatId,String modelName,String formatName,String batch){
		Iterator<String> it=set.iterator();
		List<ErpSecurityCodeMacRelation> list=new ArrayList<ErpSecurityCodeMacRelation>();
		while(it.hasNext()){
			String code=it.next();
			list.add(this.add(code, url, smart,modelId,formatId,modelName,formatName,batch));
		}
		return list;
	}
	
	private ErpSecurityCodeMacRelation add(String code,String url,Byte isSmart){
		ErpSecurityCodeMacRelation relation=new ErpSecurityCodeMacRelation();
		relation.setCreateDateTime(new Date());
		relation.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		relation.setIsSmart(isSmart);
		relation.setSecurityCode(code);
		relation.setStatus(Status.NORMAL.getStatus());
		relation.setUrl(url+code);
		return relation;
	}
	private ErpSecurityCodeMacRelation add(String code,String url,Byte isSmart,Long modelId,Long formatId,String modelName,String formatName,String batch){
		ErpSecurityCodeMacRelation relation=new ErpSecurityCodeMacRelation();
		relation.setCreateDateTime(new Date());
		relation.setInvalid(InvalidEnum.FALSE.getInvalidValue());
		relation.setIsSmart(isSmart);
		relation.setSecurityCode(code);
		relation.setStatus(Status.NORMAL.getStatus());
		relation.setUrl(url+code);
		relation.setModelId(modelId);
		relation.setMac(code);
		relation.setModelName(modelName);
		relation.setFormatId(formatId);
		relation.setFormatName(formatName);
		relation.setBatch(batch);
		return relation;
	}
	/**
	 * 返回15位随机数，2位随机数+13位当时时间毫秒数
	 * 
	 * @return
	 */
	private String getCode() {
		// 当前时间13位
		String timeStr = String.valueOf(System.currentTimeMillis());
		// 加上3位随机数
		Random random = new Random();
		String randomCode2 = String.valueOf(random.nextInt(89) + 10);
		return randomCode2 + timeStr;
	}

	@Override
	public int uploadFile(byte[] buf,String fileType) throws IOException {
		InputStream inputStream=null;
		BufferedReader reader=null;
		try {
			File destFile=new File(path+fileType);
			inputStream=new ByteArrayInputStream(buf);
			FileUtils.copyInputStreamToFile(inputStream, destFile);
			reader=new BufferedReader(new FileReader(destFile));
			String str=null;
			while((str=reader.readLine())!=null){
				logger.info("read file content---{}",str);
				String[] urlMacArr=str.split(" ");
				String url=urlMacArr[1];
				String mac=urlMacArr[0];
				logger.info("url---------{}",url);
				logger.info("mac---------{}",mac);
				if(url==null||mac==null){
					continue;
				}
				String fwm=url.substring(url.indexOf("=")+1, url.length());
				//防伪码16位
				if(fwm.length()!=16){
					continue;
				}
				logger.info("fwm---------{}",fwm);
				ErpSecurityCodeMacRelation relation=securityCodeMacRelationDao.getSecurityCodeMacRelation(fwm);
				if(relation==null){
					continue;
				}
				relation.setMac(mac);
				relation.setUpdateTime(new Date());
				securityCodeMacRelationDao.updateByPrimaryKeySelective(relation);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(reader!=null){
				reader.close();				
			}
			if(inputStream!=null){
				inputStream.close();				
			}
		}
		return 1;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int bindMac(String url,String mac) {
		if(url==null||mac==null){
			return 0;
		}
		String fwm=url.substring(url.indexOf("=")+1, url.length());
		ErpSecurityCodeMacRelation relation=securityCodeMacRelationDao.getSecurityCodeMacRelation(fwm);
		if(relation==null){
			return 0;
		}
		relation.setMac(mac);
		relation.setUpdateTime(new Date());
		securityCodeMacRelationDao.updateByPrimaryKeySelective(relation);
		return 1;
	}
	
	@Override
	public ErpSecurityCodeMacRelation getErpSecurityCodeMacRelation(String code) {

		return securityCodeMacRelationDao.getSecurityCodeMacRelation(code);
	}

	@Override
	public int bindMacByFwm(String fwm, String mac) {
		ErpSecurityCodeMacRelation relation=securityCodeMacRelationDao.getSecurityCodeMacRelation(fwm);
		if(relation==null){
			return 0;
		}
		relation.setMac(mac);
		relation.setUpdateTime(new Date());
		securityCodeMacRelationDao.updateByPrimaryKeySelective(relation);
		return 1;
	}
}
