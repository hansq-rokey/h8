package com.deppon.dop.module.sdk.shared.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * @Description Security Util
 */
public class SecurityUtil {
	private SecurityUtil(){}
	
	/**
	 * @Description 摘要验证加密方式,先MD5加密后Base64加密
	 * @param plainText
	 * @return String
	 */
	public static String getDigest(String plainText) {
		return Base64.encodeBase64String(DigestUtils.md5Hex(plainText)
				.getBytes());
	}
	
	/**
	 * @Description 时间戳生成方式
	 * @return String
	 */
	public static String getTimestamp(){
		return String.valueOf(System.currentTimeMillis());
	}
}
