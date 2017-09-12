package com.a7space.operate.service.${entityPackage};


import com.a7space.commons.paginator.model.PageBounds;
import com.a7space.commons.paginator.model.PageList;
import com.a7space.dao.vo.common.Result;
import com.a7space.dao.vo.operate.${entityName}VO;

/**
* @Title: Service
* @Description: ${ftl_description}
* @author ${author}
* @date ${ftl_create_time}
* @version V1.0
*
*/
public interface ${entityName}Service{

    int insert(${entityName}VO record);

    ${entityName}VO selectByPrimaryKey(String companyId);

    int updateByPrimaryKeySelective(${entityName}VO record);

    PageList<${entityName}VO> queryByPage(PageBounds pageBounds,${entityName}VO ${entityName}VO);

}
