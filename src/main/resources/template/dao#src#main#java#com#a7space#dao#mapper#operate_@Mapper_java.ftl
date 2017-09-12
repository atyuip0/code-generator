package com.lee.dao.po.operate;

import com.feitian.commons.paginator.model.PageBounds;
import com.feitian.commons.paginator.model.PageList;
import com.lee.dao.vo.operate.${entityName}VO;
import com.lee.dao.po.operate.${entityName}PO;


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
