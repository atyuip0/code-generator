<head>
    <title>新建${ftl_description}</title>
</head>
<body>
<div id="vue-body">
    <!-- 顶部导航部分开始 -->
    <div id="childTopBar" style="display:none;">
        <div class="col-lg-12">
            <ol class="breadcrumb">
                <li><a href="${dollar}{staticResPath}/index.html">主页</a></li>
                <li><span>${ftl_description}管理</span></li>
                <li class="active"><span>新建${ftl_description}</span></li>
            </ol>
        </div>
    </div>
    <!-- 顶部导航部分结束 -->

    <!-- 表单部分开始 -->
    <form id="saveForm" action="${dollar}{staticResPath}/${entityName?uncap_first}/add.ajax" data-find-url="${dollar}{staticResPath}/${entityName?uncap_first}/find.html">
        <div class="row">
            <div class="col-lg-12">
                <div class="main-box">
                    <header class="main-box-header with-bg clearfix">
                        <h2>基本信息</h2>
                    </header>
                    <div class="main-box-body clearfix">
                    <#list columns as po>
                        <#if !(po.fieldName=='deleteFlag' || po.fieldName=='createdBy' || po.fieldName=='createdTime' || po.fieldName=='updatedBy' || po.fieldName=='updatedTime')>
                        <#if po_index%2==0>
                        <div class="row">
                        </#if>
                            <div class="form-group col-md-6 has-warning">
                                <label for="maskedDate">${po.content}</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-info"></i></span>
                                    <input type="text" class="form-control" id="${po.fieldName}" name="${po.fieldName}" placeholder="">
                                </div>
                            </div>

                        <#if (po_index%2==0)&&(!po_has_next)>
                            <div class="form-group col-md-6 has-warning">
                            </div>
                        </#if>
                        <#if (po_index%2!=0)||(!po_has_next)>
                        </div>
                        </#if>
                        </#if>
                    </#list>
                    </div>
                    <div class="main-box-footer with-bg clearfix">
                        <div class="row">
                            <div class="form-group col-md-12">
                                <button type="button" class="btn btn-primary btn-custom" v-on:click="saveForm">保存</button>
                                <button type="reset" class="btn btn-custom" v-on:click="gotoList">返回</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <!-- 表单部分结束-->

</div>

<script src="${dollar}{staticResPath}/resources/js/screen/${entityName?uncap_first}/add.js?v=20170822"></script>
</body>