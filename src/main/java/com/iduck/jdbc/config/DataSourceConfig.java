package com.iduck.jdbc.config;

/**
 * 数据源配置参数实体
 *
 * @author songYanBin
 * @since 2022/11/21
 */
public class DataSourceConfig {

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

    @Override
    public String toString() {
        return "DataSourceConfig{" +
                "key='" + key + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", initialSize=" + initialSize +
                ", maxActive=" + maxActive +
                ", minIdle=" + minIdle +
                ", maxWait=" + maxWait +
                ", queryTimeout=" + queryTimeout +
                ", transactionTimeout=" + transactionTimeout +
                '}';
    }
}
