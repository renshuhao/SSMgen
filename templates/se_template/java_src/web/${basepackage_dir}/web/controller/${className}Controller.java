<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign actionExtension = "do">

package ${basepackage}.web.controller;

<#include "/java_controller_imports.include">

@RequestMapping("/${classNameLower}")
@Controller
public class ${className}Controller extends BaseController{

	@Autowired
	private ${className}Service ${classNameLower}Service;
	
	
	@RequestMapping(value = "/")
	public String root(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "redirect:/${classNameLower}/index";
	}

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "/${classNameLower}/index";
	}
	
	@RequestMapping(value = "/show")
	public String show(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "/${classNameLower}/show";
	}
	
	@RequestMapping(value = "/create")
	public String create(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "/${classNameLower}/create";
	}
	
	@RequestMapping(value = "/save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model) {
		
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "/${classNameLower}/edit";
	}
	
	@RequestMapping(value = "/update")
	public void update(HttpServletRequest request, HttpServletResponse response, Model model) {
		
	}
	
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response, Model model) {
		String[] items = request.getParameterValues("items");
		if(items!=null && items.length>0){
			StringBuffer ids = new StringBuffer();
			for(int i = 0; i < items.length; i++) {
				if(items[i]!=null){
					if(ids.length()!=0)
						ids.append(",");
					ids.append("'"+items[i]+"'");
				}
			}
			if(ids.length()!=0){
				List<FilterRule> filterRules = FilterRuleBuilder.newBuilder().key("id").in().value(ids.toString()).build();
				${classNameLower}Service.deleteAll(filterRules);
			}
		}
		return "redirect:/${classNameLower}/index";
	}


}
