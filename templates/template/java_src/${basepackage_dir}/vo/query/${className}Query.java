<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.vo.query;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

<#include "/java_imports.include">

public class ${className}Query extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    
	<@generateFields/>
	<@generateProperties/>

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

<#macro generateFields>

	<#list table.columns as column>
	/** ${column.columnAlias} */
	<#if column.isDateTimeColumn>
		private ${column.javaType} ${column.columnNameLower}Begin;
		private ${column.javaType} ${column.columnNameLower}End;
	<#else>
		private ${column.javaType} ${column.columnNameLower};
	</#if>
	</#list>

</#macro>

<#macro generateProperties>
	<#list table.columns as column>
	<#if column.isDateTimeColumn>
		public ${column.javaType} get${column.columnName}Begin() {
			return this.${column.columnNameLower}Begin;
		}
		
		public void set${column.columnName}Begin(${column.javaType} value) {
			this.${column.columnNameLower}Begin = value;
		}	
		
		public String get${column.columnName}BeginString() {
			return DateConvertUtils.format(get${column.columnName}Begin(), "yyyy-MM-dd");
		}
		public void set${column.columnName}BeginString(String value) {
			set${column.columnName}Begin(DateConvertUtils.parse(value, "yyyy-MM-dd", ${column.javaType}.class));
		}
		
		public ${column.javaType} get${column.columnName}End() {
			return this.${column.columnNameLower}End;
		}
		
		public void set${column.columnName}End(${column.javaType} value) {
			this.${column.columnNameLower}End = value;
		}
		
		public String get${column.columnName}EndString() {
			return DateConvertUtils.format(get${column.columnName}End(), "yyyy-MM-dd");
		}
		public void set${column.columnName}EndString(String value) {
			set${column.columnName}End(DateConvertUtils.parse(value, "yyyy-MM-dd", ${column.javaType}.class));
		}
	
	<#else>
		public ${column.javaType} get${column.columnName}() {
			return this.${column.columnNameLower};
		}
		
		public void set${column.columnName}(${column.javaType} value) {
			this.${column.columnNameLower} = value;
		}
	
	</#if>	
	</#list>
</#macro>

