<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign actionExtension = "do">
package ${basepackage}.web.${namespace}.screen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.TurbineRunData;
import com.cfth.core.beanutils.BeanUtils;
import com.cfth.core.web.scope.Flash;

<#include "/java_imports.include">

public class ${className} extends BaseAction{

	@Autowired
	private ${className}Manager ${classNameLower}Manager;
	
	/** 执行搜索 */
	public void doList(TurbineRunData rundata) {
		${className}Query query = newQuery(${className}Query.class, null);
		
		Page page = ${classNameLower}Manager.findPage(query);
		savePage(page,query);
		forword(rundata, "${className}_jsp/list");
	}
	
	/** 查看对象*/
	public void doShow(TurbineRunData rundata) {
		forword(rundata, "${className}_jsp/show");
	}
	
	/** 进入新增页面*/
	public void doCreate(TurbineRunData rundata) {
		forword(rundata, "${className}_jsp/create");
	}
	
	/** 保存新增对象 */
	public void doSave(TurbineRunData rundata) {
		 redirect("${namespace}/${className}/list.do");
	}
	
	/**进入更新页面*/
	public void doEdit(TurbineRunData rundata) {
		forword(rundata, "${className}_jsp/edit");
	}
	
	/**保存更新对象*/
	public void doUpdate(TurbineRunData rundata) {
		redirect("${namespace}/${className}/list.do");
	}
	
	/**删除对象*/
	public void doDelete(TurbineRunData rundata) {
		String[] items = getRequest().getParameterValues("items");
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			<#if table.compositeId>
			${className}Id id = (${className}Id)copyProperties(${className}Id.class,params);
			<#else>
				<#list table.compositeIdColumns as column>
			${column.javaType} id = new ${column.javaType}((String)params.get("${column.columnNameLower}"));
				</#list>
			</#if>
			${classNameLower}Manager.removeById(id);
		}
		redirect("${namespace}/${className}/list.do");
	}

}
