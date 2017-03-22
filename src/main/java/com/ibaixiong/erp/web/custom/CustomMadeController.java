package com.ibaixiong.erp.web.custom;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ibaixiong.core.utils.ALiYunUtil;
import com.ibaixiong.entity.ErpPanelPrint;
import com.ibaixiong.entity.MallCustomPic;
import com.ibaixiong.entity.SysAdmin;
import com.ibaixiong.erp.service.custom.ErpPanelPrintService;
import com.ibaixiong.erp.service.custom.MallCustomPicService;
import com.ibaixiong.erp.web.util.Response;

@Controller
public class CustomMadeController {

	@Resource
	private ErpPanelPrintService erpPanelPrintService;
	@Resource
	private MallCustomPicService customPicService;

	@RequestMapping("/u/custom/add/list")
	public String customMadeList(@RequestParam(value = "pn", defaultValue = "1") Integer pageNo, ModelMap model) {
		int pageSize = 10;
		List<ErpPanelPrint> panelPrints = erpPanelPrintService.queryPanelPrintByStatus((byte) 0, pageSize, pageNo);
		model.addAttribute("panelPrints", panelPrints);
		PageInfo<ErpPanelPrint> pageInfo=new PageInfo<ErpPanelPrint>(panelPrints);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("status", 0);
		model.addAttribute("url", "/u/custom/add/list.html");
		return "custom/custom_list";
	}

	@RequestMapping("/u/custom/send/list")
	public String customMadeSendList(@RequestParam(value = "pn", defaultValue = "1") Integer pageNo, ModelMap model) {
		int pageSize = 10;
		List<ErpPanelPrint> panelPrints = erpPanelPrintService.queryPanelPrintByStatus((byte) 1, pageSize, pageNo);
		model.addAttribute("panelPrints", panelPrints);
		PageInfo<ErpPanelPrint> pageInfo=new PageInfo<ErpPanelPrint>(panelPrints);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("status", 1);
		model.addAttribute("url", "/u/custom/send/list.html");
		return "custom/custom_list";
	}

	/**
	 * 
	 * @param id
	 * @param response
	 */
	@RequestMapping("/u/custom/download")
	public void download(@RequestParam String orderNumber, HttpServletResponse response) {
		MallCustomPic pic = customPicService.getMallCustomPicByOrderNumberAndType(orderNumber, (byte) 3);
		if (pic == null) {
			try {
				PrintWriter write = response.getWriter();
				write.print("没有找到公司处理的图片");
				write.close();
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		ServletOutputStream out = null;
		response.setContentType("multipart/form-data");
		String key = pic.getPath();
		response.setHeader("Content-Disposition",
				"attachment;fileName=" + pic.getOrderNumber() + "_" + pic.getType() + "." + pic.getPicSuffx() + "");
		InputStream inputStream = null;
		try {
			inputStream = ALiYunUtil.downloadFile(ALiYunUtil.BUCKET_NAME, key);
			// 3.通过response获取ServletOutputStream对象(out)
			out = response.getOutputStream();
			int b = 0;
			byte[] buffer = new byte[1024];
			while ((b = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, b);
				out.flush();
			}
			inputStream.close();
			out.close();
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 更新发货
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/u/custom/update")
	public String sendGoods(@RequestParam String ids, @RequestParam String logisticsCompany, @RequestParam String billNo,HttpServletRequest request) {
		SysAdmin admin = com.ibaixiong.erp.web.util.WebUtil.getLoginUser(request);
		int flag=erpPanelPrintService.update(ids, logisticsCompany, billNo,admin.getId());
		Response res=new Response();
		if(flag>0){
			res.setMessage("更新成功~~");
		}else{
			res.setMessage("更新失败/(ㄒoㄒ)/~~");
			res.setSuccess(Boolean.FALSE);
		}
		return JSON.toJSONString(res);
	}
}
