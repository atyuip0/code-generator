<head>
    <title>${ftl_description}管理</title>
</head>
<body>
<div id="vue-body">
    <!-- 顶部导航部分开始 -->
    <div id="childTopBar" style="display:none;">
        <div class="col-lg-12">
            <ol class="breadcrumb">
                <li><a href="@{staticResPath}/index.html">主页</a></li>
                <li><span>${ftl_description}管理</span></li>
                <li class="active"><span>${ftl_description}管理</span></li>
            </ol>
            <!-- <h1>搜索分析</h1> -->
        </div>
    </div>
    <!-- 顶部导航部分结束 -->

    <!-- 表单部分开始 -->
    <div class="row">
        <div class="col-lg-12">
            <div class="main-box">
                <!--<header class="main-box-header clearfix">
                <h2></h2>
                </header>-->
                <div class="main-box-body clearfix modulecontainer">
                    <!-- 查询表单，请设置id=queryForm，以及返回vo.Result的action -->
                    <form id="queryForm" action="@{staticResPath}/${entityName?uncap_first}/find.ajax"
                          data-enableFlagSwitch-url="@{staticResPath}/${entityName?uncap_first}/enableFlagSwitch.ajax"
                          data-delete-url="@{staticResPath}/${entityName?uncap_first}/delete.ajax">
                        <div class="module moduleSearch clearfix">
                            <div class="row">
                                <div class="item clear">
                                    <div class="form-group">
                                        <label class="control-label col-lg-2" for="dicName">关键词</label>
                                        <div class="input-group col-lg-8">
                                            <div class="input-group-btn">
                                                <button type="button" id="keyword" name="keyword" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">请选择<span class="caret"></span></button>
                                                <ul class="dropdown-menu">
                                                <#list columns as po>
                                                    <li><a href="#" data-menu-word="${po.fieldName}">${po.content}</a></li>
                                                </#list>
                                                </ul>
                                            </div>
                                            <input type="text" class="form-control" data-menu-value="keyvalue" name="keyvalue" id="keyvalue" />
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="row">
                                <div class="item clear">
                                    <div class="form-group">
                                        <label class="control-label col-lg-2"></label>
                                        <button type="button" class="btn btn-primary btn-custom" v-on:click="queryForm">查询</button>
                                        <button class="btn btn-custom" type="reset" id="resetButton">重置</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- 表单部分结束-->

    <!-- 数据表格部分开始 -->
    <div class="row">
        <div class="col-lg-12">
            <div class="main-box clearfix">
                <header class="main-box-header clearfix">
                    <h2 class="pull-right">
					<permit.oauth hasPermission="${entityName?uncap_first}Manage:add">
                        <a href="@{staticResPath}/${entityName?uncap_first}/add.html" class="btn btn-default pull-left">
                            <i class="fa fa-plus-circle"></i>
                            <span>新建</span>
                        </a>
					</permit.oauth>
                    </h2>
                </header>

                <div class="main-box-body clearfix">
                    <div class="table-responsive">
                        <table id="table-example-fixed" class="table table-striped table-hover table-bordered">
                            <thead>
                            <tr>
                                <th class="text-center">序号</th>
                            <#list columns as po>
                                <#if  po.fieldName!='deleteFlag'&& po.isPk=='N'>
                                <th class="text-center">${po.content}</th>
                                </#if>
                            </#list>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="(index,item) in recordList">

                                <td class="text-center">{{pageSize*(currentPage-1)+index+1}}</td>
                            <#list columns as po>
                                <#if po.fieldName!='deleteFlag'&& po.isPk!='Y'>
                                <#if po.fieldName!='enableFlag'>
                                    <#if po.type=='java.util.Date'>
                                        <td class="text-center">{{item.${po.fieldName}|formatDateTime}}</td>
                                    <#else>
                                <td class="text-center">{{item.${po.fieldName}}}</td>
                                     </#if>
                                <#else>
                                <td class="text-center">
                                    <div class="onoffswitch" 
									<permit.oauth hasPermission="${entityName?uncap_first}Manage:edit">
									v-on:click="enableFlagSwitch(this)"
									</permit.oauth>
									>
                                        <input type="checkbox" class="onoffswitch-checkbox" name="onoffswitch{{index}}" id="myonoffswitch{{index}}"
                                               v-bind:checked="item.enableFlag==1" >
                                        <label class="onoffswitch-label">
                                            <div class="onoffswitch-inner"></div>
                                            <div class="onoffswitch-switch"></div>
                                        </label>
                                    </div>
                                </td>
                                </#if>
                                </#if>
                            </#list>
                                <td class="text-center">
									<permit.oauth hasPermission="${entityName?uncap_first}Manage:edit">
                                    <a href="@{staticResPath}/${entityName?uncap_first}/edit/{{item.${pk}}}.html" class="table-link" >
											<span class="fa-stack">
												<i class="fa fa-square fa-stack-2x"></i>
												<i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
											</span>
                                    </a>
									</permit.oauth>
									<permit.oauth hasPermission="${entityName?uncap_first}Manage:delete">
                                    <a href="#" class="table-link danger" v-on:click="deleteForm(this)">
											<span class="fa-stack">
												<i class="fa fa-square fa-stack-2x"></i>
												<i class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
											</span>
                                    </a>
									</permit.oauth>
                                </td>

                            </tr>
                            </tbody>
                        </table>

                        <!-- 数据表格分页开始-->
                        <div>
                        <include '../../common/pagination.ftl'/>
                        </div>
                        <!-- 数据表格分页结束-->
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="@{staticResPath}/resources/js/screen/${entityName?uncap_first}/find.js?v=20170822"></script>
</body>