package com.wealthlake.generator;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;

/**
 * Created By Rsh
 *
 * @author rsh
 * @Description
 * @Date: 2018/6/7
 * @Time: 9:30
 */
public class GeneratorMain {

    /**
     * 请直接修改以下代码调用不同的方法以执行相关生成任务.
     */
    public static void main(String[] args) throws Exception {
        GeneratorFacade g = new GeneratorFacade();
        g.getGenerator().setTemplateRootDir("se_template");

//		g.printAllTableNames();				//打印数据库中的表名称

        g.deleteOutRootDir();
//		g.generateByTable("region", "se_template");//删除生成器的输出目录
        g.generateByAllTable();    //通过数据库表生成文件,template为模板的根目录
//		g.generateByAllTable("template");	//自动搜索数据库中的所有表并生成文件,template为模板的根目录
//		g.generateByClass(Blog.class,"template_clazz");
//		g.deleteByTable("table_name", "template"); //删除生成的文件
        //打开文件夹
        Runtime.getRuntime().exec("cmd.exe /c start " + GeneratorProperties.getRequiredProperty("outRoot"));
    }

}
