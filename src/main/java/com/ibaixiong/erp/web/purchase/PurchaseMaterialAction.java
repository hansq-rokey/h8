package com.ibaixiong.erp.web.purchase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ibaixiong.core.utils.DateUtil;
import com.ibaixiong.core.utils.ResponseResult;
import com.ibaixiong.entity.ErpPurchaseMaterial;
import com.ibaixiong.erp.service.purchase.ErpPurchaseMaterialService;
import com.ibaixiong.erp.util.InvalidEnum;
import com.ibaixiong.erp.web.util.WebUtil;
/**
 * 
 * @author zhaolei
 * @description 物料管理相关
 * @create 2015年9月29日
 *
 */
@Controller
@RequestMapping("/material")
public class PurchaseMaterialAction {
	@Resource
	ErpPurchaseMaterialService erpPurchaseMaterialService;
	
	/**
	 * 物料管理列表页面
	 * @author zhaolei
	 * @date 2015年10月9日
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list(@RequestParam(value="pageNo",defaultValue="1")Integer pageNo,
			Model model, HttpServletRequest request) {
		List<ErpPurchaseMaterial> list = erpPurchaseMaterialService.getList( pageNo);
		PageInfo<ErpPurchaseMaterial> pageInfo=new PageInfo<ErpPurchaseMaterial>(list);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("list",list);
		return "/purchase/materialList";
	}
	/**
	 * 保存物料管理对象
	 * @author zhaolei
	 * @date 2015年9月29日
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/save.html")
	public String save(Model model,
			@ModelAttribute("material") ErpPurchaseMaterial material,HttpServletRequest request) {
		if(StringUtils.isNotBlank(material.getArrivalDateStr())){
			material.setArrivalDate(DateUtil.parse(material.getArrivalDateStr()));
		}
		if(StringUtils.isNotBlank(material.getPurchaseDateStr())){
			material.setPurchaseDate(DateUtil.parse(material.getPurchaseDateStr()));
		}
		if(material.getId() == null){
			//新增
			material.setInvalid(InvalidEnum.FALSE.getInvalidValue());
			material.setCreateDateTime(new Date());
			material.setAdmin(WebUtil.getLoginUser(request));
			erpPurchaseMaterialService.insert(material);
		}else{
			//修改
			material.setUpdateTime(new Date());
			erpPurchaseMaterialService.update(material);
		}
		return "redirect:/material/list.html";
	}
	@RequestMapping("/del.html")
	public String del(Model model,
			@RequestParam(value = "materialId", required = false) Long materialId,
			HttpServletRequest request) {
		erpPurchaseMaterialService.delete(materialId);//删除主表
		return "redirect:/material/list.html";
	}
	@RequestMapping("/getMaterial.html")
	public void getPurchaseOrder(
			@RequestParam(value = "id", required = false) Long id,
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		ErpPurchaseMaterial erpMaterial = erpPurchaseMaterialService.get(id);
		map.put("id", erpMaterial.getId());
		map.put("serialNumber", erpMaterial.getSerialNumber());
		map.put("name", erpMaterial.getName());
		map.put("model", erpMaterial.getModel());
		map.put("num", erpMaterial.getNum());
		map.put("price", erpMaterial.getPrice());
		map.put("unit", erpMaterial.getUnit());
		map.put("coefficient", erpMaterial.getCoefficient());
		map.put("calculateModel", erpMaterial.getCalculateModel());
		map.put("purchasePrice", erpMaterial.getPurchasePrice());
		map.put("factoryPrice", erpMaterial.getFactoryPrice());
		if(erpMaterial.getPurchaseDate() != null)
			map.put("purchaseDate", DateUtil.format(erpMaterial.getPurchaseDate(),"yyyy-MM-dd"));
		else
			map.put("purchaseDate", "");
		if(erpMaterial.getArrivalDate() != null)
			map.put("arrivalDate", DateUtil.format(erpMaterial.getArrivalDate(),"yyyy-MM-dd"));
		else
			map.put("arrivalDate", "");
		map.put("remark", erpMaterial.getRemark());
		PrintWriter writer = null;
        try {
       	 	writer = response.getWriter();
            writer.write(JSON.toJSONString(ResponseResult.result(code, msg,map)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
	}
}
