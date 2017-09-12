function validateForm() {
return [ {
<#list columns as po>
    <#if !(po.fieldName=='deleteFlag' || po.fieldName=='createdBy' || po.fieldName=='createdTime' || po.fieldName=='updatedBy' || po.fieldName=='updatedTime')>
    ${po.fieldName}:{
        <#if po.isNull == 'Y'>
        <#else>
        required: true,
        </#if>
        <#if po.length !=0>
        maxlength:${po.length?c}
        </#if>
    }<#if (po_has_next)>,</#if>
    </#if>
</#list>
}, {
<#list columns as po>
    <#if !(po.fieldName=='deleteFlag' || po.fieldName=='createdBy' || po.fieldName=='createdTime' || po.fieldName=='updatedBy' || po.fieldName=='updatedTime')>
    ${po.fieldName}:{
        <#if po.isNull == 'Y'>
        <#else>
        required: "请输入${po.content}",
        </#if>
        <#if po.length !=0>
        maxlength:"最多可以输入${po.length?c}个字符"
        </#if>
    }<#if (po_has_next)>,</#if>
    </#if>
</#list>
} ];
}


$(function($) {

});

