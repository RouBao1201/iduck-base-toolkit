package com.iduck.jdbc.config;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库连接配置枚举
 *
 * @author songYanBin
 * @since 2022/11/21
 */
public enum JdbcEnumConfig {
    DEFAULT_DATASOURCE(
            SourceKey.Enums.DEFAULT_DATASOURCE,
            "root",
            "root",
            "com.mysql.cj.jdbc.Driver",
            "jdbc:mysql://localhost:3306/storagecoms",
            "MYSQL",
            10,
            50,
            10,
            5000,
            100,
            100
    ),

    LOCAL_STORAGE_MYSQL(
            SourceKey.Enums.LOCAL_STORAGE_MYSQL,
            "root",
            "root",
            "com.mysql.cj.jdbc.Driver",
            "jdbc:mysql://localhost:3306/storagecoms",
            "MYSQL",
            10,
            50,
            10,
            5000,
            100,
            100
    );

    private String key;

    private String username;

    private String password;

    private String driver;

    private String url;

    private String type;

    private int initialSize;

    private int maxActive;

    private int minIdle;

    private int maxWait;

    private int queryTimeout;

    private int transactionTimeout;

    private static final String MYSQL = "MYSQL";

    private static final String ORACLE = "ORACLE";

    public static List<JdbcEnumConfig> MYSQL_LIST;

    public static List<JdbcEnumConfig> ORACLE_LIST;

    public static List<JdbcEnumConfig> ALL_LIST;

    static {
        MYSQL_LIST = getDataSourceEnum(MYSQL);
        ORACLE_LIST = getDataSourceEnum(ORACLE);
        ALL_LIST = getDataSourceEnum(null);
    }

    private static List<JdbcEnumConfig> getDataSourceEnum(String type) {
        List<JdbcEnumConfig> sourceList = new ArrayList<>();
        for (JdbcEnumConfig value : JdbcEnumConfig.values()) {
            if (StrUtil.isEmpty(type) && !SourceKey.Enums.DEFAULT_DATASOURCE.equals(value.getKey())) {
                sourceList.add(value);
            } else if (type.equals(value.getType()) && !SourceKey.Enums.DEFAULT_DATASOURCE.equals(value.getKey())) {
                sourceList.add(value);
            }
        }
        return sourceList;
    }

    JdbcEnumConfig(String key, String username, String password, String driver, String url, String type, int initialSize, int maxActive, int minIdle, int maxWait, int queryTimeout, int transactionTimeout) {
        this.key = key;
        this.username = username;
        this.password = password;
        this.driver = driver;
        this.url = url;
        this.type = type;
        this.initialSize = initialSize;
        this.maxActive = maxActive;
        this.minIdle = minIdle;
        this.maxWait = maxWait;
        this.queryTimeout = queryTimeout;
        this.transactionTimeout = transactionTimeout;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getQueryTimeout() {
        return queryTimeout;
    }

    public void setQueryTimeout(int queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    public int getTransactionTimeout() {
        return transactionTimeout;
    }

    public void setTransactionTimeout(int transactionTimeout) {
        this.transactionTimeout = transactionTimeout;
    }

    public static List<JdbcEnumConfig> getMysqlList() {
        return MYSQL_LIST;
    }

    public static void setMysqlList(List<JdbcEnumConfig> mysqlList) {
        MYSQL_LIST = mysqlList;
    }

    public static List<JdbcEnumConfig> getOracleList() {
        return ORACLE_LIST;
    }

    public static void setOracleList(List<JdbcEnumConfig> oracleList) {
        ORACLE_LIST = oracleList;
    }
}
