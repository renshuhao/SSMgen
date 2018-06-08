<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dataaccess.dao.mysql;

<#include "/java_mysqlDAO_imports.include">

public interface ${className}MysqlDAO extends BaseMysqlDAO<${className}DO> {
	

}
