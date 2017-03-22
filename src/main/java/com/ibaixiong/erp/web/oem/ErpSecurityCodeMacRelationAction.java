package com.ibaixiong.erp.web.oem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ibaixiong.erp.service.oem.ErpSecurityCodeMacRelationService;
import com.ibaixiong.erp.web.util.Response;
import com.papabear.commons.sign.SignUtils;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.entity.MallBasicCategoryModelFormat;

@Controller
public class ErpSecurityCodeMacRelationAction {
	@Resource
	private ErpSecurityCodeMacRelationService securityCodeMacRelationService;
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	//@Value("${erp.fwm.sign.key}")
	private String key="erpfwm1234%^&*";
	@Autowired
	private CategoryQueryService categoryQueryService;

	/**
	 * 生成防伪码记录
	 * 
	 * @param url
	 *            防伪码url
	 * @param num
	 *            生成记录数
	 * @param smart
	 *            知否智能 1 智能，0非智能
	 * @return
	 */
//	@ResponseBody
	@RequestMapping(value ="/fwm/create.html", method = RequestMethod.POST)
	public void createSecurityCode(@RequestParam(defaultValue = "http://www.ibaixiong.com/h5/scan-record.html?code=") String url,
			@RequestParam(defaultValue = "2") Integer num, @RequestParam(defaultValue = "1") Byte smart, 
			HttpServletResponse response,HttpServletRequest request) {
//		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		byte code = 0;
//		String message = "成功";
		Long modelId = Long.valueOf(request.getParameter("modelId"));
		Long formatId = Long.valueOf(request.getParameter("formatId"));
		String modelName = request.getParameter("modelName");
		String formatName = request.getParameter("formatName");
		String batch = request.getParameter("batch");
		MallBasicCategoryModelFormat format=categoryQueryService.getCategoryModelFormat(formatId);
//		Set<String> set = securityCodeMacRelationService.createSecurityCode(url, num, smart);
		Set<String> set = securityCodeMacRelationService.createSecurityCodeMac(url, num, format.getIsSmart(),modelId,formatId,modelName,formatName,batch);
		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		ServletOutputStream out = null;
		response.setContentType("multipart/form-data");
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		//这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859  
		response.setCharacterEncoding("UTF-8");
		try {
			response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(format.getName(), "UTF-8") + ".txt");
			// 3.通过response获取ServletOutputStream对象(out)
			out = response.getOutputStream();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String outUrl = url + it.next() + "\r\n";
				out.write(outUrl.getBytes());
				out.flush();
			}
			out.close();
			out.flush();
		} catch (IOException e) {
//			code = 1;
//			message = "失败";
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
//		jsonMap.put("code", code);
//		jsonMap.put("message", message);
//		return JSON.toJSONString(jsonMap);
	}

	
	/**
	 * 返回16位随机数，3位随机数+13位当时时间毫秒数
	 * 
	 * @return
	 */
	private String getMacCode() {
		// 当前时间13位
		String timeStr = String.valueOf(System.currentTimeMillis());
		// 加上3位随机数
		Random random = new Random();
		String randomCode2 = String.valueOf(random.nextInt(89) + 10);
		return randomCode2 + timeStr;
	}
	
	@ResponseBody
	@RequestMapping(value = "/fwmupload.html", method = RequestMethod.POST)
	public String uploadTxt(HttpServletRequest request) {
		Response response = new Response();
		String timeStamp = request.getParameter("time");
		String sign1 = request.getParameter("sign");
		String baseByte = request.getParameter("file");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timeStamp", timeStamp);
		logger.info("timeStamp-----------{}",timeStamp);
		logger.info("接收到的签名sign-----------{}",sign1);
		String sign2 = SignUtils.getSign(map, key);
		logger.info("校验得到的签名-----------{}",sign2);
		logger.info("baseByte-----------{}",baseByte);
		if (!sign2.equals(sign1)) {
			response.setSuccess(Boolean.FALSE);
			response.setMessage("检测到该请求非法！");
			return JSON.toJSONString(response);
		}

		if (StringUtils.isBlank(baseByte)) {
			response.setSuccess(Boolean.FALSE);
			response.setMessage("上传文件不能为空！");
			return JSON.toJSONString(response);
		}
		try {
			byte[] baseByteSet = Base64.decodeBase64(baseByte);
			for (int i = 0; i < baseByteSet.length; ++i) {
				if (baseByteSet[i] < 0) {
					// 调整异常数据
					baseByteSet[i] += 256;
				}
			}
			securityCodeMacRelationService.uploadFile(baseByteSet, String.valueOf(System.currentTimeMillis())+".txt");
		} catch (IOException e) {
			e.printStackTrace();
			response.setSuccess(Boolean.FALSE);
			response.setMessage("读取文件出错");
			return JSON.toJSONString(response);
		}
		response.setMessage("文件处理成功！");
		return JSON.toJSONString(response);
	}

	@ResponseBody
	@RequestMapping(value = "/fwm/bind.html", method = RequestMethod.POST)
	public String fwmBindMac(@RequestParam(value = "url", required = false) String url, @RequestParam(value = "mac", required = false) String mac,
			@RequestParam(value = "time", required = false) String timeStamp, @RequestParam(value = "sign", required = false) String sign) {
		Response response = new Response();
		if (StringUtils.isBlank(url) || StringUtils.isBlank(mac) || StringUtils.isBlank(timeStamp) || StringUtils.isBlank(sign)) {
			response.setSuccess(Boolean.FALSE);
			response.setMessage("参数不能为空，请检查上传内容");
			return JSON.toJSONString(response);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timeStamp", timeStamp);
		String sign2 = SignUtils.getSign(map, key);
		if (!sign2.equals(sign)) {
			response.setSuccess(Boolean.FALSE);
			response.setMessage("检测到该请求非法！");
			return JSON.toJSONString(response);
		}

		try {
			securityCodeMacRelationService.bindMac(url, mac);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(Boolean.FALSE);
			response.setMessage("绑定失败");
			return JSON.toJSONString(response);
		}
		response.setMessage("绑定成功！");
		return JSON.toJSONString(response);
	}

	public void setKey(String key) {
		this.key = key;
	}

}
