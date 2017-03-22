package com.ibaixiong.erp.web.storage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ibaixiong.constant.PageConstant;
import com.ibaixiong.core.utils.DateUtil;
import com.ibaixiong.core.utils.ResponseResult;
import com.ibaixiong.entity.ErpBom;
import com.ibaixiong.entity.User;
import com.ibaixiong.erp.service.storage.ErpBomService;
import com.ibaixiong.erp.service.storage.MallOrderService;
import com.ibaixiong.erp.service.sys.UserService;
import com.ibaixiong.erp.util.DateEditor;
import com.papabear.commons.entity.Pager;
import com.papabear.order.api.OrderService;
import com.papabear.order.entity.MallOrder;
import com.papabear.order.entity.MallOrderItem;
import com.papabear.order.entity.MallOrderRevicerInformation;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.api.ProductQueryService;
@Controller
@RequestMapping("/bom")
public class ErpBomAction{
	
	@Resource
	ErpBomService erpBomService;
	@Resource
	private MallOrderService mallOrderService;
	@Resource
	private CategoryQueryService categoryQueryService;
	@Resource
	private OrderService orderService;
	@Resource
	private UserService userService;
	@Resource
	private ProductQueryService productQueryService;
	
	@RequestMapping("/list.html")
	public String orderList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(value = "start", required = false) Date startTime, 
			@RequestParam(value = "end", required = false) Date endTime,ModelMap model,
			HttpServletRequest request) {
		Byte status = null;
		String statusStr = request.getParameter("status");
		String keywords = request.getParameter("keywords");
		String uniqueCode = request.getParameter("uniqueCode");
		if (StringUtils.isNotBlank(statusStr)) {
			status = Byte.valueOf(statusStr);
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(keywords)) {
			model.addAttribute("keywords", keywords);
		}
		if (startTime != null) {
			model.addAttribute("start", DateUtil.format(startTime, "yyyy-MM-dd"));
		}
		if (endTime != null) {
			model.addAttribute("end", DateUtil.format(endTime, "yyyy-MM-dd"));
			endTime = DateUtil.getDateEndTime(endTime);
		}
		if(endTime==null){
			endTime = DateUtil.getDateEndTime(new Date());
		}
		Pager<com.papabear.order.entity.MallOrder> pager=null;
		if (StringUtils.isBlank(uniqueCode)) {
//			list = mallOrderService.queryByParamters(keywords, startTime, endTime, status, pageNo, PageConstant.pageSize);
//			pager = orderService.queryCustomerOrder(keywords, startTime, endTime, null, null, pageNo, PageConstant.pageSize);
			pager = orderService.queryOrdersBysearch(keywords, startTime, endTime, status, pageNo, PageConstant.pageSize,(byte)0);

		} else {
			List<MallOrder> list = mallOrderService.queryByUniqueCode(uniqueCode);
			pager=new Pager<MallOrder>(list.size(), pageNo, PageConstant.pageSize);
			pager.setList(list);
		}
//		PageInfo<MallOrder> pageInfo = new PageInfo<MallOrder>(list);
		model.addAttribute("pageInfo", pager);
		model.addAttribute("status", status);
		Map<String, Object> map = new HashMap<String, Object>();
		for (MallOrder order : pager.getList()) {
//			MallOrderRevicerInformation information = mallOrderRevicerInformationService.getByOrderNumberAndUserId(order.getOrderNumber(), order.getUser().getId());
			order.setInformation(orderService.getRevicerByUserIdAndOrderNumber(order.getUserId(), order.getOrderNumber()));
			List<MallOrderItem> orderItems=orderService.queryOrderItems(order.getUserId(), order.getOrderNumber());
			for(MallOrderItem item:orderItems){
				item.setCategoryModelFormatNumber(categoryQueryService.getCategoryModelFormat(item.getProductModelFormatId()).getCategoryModelFormatNumber());
				item.setPics(productQueryService.queryPics(null, item.getProductModelFormatId(), (short)2));
			}
			order.setOrderItems(orderItems);
			User user=userService.getuUser(order.getUserId());
			if(user==null)continue;
			order.setEmail(user.getEmail());
			order.setBxNum(user.getBxNum());
			order.setPhone(user.getPhone());
//			order.setInformation(information);
		}
		map.put("orders", pager.getList());//js 用到
		model.addAttribute("orderList", pager.getList());//TODO
		model.addAttribute("json", JSON.toJSONString(ResponseResult.result(1, "", map)));

		// System.out.println(JSON.toJSONString(ResponseResult.result(1,"",map)));
		return "storage/order_bom_list";
	}
	@RequestMapping("/bom_list.html")
	public String getList(@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			String orderNumber,
			Model model,HttpServletRequest request){
		MallOrder order = orderService.getMallOrder(orderNumber);
		order.setInformation(orderService.getRevicerByUserIdAndOrderNumber(order.getUserId(), orderNumber));
		List<ErpBom> bomList = erpBomService.getListByOrderNumber(orderNumber,pageNo);
		PageInfo<ErpBom> pageInfo = new PageInfo<ErpBom>(bomList);
		model.addAttribute("order", order);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("bomList", bomList);
		return "/storage/bom_list";
	}
/*	@RequestMapping("/del.html")
	public String delete(@RequestParam(value="id",required = false)Long id,
			HttpServletRequest request){
		erpBomService.delete(id);
		return "redirect:/bom/list.html";
	}
*/	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
}
