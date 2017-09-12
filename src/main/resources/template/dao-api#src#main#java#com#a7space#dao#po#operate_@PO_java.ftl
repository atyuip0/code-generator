package com.a7space.dao.po.operate;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;

import com.a7space.dao.po.common.BasePO;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;


/**   
 * @Title: Entity
 * @Description: ${ftl_description}
 * @author zhanghaihe
 * @date ${ftl_create_time}
 * @version V1.0   
 *
 */
public class ${entityName}PO extends BasePO implements java.io.Serializable {

	<#list columns as po>
    /**${po.content}*/
    <#if (po.isNull == 'Y'|| po.isPk == 'Y')>
    <#else>
    <#if po.type=='java.lang.String'>
    @NotBlank(message = "请输入${po.content}")
    <#else>
    @NotNull(message = "请输入${po.content}")
    </#if>
    </#if>
    <#if po.type!='java.lang.Integer' && po.type!='java.util.Date' && po.isPk != 'Y'>
    <#if po.length !=0>
    @Size(max=${po.length?c})
    </#if>
    </#if>
    private <#if po.type=='java.sql.Blob'>byte[]<#else>${po.type}</#if> ${po.fieldName};

	</#list>
	
	<#list columns as po>
	public <#if po.type=='java.sql.Blob'>byte[]<#else>${po.type}</#if> get${po.fieldName?cap_first}(){
		return this.${po.fieldName};
	}

	public void set${po.fieldName?cap_first}(<#if po.type=='java.sql.Blob'>byte[]<#else>${po.type}</#if> ${po.fieldName}){
		this.${po.fieldName} = ${po.fieldName};
	}
	</#list>
}
