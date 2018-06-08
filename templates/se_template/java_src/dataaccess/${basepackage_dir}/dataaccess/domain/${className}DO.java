<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.dataaccess.domain;

<#include "/java_DO_imports.include">

public class ${className}DO extends BaseDO {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "${table.tableAlias}";
	<#list table.columns as column>
	public static final String ALIAS_${column.constantName} = "${column.columnAlias}";
	</#list>
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	<#list table.columns as column>
    /**
     * ${column.columnAlias!}       db_column: ${column.sqlName} 
     */	
	private ${column.javaType} ${column.columnNameLower};
	</#list>
	//columns END

	public ${className}DO() {
		
	}
	
	public ${className}DO(${table.idColumn.javaType} ${table.idColumn}) {
		this.${table.idColumn} = ${table.idColumn};
	}

<@generateJavaColumns/>
<@generateJavaOneToMany/>
<@generateJavaManyToOne/>
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
		<#list table.columns as column>
			.append("${column.columnName}",get${column.columnName}())
		</#list>
			.toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		<#list table.pkColumns as column>
			.append(get${column.columnName}())
		</#list>
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ${className}DO == false) {
			return false;
		}
		if(this == obj){
			return true;
		}
		${className}DO other = (${className}DO)obj;
		return new EqualsBuilder()
			<#list table.pkColumns as column>
			.append(get${column.columnName}(),other.get${column.columnName}())
			</#list>
			.isEquals();
	}
}

<#macro generateJavaColumns>
	<#list table.columns as column>
	<#if column.isDateTimeColumn>
	public String get${column.columnName}String() {
		if (get${column.columnName}()!=null) {
			return DateUtil.formatDate(get${column.columnName}(), DateUtil.LONG_DATE_FORMAT_STR);
		}
		else {
			return null;
		}
	}
	public void set${column.columnName}String(String value) {
		if (value!=null) {
			set${column.columnName}(DateUtil.parse(value, DateUtil.LONG_DATE_FORMAT_STR));
		}
	}

	</#if>	
	public void set${column.columnName}(${column.javaType} value) {
		this.${column.columnNameLower} = value;
	}
	
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	</#list>
</#macro>

<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private Set ${fkPojoClassVar}DOs = new HashSet(0);
	public void set${fkPojoClass}DOs(Set<${fkPojoClass}DO> ${fkPojoClassVar}DO){
		this.${fkPojoClassVar}DOs = ${fkPojoClassVar}DO;
	}
	
	public Set<${fkPojoClass}DO> get${fkPojoClass}DOs() {
		return ${fkPojoClassVar}DOs;
	}
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private ${fkPojoClass}DO ${fkPojoClassVar}DO;
	
	public void set${fkPojoClass}DO(${fkPojoClass}DO ${fkPojoClassVar}DO){
		this.${fkPojoClassVar}DO = ${fkPojoClassVar}DO;
	}
	
	public ${fkPojoClass}DO get${fkPojoClass}DO() {
		return ${fkPojoClassVar}DO;
	}
	</#list>
</#macro>
