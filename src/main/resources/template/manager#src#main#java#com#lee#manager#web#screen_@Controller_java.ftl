package com.lee.manager.web.screen;

import com.lee.dao.vo.operate.${entityName}VO;
import com.lee.operate.service.${entityPackage}.${entityName}Service;

<#-- restful 通用方法生成 -->
import javax.servlet.http.HttpServletRequest;
import com.feitian.commons.authority.annotation.RequiresPermissions;
import com.feitian.commons.authority.annotation.RequiresAuthentication;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.feitian.commons.authority.SecurityUtils;
import com.feitian.commons.paginator.model.Order;
import com.feitian.commons.paginator.model.PageBounds;
import com.feitian.commons.paginator.model.PageList;
import com.feitian.commons.utils.Constant;
import com.feitian.commons.utils.DateUtil;
import com.feitian.commons.utils.StringUtils;
import com.feitian.commons.utils.UUIDCreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lee.dao.po.operate.AccessUserPO;
import com.lee.dao.vo.common.Result;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
<#-- restful 通用方法生成 -->

/**
* @Title: Controller
* @Description: ${ftl_description}
* @author zhangdaihao
* @date ${ftl_create_time}
* @version V1.0
*
*/
@Controller
@RequestMapping("/${entityName?uncap_first}")
public class ${entityName}Controller {
/**
* Logger for this class
*/
private static final Logger logger = LoggerFactory.getLogger(${entityName}Controller.class);

@Reference(version="1.0.0")
private ${entityName}Service ${entityName?uncap_first}Service;

/**
* ${ftl_description}列表 页面跳转
*
* @return
*/
@RequestMapping("/find.html")
@RequiresPermissions("${entityName?uncap_first}Manage:find")
public String findHTML(HttpServletRequest request) {
return "screen/${entityName?uncap_first}/find.ftl";
}


@ResponseBody
@RequestMapping("/find.ajax")
@RequiresPermissions("${entityName?uncap_first}Manage:find")
public Result findAJAX(${entityName}VO params){
Result result=new Result();
try {
PageBounds pageBounds=new PageBounds(params.getCurrentPage(),params.getPageSize());
pageBounds.addOrder(Order.create("created_time", "desc"));
params.setDeleteFlag(Constant.DELETE_FLAG_NOTDELETED);
PageList<${entityName}VO> pageList=${entityName?uncap_first}Service.queryByPage(pageBounds,params);
    result.setList(pageList);
    result.setPaginator(pageList.getPaginator());
    } catch (Exception e) {
    logger.error("error:",e);
    }
    return result;
    }


    @RequestMapping("/add.html")
	@RequiresPermissions("${entityName?uncap_first}Manage:add")
    public String addHTML(){
    return "screen/${entityName?uncap_first}/add.ftl";
    }


    @ResponseBody
    @RequestMapping("/add.ajax")
	@RequiresPermissions("${entityName?uncap_first}Manage:add")
    public Result addAJAX(@Valid ${entityName}VO ${entityName?uncap_first}VO,BindingResult bindingResult, HttpServletRequest request){
    Result result=new Result();
    try {
    if (bindingResult.hasErrors()) {
    result.setErrorMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    return result;
    }
    Object object=SecurityUtils.getSubject();
    AccessUserPO accessUserPO=JSONObject.toJavaObject((JSONObject)object, AccessUserPO.class);
${entityName?uncap_first}VO.set${pk?cap_first}(UUIDCreator.randomUUID().toString());
${entityName?uncap_first}VO.setDeleteFlag(Constant.DELETE_FLAG_NOTDELETED);
${entityName?uncap_first}VO.setCreatedBy(accessUserPO.getUserName());
${entityName?uncap_first}VO.setCreatedTime(DateUtil.getCurrentDate());
    //保存入库
    int num=${entityName?uncap_first}Service.insert(${entityName?uncap_first}VO);
    if(num>=1){
    result.setInfoMessage("添加成功");
    }
    }catch (Exception e) {
    logger.error("error:",e);
    result.setErrorMessage("添加失败！");
    }
    return result;
    }

    @RequestMapping(value = "/edit/{id}.html", method = RequestMethod.GET)
	@RequiresPermissions("${entityName?uncap_first}Manage:edit")
    public String toEdit(HttpServletRequest request,@PathVariable("id") String id) {
${entityName}VO task = ${entityName?uncap_first}Service.selectByPrimaryKey(id);
    if (task != null) {
    Result result = new Result();
    result.setData(task);
    request.setAttribute("result", result.toJsonString());
    }
    return "screen/${entityName?uncap_first}/edit.ftl";
    }

