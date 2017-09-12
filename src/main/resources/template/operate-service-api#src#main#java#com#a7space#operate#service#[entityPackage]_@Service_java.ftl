
package com.lee.operate.service.${entityPackage};


import com.feitian.commons.paginator.model.PageBounds;
import com.feitian.commons.paginator.model.PageList;
import com.lee.dao.vo.common.Result;
import com.lee.dao.vo.operate.${entityName}VO;

public interface ${entityName}Service{

    int insert(${entityName}VO record);

    ${entityName}VO selectByPrimaryKey(String companyId);

    int updateByPrimaryKeySelective(${entityName}VO record);

    PageList<${entityName}VO> queryByPage(PageBounds pageBounds,${entityName}VO ${entityName}VO);

    Result get${entityName}SelectOption(String userId, String companyId);

}