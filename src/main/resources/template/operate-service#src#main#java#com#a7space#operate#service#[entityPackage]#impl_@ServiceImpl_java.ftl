package com.a7space.operate.service.${entityPackage}.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.a7space.commons.paginator.model.Order;
import com.a7space.commons.paginator.model.PageBounds;
import com.a7space.commons.paginator.model.PageList;
import com.a7space.commons.utils.BeanUtils;
import com.a7space.commons.utils.StringUtils;
import com.lee.dao.mapper.operate.AccessAuthorityMapper;
import com.lee.dao.mapper.operate.${entityName}Mapper;
import com.lee.dao.po.operate.AccessAuthorityPO;
import com.lee.dao.po.operate.AccessUserPO;
import com.lee.dao.po.operate.CompanyInfoPO;
import com.lee.dao.po.operate.${entityName}PO;
import com.lee.dao.vo.common.Result;
import com.lee.dao.vo.operate.${entityName}VO;
import com.lee.operate.service.space.${entityName}Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
* @Title: ServiceImpl
* @Description: ${ftl_description}
* @author ${author}
* @date ${ftl_create_time}
* @version V1.0
*
*/
public class ${entityName}ServiceImpl implements ${entityName}Service {


    private static Logger logger = LoggerFactory.getLogger(${entityName}ServiceImpl.class);

    @Autowired
    private ${entityName}Mapper ${entityName?uncap_first}Mapper;


    @Override
    public int insert(${entityName}VO ${entityName?uncap_first}VO) {
        int result=0;
        try {
            ${entityName}PO ${entityName?uncap_first}PO=new ${entityName}PO();
            BeanUtils.copyProperties(${entityName?uncap_first}PO, ${entityName?uncap_first}VO);
            result=${entityName?uncap_first}Mapper.insert(${entityName?uncap_first}PO);
        } catch (Exception e) {
            logger.error("error:",e);
        }
        return result;
    }

    @Override
    public ${entityName}VO selectByPrimaryKey(String ${pk}) {
        ${entityName}VO result=null;
        try {
            result=${entityName?uncap_first}Mapper.selectByPrimaryKey(${pk});
        } catch (Exception e) {
            logger.error("error:",e);
        }
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(${entityName}VO record) {
        int result=0;
        try {
            result=${entityName?uncap_first}Mapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("error:",e);
        }
        return result;
    }

    @Override
    public PageList<${entityName}VO> queryByPage(PageBounds pageBounds, ${entityName}VO ${entityName?uncap_first}VO) {
        try {
            PageList<${entityName}VO> list = ${entityName?uncap_first}Mapper.selectBySelectiveByPage(pageBounds,${entityName?uncap_first}VO);
            return list;
        } catch (Exception e) {
            logger.error("error:",e);
        }
        return null;
    }

}