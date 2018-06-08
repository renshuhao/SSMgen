<%@page import="${basepackage}.model.*" %>
<#include "/macro.include"/> 
<#include "/custom.include"/> 
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<%@ include file="/common/taglibs.jsp" %>
<%
   /**
	* 功能: 
	* 版本: v1.0
	* 日期: 
	* 作者: 
	* 版权: 
	* 修改历史
	* 修改日期      修改人      修改目的
	*/
%>
<rapid:override name="head">
	<title><%=${className}.TABLE_ALIAS%> 维护</title>
	
	<link href="<@jspEl 'ctx'/>/plugins/datatables/jquery.dataTables.css" type="text/css" rel="stylesheet">
</rapid:override>

<rapid:override name="content_header">
    <section class="content-header">
      <h1>
        <%=${className}.TABLE_ALIAS%>
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#" target="ifram" id="work_menu_a"><i class="fa fa-dashboard"></i> 哈哈财神管理平台</a></li>
        <li class="active"><%=${className}.TABLE_ALIAS%></li>
      </ol>
    </section>
</rapid:override>

<rapid:override name="content">
	<div class="box">
    <div class="box-header with-border">
    </div>
    <form id="queryForm" name="queryForm" action="<@jspEl "ctx"/>/${namespace}/${className}/list.do" method="post" class="form-horizontal">
		<div class="box-body">
		
			<#list table.notPkColumns as column>
			<div class="form-group col-sm-6 col-md-4">
	          <label class="col-sm-5 control-label" for="${column.columnNameLower}"><%=${className}.ALIAS_${column.constantName}%>:</label>
	          <div class="col-sm-7">
	          	<#if column.isDateTimeColumn>
					<input value="<@jspEl 'query.'+column.columnNameLower+'String' />" id="${column.columnNameLower}String" name="${column.columnNameLower}" maxlength="${column.size}" class="form-control Wdate" readonly="readonly" onClick="WdatePicker({dateFmt:'<%=${className}.FORMAT_${column.constantName}%>'})"  />
				<#else>
					<input value="<@jspEl 'query.'+column.columnNameLower />" id="${column.columnNameLower}" name="${column.columnNameLower}" maxlength="${column.size}" class="form-control"/>
				</#if>
	          </div>
	        </div>
        	</#list>
        	
        </div>
        
        
        <div class="box-footer text-center">
           <button class="btn btn-primary btn-flat margin" id="submitButton" name="submitButton" type="submit" onclick="getReferenceForm(this).action='<@jspEl 'ctx'/>/${namespace }/${className }/list.do'"><i class="fa fa-search"></i>&nbsp;&nbsp;&nbsp;&nbsp;查询</button>
           <button class="btn btn-default btn-flat margin" onclick="resetForm('queryForm');" type="button"><i class="fa fa-refresh"></i>&nbsp;&nbsp;&nbsp;&nbsp;重置</button>
           <button class="btn btn-success btn-flat margin" type="submit" onclick="getReferenceForm(this).action='<@jspEl 'ctx'/>/${namespace }/${className }/create.do'"><i class="fa fa-plus "></i>&nbsp;&nbsp;&nbsp;&nbsp;新增</button>
           <button class="btn btn-danger btn-flat margin" type="button" onclick="batchDelete('<@jspEl 'ctx'/>/${namespace }/${className }/delete.do','items',document.forms.queryForm)"><i class="fa fa-trash-o "></i>&nbsp;&nbsp;&nbsp;&nbsp;删除</button>
        </div>
        
        
        <div class="nav-tabs-custom">
        	<div class="box-body">
        		<simpletable:pageToolbar page="<@jspEl 'pageList'/>"> </simpletable:pageToolbar>
        		
        		<table border="0" cellspacing="0" class="gridBody display nowrap dataTable">
				  <thead>
				  	<tr>
						<th width="1px"></th>
						<th width="50px">序号</th>
						<#list table.columns as column>
						<#if !column.htmlHidden>
						<th sortColumn="${column.sqlName}" ><%=${className}.ALIAS_${column.constantName}%></th>
						</#if>
						</#list>
			
						<th>操作</th>
					</tr>
				  </thead>
				  
				  <tbody>
				  	  <c:forEach items="<@jspEl 'pageList.result'/>" var="item" varStatus="status">
					  <tr>
					  
					  	<td><input type="checkbox" name="items" value="<@generateIdQueryString/>"></td>
						<td><@jspEl 'pageList.thisPageFirstElementNumber + status.index'/></td>
						<#list table.columns as column>
						<#if !column.htmlHidden>
						<td><#rt>
							<#compress>
							<#if column.isDateTimeColumn>
							<c:out value='<@jspEl "item."+column.columnNameLower+"String"/>'/>&nbsp;
							<#else>
							<c:out value='<@jspEl "item."+column.columnNameLower/>'/>&nbsp;
							</#if>
							</#compress>
						<#lt></td>
						</#if>
						</#list>
						<td>
							<a href="<@jspEl 'ctx'/>/${namespace }/${className }/show.do?<@generateIdQueryString/>">查看</a>&nbsp;&nbsp;&nbsp;
							<a href="<@jspEl 'ctx'/>/${namespace }/${className }/edit.do?<@generateIdQueryString/>">修改</a>
						</td>
							
					  </tr>
					 
					</c:forEach>
					
				 </tbody>
				</table>
        		
        		<simpletable:pageToolbar page="<@jspEl 'pageList'/>"> </simpletable:pageToolbar>
        		
           	</div>
        </div>
        
	</form>
	</div>
	
</rapid:override>

<rapid:override name="footer">
<script type="text/javascript" src="<c:url value="/widgets/simpletable/simpletable.js"/>"></script>

<script type="text/javascript">
	$(document).ready(function() {
		// 分页需要依赖的初始化动作
		window.simpleTable = new SimpleTable('queryForm','<@jspEl 'pageList.thisPageNumber'/>','<@jspEl 'pageList.pageSize'/>','<@jspEl 'pageRequest.sortColumns'/>');
	});


</script>
</rapid:override>

<%@ include file="base.jsp" %>

<#macro generateIdQueryString>
	<#if table.compositeId>
		<#assign itemPrefix = 'item.id.'>
	<#else>
		<#assign itemPrefix = 'item.'>
	</#if>
<#compress>
		<#list table.compositeIdColumns as column>
			<#t>${column.columnNameLower}=<@jspEl itemPrefix + column.columnNameLower/>&
		</#list>				
</#compress>
</#macro>