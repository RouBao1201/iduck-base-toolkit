package com.iduck.jdbc.config;

/**
 * 数据源Key
 * 与JdbcTemplateHolder搭配使用
 *
 * @author songYanBin
 * @since 2022/11/21
 */
public class SourceKey {

    /**
     * 枚举类型配置
     */
    public static class Enums {
        public static final String LOCAL_STORAGE_MYSQL = "LOCAL_STORAGECOMS_MYSQL";

        /**
         * 默认数据源
         */
        public static final String DEFAULT_DATASOURCE = "DEFAULT-DATASOURCE";
    }

    /**
     * 配置文件配置
     */
    public static class Properties {
        /**
         * 默认数据源
         */
        public static final String DEFAULT_DATASOURCE = "DEFAULT-DATASOURCE";

    }

    private SourceKey() {

    }
}
