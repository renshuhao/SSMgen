<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.core.service.impl;


<#include "/java_serviceImpl_imports.include">

@Service("${classNameLower}Service")
public class ${className}ServiceImpl extends BaseServiceImpl implements ${className}Service >{

	@Autowired
	private ${className}MysqlDAO ${classNameLower}MysqlDAO;
	
	
	
}
