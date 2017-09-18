package com.a7space.dao.mapper.operate;

import com.a7space.commons.paginator.model.PageBounds;
import com.a7space.commons.paginator.model.PageList;
import com.a7space.dao.vo.operate.${entityName}VO;
import com.a7space.dao.po.operate.${entityName}PO;

/**
 * @Title: Mapper
 * @Description: ${ftl_description}
 * @author ${author}
 * @date ${ftl_create_time}
 * @version V1.0   
 *
 */
public interface ${entityName}Mapper {

    int insert(${entityName}PO record);

    ${entityName}VO selectByPrimaryKey(String ${pk});

    PageList<${entityName}VO> selectBySelectiveByPage(PageBounds pageBounds,${entityName}VO record);

    int updateByPrimaryKeySelective(${entityName}VO record);
}
