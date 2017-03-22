package com.ibaixiong.erp.dao.oem;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ibaixiong.entity.ErpTransportCode;

public interface ErpTransportCodeDao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpTransportCode record);

    int insertSelective(ErpTransportCode record);

    ErpTransportCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpTransportCode record);

    int updateByPrimaryKey(ErpTransportCode record);
    /**
     * 根据运输码查询
     * @param code     运输码
     * @param invalid  是否有效
     * @return
     */
    ErpTransportCode getErpTransportCode(@Param("code")String code,@Param("invalid")boolean invalid);
    
    Map<String, Object> getErpTransportCodeBycode(@Param("code")String code,@Param("invalid")boolean invalid);
    /**
     * 查询运输码列表
     * @author zhaolei
     * @date 2015年10月16日
     * @param map
     * @return
     */
    List<ErpTransportCode> queryList(Map<String, Object> map);
}