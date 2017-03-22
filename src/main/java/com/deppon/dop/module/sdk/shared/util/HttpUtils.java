package com.deppon.dop.module.sdk.shared.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpUtils {
	private HttpUtils(){}
	/**
	 * HTTP的RequestBody方式请求
	 */
	public static final String sendRequest(String url,NameValuePair[] paramsList,String encoding,int timeout) throws IOException{
		
		if(null==url||"".equals(url)){
			throw new NullPointerException("url empty");
		}
		if(null == paramsList){
			throw new NullPointerException("paramsList empty");
		}
		if(null==encoding||"".equals(encoding)){
			encoding = "UTF-8";
		}
		if(timeout <= 0){
			timeout = 5000;
		}
		//设置PostMethod
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setContentCharset(encoding);
		postMethod.getParams().setHttpElementCharset(encoding);
		
		//设置body
		postMethod.setRequestBody(paramsList);
		
		//http client 参数设置 连接超时 读取数据超时
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeout);
		
		try {
			httpClient.executeMethod(postMethod);
			 if(postMethod.getStatusCode() == HttpStatus.SC_OK){
				 return postMethod.getResponseBodyAsString();
			 }
			 throw new IllegalStateException("sendRequest remote error");
		}finally{
			postMethod.releaseConnection();
		}
	}
	
}