    @RequestMapping("/edit.ajax")
    @ResponseBody
	@RequiresPermissions("${entityName?uncap_first}Manage:edit")
    public Result editAJAX(@Valid ${entityName}VO ${entityName?uncap_first}VO,BindingResult bindingResult) {
    Result result=new Result();
    try {
    if (bindingResult.hasErrors()) {
    result.setErrorMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    return result;
    }
    Object object=SecurityUtils.getSubject();
    AccessUserPO accessUserPO=JSONObject.toJavaObject((JSONObject)object, AccessUserPO.class);
${entityName?uncap_first}VO.setUpdatedBy(accessUserPO.getUserName());
${entityName?uncap_first}VO.setUpdatedTime(DateUtil.getCurrentDate());
    //保存
    int num=${entityName?uncap_first}Service.updateByPrimaryKeySelective(${entityName?uncap_first}VO);

    if(num>=1){
    result.setInfoMessage("成功修改！");
    }
    } catch (Exception e) {
    logger.error("error:",e);
    result.setErrorMessage("修改失败！");
    }
    return result;
    }

    @ResponseBody
    @RequestMapping("/delete.ajax")
	@RequiresPermissions("${entityName?uncap_first}Manage:delete")
    public Result deleteAJAX(HttpServletRequest request){
    Result result=new Result();
    try {
    String id=request.getParameter("entityId");
    if(StringUtils.isBlank(id)){
    result.setErrorMessage("缺少关键参数!");
    return result;
    }

    Object object=SecurityUtils.getSubject();
    AccessUserPO accessUserPO=JSONObject.toJavaObject((JSONObject)object, AccessUserPO.class);
${entityName}VO ${entityName?uncap_first}VO=new ${entityName}VO();
${entityName?uncap_first}VO.set${pk?cap_first}(id);
${entityName?uncap_first}VO.setDeleteFlag(Constant.DELETE_FLAG_DELETED);
${entityName?uncap_first}VO.setUpdatedBy(accessUserPO.getUserName());
${entityName?uncap_first}VO.setUpdatedTime(DateUtil.getCurrentDate());

    int num=${entityName?uncap_first}Service.updateByPrimaryKeySelective(${entityName?uncap_first}VO);
    if(num>=1){
    result.setInfoMessage("成功删除！");
    }
    } catch (Exception e) {
    result.setErrorMessage("删除失败！");
    logger.error("error:",e);
    }
    return result;
    }

    @ResponseBody
    @RequestMapping("/enableFlagSwitch.ajax")
	@RequiresPermissions("${entityName?uncap_first}Manage:edit")
    public Result enableFlagSwitch(HttpServletRequest request){
    Result result=new Result();
    try {
    String id=request.getParameter("${pk}");
    if(StringUtils.isBlank(id)){
    result.setErrorMessage("缺少关键参数!");
    return result;
    }
    String enableFlag=request.getParameter("enableFlag");
    Object object=SecurityUtils.getSubject();
    AccessUserPO accessUserPO=JSONObject.toJavaObject((JSONObject)object, AccessUserPO.class);

${entityName}VO ${entityName?uncap_first}VO=new ${entityName}VO();
${entityName?uncap_first}VO.set${pk?cap_first}(id);
${entityName?uncap_first}VO.setEnableFlag(1^Integer.valueOf(enableFlag));
${entityName?uncap_first}VO.setUpdatedBy(accessUserPO.getUserName());
${entityName?uncap_first}VO.setUpdatedTime(DateUtil.getCurrentDate());

    int num=${entityName?uncap_first}Service.updateByPrimaryKeySelective(${entityName?uncap_first}VO);
    if(num>=1){
    result.setInfoMessage("成功操作！");
    }
    } catch (Exception e) {
    result.setErrorMessage("操作失败！");
    logger.error("error:",e);
    }
    return result;
    }

    @ResponseBody
    @RequestMapping("/get${entityName}SelectOption.ajax")
    @RequiresAuthentication
    public Result get${entityName}SelectOption(HttpServletRequest request){
    Result result=new Result();
    try {
    Object subject=SecurityUtils.getSubject();
    if(subject!=null){
    AccessUserPO accessUserPO=JSONObject.toJavaObject((JSONObject)subject, AccessUserPO.class);
    result=${entityName?uncap_first}Service.get${entityName}SelectOption(accessUserPO,accessUserPO.getCompanyId());
    }
    } catch (Exception e) {
    logger.error("error:",e);
    }
    return result;
    }

    }
