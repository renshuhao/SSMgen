<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.core.service.impl;


<#include "/java_serviceImpl_imports.include">

@Service("${classNameLower}Service")
public class ${className}ServiceImpl extends BaseServiceImpl implements ${className}Service {

	@Autowired
	private ${className}MysqlDAO ${classNameLower}MysqlDAO;
	
	public Integer save(${className}DO ${classNameLower}DO){
		if(${classNameLower}DO == null) 
			return 0;
		return ${classNameLower}MysqlDAO.save(${classNameLower}DO);
	}

	public int update(${className}DO ${classNameLower}DO){
		if(${classNameLower}DO == null) 
			return 0;
		return ${classNameLower}MysqlDAO.update(${classNameLower}DO);
	}

	public void delete(Integer id){
		${classNameLower}MysqlDAO.delete(id);
	}

	public ${className}DO findById(Integer id){
		return ${classNameLower}MysqlDAO.findById(id);
	}
	
	public List<${className}DO> findAll(List<FilterRule> filterRules){
		return ${classNameLower}MysqlDAO.findAll(filterRules);
	}

	public void deleteAll(List<FilterRule> filterRules){
		${classNameLower}MysqlDAO.deleteAll(filterRules);
	}

	public List<${className}DO> findByPage(List<FilterRule> filterRules, PageQuery pageQuery){
		return ${classNameLower}MysqlDAO.findByPage(filterRules, pageQuery);
	}

	public Integer getTotalCount(List<FilterRule> filterRules){
		return ${classNameLower}MysqlDAO.getTotalCount(filterRules);
	}
	
	
}
