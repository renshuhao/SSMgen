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
	<title><%=${className}.TABLE_ALIAS%>新增</title>
</rapid:override>

<rapid:override name="content_header">
    <section class="content-header">
      <h1>
       	<%=${className}.TABLE_ALIAS%>
        <small>新增</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#" target="ifram" id="work_menu_a"><i class="fa fa-dashboard"></i> 哈哈财神管理平台</a></li>
        <li class="active"><%=${className}.TABLE_ALIAS%>新增</li>
      </ol>
    </section>
</rapid:override>

<rapid:override name="content">
	<div class="box">
	<div class="box-header with-border">
    </div>
	<form id="query_form" name="query_form" action="<@jspEl 'ctx'/>/${namespace }/${className }/save.do" method="post" class="form-horizontal">
	
		<div class="box-body">
				<%@include file="form_include.jsp" %>
					
		</div>
		<div class="box-footer text-center">
           <button class="btn btn-info btn-flat margin" id="submitButton" name="submitButton" type="submit">提交</button>
           <button class="btn btn-default btn-flat margin" onclick="history.back();" type="button">返回</button>
        </div>
	</form>
	</div>

</rapid:override>

<rapid:override name="footer">
<script type="text/javascript">
	<#list table.notPkColumns as column>
	<#if column.isDateTimeColumn>
		$('#${column.columnNameLower}').datepicker({
			format: '<%=${className}.FORMAT_${column.constantName}%>'
		});
	<#else>
	</#if>
	</#list>

	$(function(){
		validateForm();
	});

	function validateForm(){
		return $("#query_form").validate({
	        submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form   
	            //alert("提交表单");   
	            form.submit();   //提交表单   
	        },   
	        rules:{
	        	<#list table.notPkColumns as column>
	        		<#if column_index!=0>,</#if>
	        		${column.columnNameLower}:{
		                required:true
		            }
	        	</#list>
	        },
	        messages:{
	        	<#list table.notPkColumns as column>
	        	<#if column_index!=0>,</#if>
	        		${column.columnNameLower}:{
	        			 required:"必填"
		            }
        		</#list>
	        }
	    });
	}
		
</script>
</rapid:override>

<%@ include file="base.jsp" %>