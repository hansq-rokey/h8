package com.deppon.dop.module.sdk.shared.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 引入alibaba FastJson.<br>
 * 详情请移步 http://code.alibabatech.com/wiki/display/FastJSON/Overview
 */
public final class FastJsonUtil {
	  private FastJsonUtil(){}
	/**
	 * 对象转Json字符串
	 */
	  public static final String toJSONString(Object object, SerializerFeature... features) {
		  return JSON.toJSONString(object,features);
	  }
	  
	  /**
	   * Json 字符串 转对象
	   */
	  public static final <T> T parseObject(String text, Class<T> clazz, Feature... features){
		 return  JSON.parseObject(text,clazz,features);
	  }
	
}
