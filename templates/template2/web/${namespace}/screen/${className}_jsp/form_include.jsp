<%@page import="${basepackage}.model.*" %>
<#include "/macro.include"/> 
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<#list table.columns as column>
<#if column.htmlHidden>
	<input type="hidden" id="${column.columnNameLower}" name="${column.columnNameLower}" value="<@jspEl 'model.'+column.columnNameLower/>">
</#if>
</#list>

<!-- ONGL access static field: @package.class@field or @vs@field -->
<#list table.notPkColumns as column>

<div class="form-group col-sm-4">
  <label class="col-sm-5 control-label" for="${column.columnNameLower}String">
  	<#if !column.nullable><span class="required red">*</span></#if><%=${className}.ALIAS_${column.constantName}%>:
  </label>
  <div class="col-sm-7">
  	<#if column.isDateTimeColumn>
		<input value="<@jspEl 'model.'+column.columnNameLower+'String'/>" id="${column.columnNameLower}String" name="${column.columnNameLower}" class="form-control" />
	<#else>
		<input value="<@jspEl 'model.'+column.columnNameLower/>" id="${column.columnNameLower}String" name="${column.columnNameLower}" maxlength="${column.size}" class="form-control" />
	</#if>
  </div>
</div>

</#list>