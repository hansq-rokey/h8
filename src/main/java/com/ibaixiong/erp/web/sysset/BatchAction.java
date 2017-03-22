package com.ibaixiong.erp.web.sysset;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.ibaixiong.entity.ErpBatch;
import com.ibaixiong.entity.ErpBatchDetails;
import com.ibaixiong.erp.service.sysset.ErpBatchDetailsService;
import com.ibaixiong.erp.service.sysset.ErpBatchService;
/**
 * 产品批次管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/sysSet/batch")
public class BatchAction {
	@Resource
	private ErpBatchService erpBatchService;
	@Resource
	private ErpBatchDetailsService erpBatchDetailsService;
	/**
	 * 仓库列表页面
	 * @author zhaolei
	 * @date 2015年10月10日
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list(@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			Model model, HttpServletRequest request) {
		List<ErpBatch> list = erpBatchService.getListPage(pageNo);
		PageInfo<ErpBatch> pageInfo=new PageInfo<ErpBatch>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list",list);
		return "/sysSet/batchList";
	}
	/**
	 * 去详细页面
	 * @author zhaolei
	 * @date 2015年10月10日
	 * @param model
	 * @param batchId
	 * @param request
	 * @return
	 */
	@RequestMapping("/detailsList.html")
	public String detailsList(@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			Model model,
			@RequestParam(value = "batchId", required = false) Long batchId,
			HttpServletRequest request) {
		List<ErpBatchDetails> list=erpBatchDetailsService.getListPageByBatchId(batchId,null,pageNo);
		PageInfo<ErpBatchDetails> pageInfo=new PageInfo<ErpBatchDetails>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list",list);
		model.addAttribute("batch",erpBatchService.get(batchId));
		model.addAttribute("batchId",batchId);
		return "/sysSet/detailsBatchList";
	}
	/**
	 * 修改状态
	 * @author zhaolei
	 * @date 2015年10月10日
	 * @param model
	 * @param batchId
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateInvalid.html")
	public String updateInvalid(Model model,
			@RequestParam(value = "detailsId", required = false) Long detailsId,
			@RequestParam(value = "invalid", required = false) Byte invalid,
			@RequestParam(value = "batchId", required = false) Long batchId,
			HttpServletRequest request) {
		erpBatchDetailsService.updateInvalid(detailsId,invalid);
		return "redirect:/sysSet/batch/detailsList.html?batchId="+batchId;
	}
}
