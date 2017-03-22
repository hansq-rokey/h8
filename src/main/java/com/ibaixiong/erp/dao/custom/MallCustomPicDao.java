package com.ibaixiong.erp.dao.custom;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.MallCustomPic;

public interface MallCustomPicDao {
    int deleteByPrimaryKey(Long id);

    int insert(MallCustomPic record);

    int insertSelective(MallCustomPic record);

    MallCustomPic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MallCustomPic record);

    int updateByPrimaryKey(MallCustomPic record);
    
    List<MallCustomPic> queryCustomPicList(Map<String, Object> map);
    
    /**
     * 根据订单编号查询上传图片
     * @param orderNumber
     * @return
     */
    List<MallCustomPic> queryMallCustomPicByOrderNumber(String orderNumber);
    
    MallCustomPic getMallCustomPicByOrderNumberAndType(@Param("orderNumber")String orderNumber,@Param("type")Byte type);
}