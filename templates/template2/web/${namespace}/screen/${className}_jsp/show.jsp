<%@page import="${basepackage}.model.*" %>
<#include "/macro.include"/> 
<#include "/custom.include"/> 
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign actionExtension = "do"> 
<%@ page contentType="text/html;charset=UTF-8" %>
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
	<title><%=${className}.TABLE_ALIAS%>信息查看</title>
</rapid:override>

<rapid:override name="content_header">
    <section class="content-header">
      <h1>
       	<%=${className}.TABLE_ALIAS%>
        <small>信息查看</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#" target="ifram" id="work_menu_a"><i class="fa fa-dashboard"></i> 哈哈财神管理平台</a></li>
        <li class="active"><%=${className}.TABLE_ALIAS%>信息查看</li>
      </ol>
    </section>
</rapid:override>

<rapid:override name="content">
	<div class="box">
	<form id="query_form" name="query_form" action="<@jspEl 'ctx'/>/${namespace }/${className }/save.do" method="post" class="form-horizontal">
		
		<div class="box-body">
				<table class="table table-bordered input">
					<tbody>
					<#list table.notPkColumns?chunk(3) as row>
					<tr>	
						<#list row as column>
						<#if !column.htmlHidden>	
						<th >
							<%=${className}.ALIAS_${column.constantName}%>:
						</th>		
						<td>
							<#rt>
							<#compress>
							<#if column.isDateTimeColumn>
								<@jspEl 'model.${column.columnNameLower}String'/>
							<#else>
								<@jspEl 'model.${column.columnNameLower}'/>
							</#if>
							</#compress>
							<#lt>
						</td>
						</#if>
						</#list>
					</tr>	
					</#list>
				</tbody>
			</table>
			
		</div>
		<div class="box-footer text-center">
           <button class="btn btn-default btn-flat margin" onclick="history.back();" type="button">返回</button>
        </div>
	</form>
	</div>
</rapid:override>

<rapid:override name="footer">

</rapid:override>

<%@ include file="base.jsp" %>