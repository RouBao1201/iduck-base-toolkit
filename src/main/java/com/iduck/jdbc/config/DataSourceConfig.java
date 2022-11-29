package com.iduck.jdbc.config;

/**
 * 数据源配置参数实体
 *
 * @author songYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/21
 */
public class DataSourceConfig {

    private String key;

    private String username;

    private String password;

    private String driverClassName;

    private String url;

    private String type;

    private Integer initialSize;

    private Integer maxActive;

    private Integer minIdle;

    private Integer maxWait;

    private Integer queryTimeout;

    private Integer transactionTimeout;

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

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
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

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
    }

    public Integer getQueryTimeout() {
        return queryTimeout;
    }

    public void setQueryTimeout(Integer queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    public Integer getTransactionTimeout() {
        return transactionTimeout;
    }

    public void setTransactionTimeout(Integer transactionTimeout) {
        this.transactionTimeout = transactionTimeout;
    }

    @Override
    public String toString() {
        return "DataSourceConfig{" +
                "key='" + key + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", driverClassName='" + driverClassName + '\'' +
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
