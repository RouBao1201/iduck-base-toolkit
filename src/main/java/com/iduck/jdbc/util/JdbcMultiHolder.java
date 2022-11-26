package com.iduck.jdbc.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.iduck.common.util.SpringContextHolder;
import com.iduck.jdbc.config.DataSourceConfig;
import com.iduck.jdbc.config.JdbcEnumConfig;
import com.iduck.jdbc.config.JdbcPropConfig;
import com.iduck.jdbc.config.SourceKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源连接持有工具类【多数据源适用-JdbcTemplate】
 *
 * @author songYanBin
 * @since 2022/11/21
 */
@Slf4j
public class JdbcMultiHolder {
    private static final String ENUMS = "enums";

    private static final String PROPERTIES = "properties";

    /**
     * JdbcTemplate集合【多数据源】
     */
    private static final Map<String, JdbcTemplate> JDBC_MAP;

    private static final JdbcPropConfig JDBC_CONFIG;

    static {
        log.info("JdbcMultiHolder => The multi-datasource initialization starts.");
        JDBC_MAP = new ConcurrentHashMap<>();
        JDBC_CONFIG = SpringContextHolder.getBean(JdbcPropConfig.class);
        init();
    }

    /**
     * 获取默认JdbcTemplate
     *
     * @return JdbcTemplate
     */
    public static JdbcTemplate getDefault() {
        if (PROPERTIES.equals(JDBC_CONFIG.getType())) {
            return JDBC_MAP.get(SourceKey.Properties.DEFAULT_DATASOURCE);
        }
        return JDBC_MAP.get(SourceKey.Enums.DEFAULT_DATASOURCE);
    }

    /**
     * 根据Key【SourceKey类中定义维护】获取JdbcTemplate
     *
     * @param key key
     * @return JdbcTemplate
     */
    public static JdbcTemplate get(String key) {
        return JDBC_MAP.get(key);
    }

    /**
     * 获取所有的JdbcTemplate
     *
     * @return JDBC_MAP
     */
    public static Map<String, JdbcTemplate> getAll() {
        return JDBC_MAP;
    }

    /**
     * 初始化数据连接,创建JdbcTemplate
     */
    private static void init() {
        // 注册默认的JdbcTemplate
        JdbcTemplate bean = SpringContextHolder.getBean(JdbcTemplate.class, true);
        if (!ObjectUtils.isEmpty(bean)) {
            log.info("JdbcMultiHolder ==> Load the Spring configuration datasource as the default.");
            JDBC_MAP.put(SourceKey.Properties.DEFAULT_DATASOURCE, bean);
        } else {
            if (ENUMS.equals(JDBC_CONFIG.getType())) {
                log.info("JdbcMultiHolder ==> Load enum [{}] as default datasource.", JdbcEnumConfig.DEFAULT_DATASOURCE.getKey());
                initJdbcTemplate(transAllToConfig(JdbcEnumConfig.DEFAULT_DATASOURCE));
            } else if (PROPERTIES.equals(JDBC_CONFIG.getType())) {
                log.info("JdbcMultiHolder ==> Load configuration [{}] as default datasource.", SourceKey.Properties.DEFAULT_DATASOURCE);
                initJdbcTemplate(JDBC_CONFIG.getConfig().get(SourceKey.Properties.DEFAULT_DATASOURCE));
            }
        }

        // 注册剩余的数据源连接
        log.info("JdbcMultiHolder ==> Initialize the data source mode to:[{}]", JDBC_CONFIG.getType());
        switch (JDBC_CONFIG.getType()) {
            case ENUMS:
                initByEnums();
                break;
            case PROPERTIES:
                initByProperties();
                break;
            default:
                break;
        }
    }

    /**
     * 创建多数据源连接【by Properties】
     */
    private static void initByProperties() {
        Map<String, DataSourceConfig> configMap = JDBC_CONFIG.getConfig();
        if (CollectionUtil.isEmpty(configMap)) {
            return;
        }
        configMap.forEach((key, config) -> {
            if (!SourceKey.Properties.DEFAULT_DATASOURCE.equals(key)) {
                config.setKey(key);
                initJdbcTemplate(config);
            }
        });
    }

    /**
     * 创建多数据源连接【by Enums】
     */
    private static void initByEnums() {
        JdbcEnumConfig.ALL_LIST.forEach(it -> {
            DataSourceConfig dataSourceConfig = transAllToConfig(it);
            initJdbcTemplate(dataSourceConfig);
        });
    }

    /**
     * 创建JdbcTemplate
     *
     * @param config config
     */
    private static void initJdbcTemplate(DataSourceConfig config) {
        log.info("JdbcMultiHolder ==> Registering datasource:[{}]", config.getKey());
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(getDataSource(config));
        JDBC_MAP.put(config.getKey(), jdbcTemplate);
    }

    /**
     * 将枚举转为DataSourceConfig对象
     *
     * @param jdbcEnum jdbcEnum
     * @return DataSourceConfig
     */
    private static DataSourceConfig transAllToConfig(JdbcEnumConfig jdbcEnum) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setKey(jdbcEnum.getKey());
        dataSourceConfig.setUsername(jdbcEnum.getUsername());
        dataSourceConfig.setPassword(jdbcEnum.getPassword());
        dataSourceConfig.setDriver(jdbcEnum.getDriver());
        dataSourceConfig.setUrl(jdbcEnum.getUrl());
        dataSourceConfig.setType(jdbcEnum.getType());
        dataSourceConfig.setInitialSize(jdbcEnum.getInitialSize());
        dataSourceConfig.setMaxActive(jdbcEnum.getMaxActive());
        dataSourceConfig.setMinIdle(jdbcEnum.getMinIdle());
        dataSourceConfig.setMaxWait(jdbcEnum.getMaxWait());
        dataSourceConfig.setQueryTimeout(jdbcEnum.getQueryTimeout());
        dataSourceConfig.setTransactionTimeout(jdbcEnum.getTransactionTimeout());
        return dataSourceConfig;
    }

    /**
     * 创建数据库连接池【Druid】
     *
     * @param config config
     * @return DruidDataSource
     */
    private static DataSource getDataSource(DataSourceConfig config) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setDriverClassName(config.getDriver());
        datasource.setUrl(config.getUrl());
        datasource.setUsername(config.getUsername());
        datasource.setPassword(config.getPassword());
        datasource.setInitialSize(config.getInitialSize());
        datasource.setMaxActive(config.getMaxActive());
        datasource.setMinIdle(config.getMinIdle());
        datasource.setMaxWait(config.getMaxWait());
        datasource.setQueryTimeout(config.getQueryTimeout());
        datasource.setTransactionQueryTimeout(config.getTransactionTimeout());
        return datasource;
    }

    private JdbcMultiHolder() {

    }
}
