package com.wealthlake.generator;


/**
 * 配置类
 */
public class Settings {
    private Settings() {
    }

    private static final Settings single = new Settings();

    public static Settings getInstance() {
        return single;
    }

    // 基础包名
    private String basepackage;
    // 命名空间
    private String namespace;
    // 输出目录
    private String outRoot;
    // 需要移除的表名前缀,使用逗号进行分隔多个前缀,示例值: t_,v_
    private String tableRemovePrefixes;
    // 数据库类型，1：Mysql，2：Oracle，3：SQLServer2000，4：SQLServer2005，5：JTDs for SQLServer ，6：PostgreSql，7：Sybase，8：DB2，9：HsqlDB，10：Derby，11：H2
    private int dbType;
    // 数据库连接地址
    private String jdbcUrl;
    // 数据库连接驱动
    private String jdbcDriver;
    // 数据库连接用户名
    private String jdbcUsername;
    // 数据库连接密码
    private String jdbcPassword;
    // 数据表，为空生成全部数据表
    private String table;
    // 模板
    private String template;

    public static Settings getSingle() {
        return single;
    }

    public String getBasepackage() {
        return basepackage;
    }

    public void setBasepackage(String basepackage) {
        this.basepackage = basepackage;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getOutRoot() {
        return outRoot;
    }

    public void setOutRoot(String outRoot) {
        this.outRoot = outRoot;
    }

    public String getTableRemovePrefixes() {
        return tableRemovePrefixes;
    }

    public void setTableRemovePrefixes(String tableRemovePrefixes) {
        this.tableRemovePrefixes = tableRemovePrefixes;
    }

    public int getDbType() {
        return dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
