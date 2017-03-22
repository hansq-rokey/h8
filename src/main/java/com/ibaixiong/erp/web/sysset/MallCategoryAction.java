package com.ibaixiong.erp.web.sysset;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.ibaixiong.core.utils.ResponseResult;
import com.ibaixiong.entity.ErpBaseBatch;
import com.ibaixiong.entity.ErpBatch;
import com.ibaixiong.entity.ErpBatchDetails;
import com.ibaixiong.erp.service.sysset.ErpBaseBatchService;
import com.ibaixiong.erp.service.sysset.ErpBatchDetailsService;
import com.ibaixiong.erp.service.sysset.ErpBatchService;
import com.papabear.commons.entity.enumentity.Constant;
import com.papabear.product.api.CategoryQueryService;
import com.papabear.product.entity.MallBasicCategory;
import com.papabear.product.entity.MallBasicCategoryModel;
import com.papabear.product.entity.MallBasicCategoryModelFormat;

/**
 * 获取产品批次下拉相关
 * @author zhaolei
 *
 */
@Controller
@RequestMapping("/sysSet/batch")
public class MallCategoryAction {
	@Resource
	private ErpBaseBatchService erpBaseBatchService;
	@Resource
	private CategoryQueryService categoryQueryService;
	@Resource
	private ErpBatchService erpBatchService;
	@Resource
	private ErpBatchDetailsService erpBatchDetailsService;
	
