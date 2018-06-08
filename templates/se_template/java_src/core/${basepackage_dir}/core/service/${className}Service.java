<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.core.service;

<#include "/java_service_imports.include">

public interface ${className}Service extends BaseService {
	
	/**
	 * 新增一条数据
	 * 
	 * @param t
	 * @return
	 */
	Integer save(${className}DO ${classNameLower}DO);

	/**
	 * 更新一条数据
	 * 
	 * @param t
	 * @return
	 */
	int update(${className}DO ${classNameLower}DO);

	/**
	 * 删除一条数据
	 * 
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 根据id查找一条数据
	 * 
	 * @param id
	 * @return
	 */
	${className}DO findById(Integer id);

	/**
	 * 查找所有
	 * 
	 * @return
	 */
	List<${className}DO> findAll(List<FilterRule> filterRules);

	/**
	 * 删除所有
	 * 
	 * @return
	 */
	void deleteAll(List<FilterRule> filterRules);

	/**
	 * 分页查找
	 * 
	 * @param filterRules
	 * @param pageQuery
	 * @return
	 */
	List<${className}DO> findByPage(List<FilterRule> filterRules, PageQuery pageQuery);

	/**
	 * 获取个数
	 * 
	 * @param filterRules
	 * @return
	 */
	Integer getTotalCount(List<FilterRule> filterRules);
	

}