	@RequestMapping("/getBatchNum.html")
	public void getBatchNum(
			@RequestParam(value = "categoryId", required = false) Long categoryId,
			@RequestParam(value = "categoryModelId", required = false) Long categoryModelId,
			@RequestParam(value = "categoryModelFormatId", required = false) Long categoryModelFormatId,
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		ErpBaseBatch erpBaseBatch = erpBaseBatchService.selectByBatch(categoryId, categoryModelId, categoryModelFormatId);
		if(erpBaseBatch == null){ 
			erpBaseBatch = new ErpBaseBatch();
			MallBasicCategory category = new MallBasicCategory();
			category.setId(categoryId);
			MallBasicCategoryModel cmodel = new MallBasicCategoryModel();
			cmodel.setId(categoryModelId);
			MallBasicCategoryModelFormat format = new MallBasicCategoryModelFormat();
			format.setId(categoryModelFormatId);
			erpBaseBatch.setCategory(category);
			erpBaseBatch.setCategoryModel(cmodel);
			erpBaseBatch.setCategoryModelFormat(format);
			erpBaseBatch.setIndexNum(0);//这里初始化是0
			erpBaseBatchService.insert(erpBaseBatch);
			map.put("num","001");
		}else{
			int num = erpBaseBatch.getIndexNum()+1;
			if(num<10){
				map.put("num","00"+num);
			}
			if(num>=10 &&num<=99){
				map.put("num","0"+num);
			}
			if(num>=100 &&num<=999){
				map.put("num",num+"");
			}
		}
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
	/**
	 * 查询产品大类
	 * @author zhaolei
	 * @date 2015年10月8日
	 * @return
	 * 
	 * {
		"code": 0,								 
	    "message": null, 
	    "result": {
	    	"list": [
	            {
	                "id": 1,					//ID
	                "name": 飘窗款,			
	            }, 
	            ......
	        ]
	    	}
		}
	 */
	@RequestMapping("/getCategoryList.html")
	public void getCategoryList(
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<MallBasicCategory> list = categoryQueryService.queryBasicCategory(Constant.Status.NORMAL.getStatus());
		if(list != null){
			List<Map<String, Object>> dataMap = new ArrayList<Map<String,Object>>();
			for (MallBasicCategory mallBasicCategory : list) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", mallBasicCategory.getId());
				m.put("name", mallBasicCategory.getName());
				dataMap.add(m);
			}
			map.put("list", dataMap);
		}
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
	/**
	 * 查询产品大类
	 * @author zhaolei
	 * @date 2015年10月8日
	 * @return
	 * 
	 * {
		"code": 0,								 
	    "message": null, 
	    "result": {
	    	"list": [
	            {
	                "id": 1,					//ID
	                "name": 飘窗款,			
	            }, 
	            ......
	        ]
	    	}
		}
	 */
	@RequestMapping("/getOemCategoryList.html")
	public void getOemCategoryList(
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		//oem查询的下拉列表不一样需要先从产品批次中检索
		List<ErpBatch> batchList = erpBatchService.getList();
		Map<Long, Object> maps  = new HashMap<Long, Object>();
		List<MallBasicCategory> list = new ArrayList<MallBasicCategory>();
		for (ErpBatch erpBatch : batchList) {
			if(erpBatch.getCategory()!=null){
				Long categoryid = erpBatch.getCategory().getId();
				if(!maps.containsKey(categoryid)){//在键值对中未找到该键值对
					list.add(erpBatch.getCategory());
					maps.put(categoryid, categoryid);//标记为已存在了
				}
			}
		}
		if(list != null && list.size()>0){
			List<Map<String, Object>> dataMap = new ArrayList<Map<String,Object>>();
			for (MallBasicCategory mallBasicCategory : list) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", mallBasicCategory.getId());
				m.put("name", mallBasicCategory.getName());
				dataMap.add(m);
			}
			map.put("list", dataMap);
		}
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
	/**
	 * 查询产品型号
	 * @author zhaolei
	 * @date 2015年10月8日
	 * @return
	 * 
	 * {
		"code": 0,								 
	    "message": null, 
	    "result": {
	    	"list": [
	            {
	                "id": 1,					//ID
	                "name": 飘窗款,			
	            }, 
	            ......
	        ]
	    	}
		}
	 */
	@RequestMapping("/getCategoryModelList.html")
	public void getCategoryModelList(
			@RequestParam(value = "categoryId", required = false) Long categoryId,
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<MallBasicCategoryModel> list = categoryQueryService.queryByCategoryId(categoryId);
		if(list != null){
			List<Map<String, Object>> dataMap = new ArrayList<Map<String,Object>>();
			for (MallBasicCategoryModel mallBasicCategory : list) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", mallBasicCategory.getId());
				m.put("name", mallBasicCategory.getName());
				dataMap.add(m);
			}
			map.put("list", dataMap);
		}
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
	/**
	 * 查询产品型号
	 * @author zhaolei
	 * @date 2015年10月8日
	 * @return
	 * 
	 * {
		"code": 0,								 
	    "message": null, 
	    "result": {
	    	"list": [
	            {
	                "id": 1,					//ID
	                "name": 飘窗款,			
	            }, 
	            ......
	        ]
	    	}
		}
	 */
	@RequestMapping("/getOemCategoryModelList.html")
	public void getOemCategoryModelList(
			@RequestParam(value = "categoryId", required = false) Long categoryId,
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		//oem查询的下拉列表不一样需要先从产品批次中检索
		List<ErpBatch> batchList = erpBatchService.getList();
		Map<Long, Object> maps  = new HashMap<Long, Object>();
		List<MallBasicCategoryModel> list = new ArrayList<MallBasicCategoryModel>();
		for (ErpBatch erpBatch : batchList) {
			if(erpBatch.getCategory()!=null){
				Long categoryid = erpBatch.getCategory().getId();
				//如果当前的ID与传过来要查询的ID相等
				if(categoryid.intValue() == categoryId.intValue()){
					Long categoryMid = erpBatch.getCategoryModel().getId();
					if(!maps.containsKey(categoryMid)){//在键值对中未找到该键值对
						list.add(erpBatch.getCategoryModel());
						maps.put(categoryMid, categoryMid);//标记为已存在了
					}
				}
			}
		}
		if(list != null && list.size()>0){
			List<Map<String, Object>> dataMap = new ArrayList<Map<String,Object>>();
			for (MallBasicCategoryModel mallBasicCategory : list) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", mallBasicCategory.getId());
				m.put("name", mallBasicCategory.getName());
				dataMap.add(m);
			}
			map.put("list", dataMap);
		}
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
	/**
	 * 查询产品型号
	 * @author zhaolei
	 * @date 2015年10月8日
	 * @return
	 * 
	 * {
		"code": 0,								 
	    "message": null, 
	    "result": {
	    	"list": [
	            {
	                "id": 1,					//ID
	                "name": 飘窗款,			
	            }, 
	            ......
	        ]
	    	}
		}
	 */
	@RequestMapping("/getCategoryModelFormatList.html")
	public void getCategoryModelFormatList(
			@RequestParam(value = "categoryModelId", required = false) Long categoryModelId,
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<MallBasicCategoryModelFormat> list = categoryQueryService.queryByCategoryModel(categoryModelId);
		if(list != null){
			List<Map<String, Object>> dataMap = new ArrayList<Map<String,Object>>();
			for (MallBasicCategoryModelFormat mallBasicCategory : list) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", mallBasicCategory.getId());
				m.put("name", mallBasicCategory.getName());
				dataMap.add(m);
			}
			map.put("list", dataMap);
		}
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
	/**
	 * 查询产品型号
	 * @author zhaolei
	 * @date 2015年10月8日
	 * @return
	 * 
	 * {
		"code": 0,								 
	    "message": null, 
	    "result": {
	    	"list": [
	            {
	                "id": 1,					//ID
	                "name": 飘窗款,			
	            }, 
	            ......
	        ]
	    	}
		}
	 */
	@RequestMapping("/getOemCategoryModelFormatList.html")
	public void getOemCategoryModelFormatList(
			@RequestParam(value = "categoryModelId", required = false) Long categoryModelId,
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		//oem查询的下拉列表不一样需要先从产品批次中检索
		List<ErpBatch> batchList = erpBatchService.getList();
		Map<Long, Object> maps  = new HashMap<Long, Object>();
		List<MallBasicCategoryModelFormat> list = new ArrayList<MallBasicCategoryModelFormat>();
		for (ErpBatch erpBatch : batchList) {
			if(erpBatch.getCategoryModel()!=null){
				Long categorymid = erpBatch.getCategoryModel().getId();
				//如果当前的ID与传过来要查询的ID相等
				if(categorymid.intValue() == categoryModelId.intValue()){
					Long categoryMfid = erpBatch.getCategoryModelFormat().getId();
					if(!maps.containsKey(categoryMfid)){//在键值对中未找到该键值对
						list.add(erpBatch.getCategoryModelFormat());
						maps.put(categoryMfid, categoryMfid);//标记为已存在了
					}
				}
			}
		}
		if(list != null && list.size()>0){
			List<Map<String, Object>> dataMap = new ArrayList<Map<String,Object>>();
			for (MallBasicCategoryModelFormat mallBasicCategory : list) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", mallBasicCategory.getId());
				m.put("name", mallBasicCategory.getName());
				dataMap.add(m);
			}
			map.put("list", dataMap);
		}
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
	/**
	 * 查询批次下拉
	 * @author zhaolei
	 * @date 2015年10月10日
	 * @return
	 * 
	 * {
		"code": 0,								 
	    "message": null, 
	    "result": {
	    	"list": [
	            {
	                "id": 1,					//ID
	                "name": 飘窗款,			
	            }, 
	            ......
	        ]
	    	}
		}
	 */
	@RequestMapping("/getOemBatchList.html")
	public void getOemBatchList(
			@RequestParam(value = "categoryId", required = false) Long categoryId,
			@RequestParam(value = "categoryModelId", required = false) Long categoryModelId,
			@RequestParam(value = "categoryModelFormatId", required = false) Long categoryModelFormatId,
			Model model,HttpServletResponse response) {
		String msg = "";
		int code = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		//oem查询的下拉列表不一样需要先从产品批次中检索
		ErpBatch batch = erpBatchService.queryByBatch(categoryId, categoryModelId, categoryModelFormatId,0);
		if(batch != null){
			List<ErpBatchDetails> list = erpBatchDetailsService.getListByBatchId(batch.getId(),(byte)0);
			if(list != null && list.size()>0){
				List<Map<String, Object>> dataMap = new ArrayList<Map<String,Object>>();
				for (ErpBatchDetails bd : list) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("id", bd.getId());
					m.put("name", bd.getBatchNumber());//这个不专门指的是名称是指页面上的显示
					dataMap.add(m);
				}
				map.put("list", dataMap);
			}
		}
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
