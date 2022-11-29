package com.iduck.jdbc.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.iduck.common.constant.NumberConst;
import com.iduck.common.util.SpringContextHolder;
import com.iduck.exception.model.BaseException;
import com.iduck.exception.util.ExceptionHandler;
import com.iduck.jdbc.config.DataSourceConfig;
import com.iduck.jdbc.config.JdbcSourcePropConfig;
import com.iduck.jdbc.service.MultiDatasourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class JdbcMultiHolder {
    private static final Logger log = LoggerFactory.getLogger(JdbcMultiHolder.class);

    private static final String SERVICE_IMPL = "service";

    private static final String PROPERTIES = "properties";

    private static final String DEFAULT_DATASOURCE = "DEFAULT-DATASOURCE";

    /**
     * JdbcTemplate集合【多数据源】
     */
    private static final Map<String, JdbcTemplate> JDBC_MAP;

    private static final JdbcSourcePropConfig JDBC_CONFIG;

    static {
        log.info("JdbcMultiHolder => The multi-datasource initialization starts.");
        JDBC_MAP = new ConcurrentHashMap<>();
        JDBC_CONFIG = SpringContextHolder.getBean(JdbcSourcePropConfig.class);
        init();
    }

    /**
     * 获取默认JdbcTemplate
     *
     * @return JdbcTemplate
     */
    public static JdbcTemplate getDefault() {
        return JDBC_MAP.get(DEFAULT_DATASOURCE);
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
        JdbcTemplate bean = SpringContextHolder.getBean(JdbcTemplate.class);
        if (!ObjectUtils.isEmpty(bean)) {
            log.info("JdbcMultiHolder ==> Load the Spring configuration datasource as the default.");
            JDBC_MAP.put(DEFAULT_DATASOURCE, bean);
        }

        // 注册剩余的数据源连接
        log.info("JdbcMultiHolder ==> Initialize the data source mode to:[{}]", JDBC_CONFIG.getType());
        switch (JDBC_CONFIG.getType()) {
            case SERVICE_IMPL:
                initByServiceImpl();
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
            config.setKey(key);
            initJdbcTemplate(config);
        });
    }

    /**
     * 创建多数据源连接【by Bean】
     */
    private static void initByServiceImpl() {
        MultiDatasourceService bean = SpringContextHolder.getBean(MultiDatasourceService.class);
        bean.getDatasourceConfig().forEach(JdbcMultiHolder::initJdbcTemplate);
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
     * 创建数据库连接池【Druid】
     *
     * @param config config
     * @return DruidDataSource
     */
    private static DataSource getDataSource(DataSourceConfig config) {
        DruidDataSource datasource = new DruidDataSource();
        if (ObjUtil.isEmpty(config)
                || ObjUtil.isEmpty(config.getKey())
                || ObjUtil.isEmpty(config.getDriverClassName())
                || ObjUtil.isEmpty(config.getUrl())
                || ObjUtil.isEmpty(config.getUsername())
                || ObjUtil.isEmpty(config.getPassword())) {
            ExceptionHandler.pushExc("500", "Datasouece init error.", BaseException.class);
        }
        datasource.setDriverClassName(config.getDriverClassName());
        datasource.setUrl(config.getUrl());
        datasource.setUsername(config.getUsername());
        datasource.setPassword(config.getPassword());
        datasource.setInitialSize(ObjUtil.isEmpty(config.getInitialSize()) ? NumberConst.FIVE : config.getInitialSize());
        datasource.setMaxActive(ObjUtil.isEmpty(config.getMaxActive()) ? NumberConst.TEN : config.getMaxActive());
        datasource.setMinIdle(ObjUtil.isEmpty(config.getMinIdle()) ? NumberConst.FIVE : config.getMinIdle());
        datasource.setMaxWait(ObjUtil.isEmpty(config.getMaxWait()) ? NumberConst.SIXTY_THOUSAND : config.getMaxWait());
        datasource.setQueryTimeout(ObjUtil.isEmpty(config.getQueryTimeout()) ? NumberConst.ONE_THOUSAND : config.getQueryTimeout());
        datasource.setTransactionQueryTimeout(ObjUtil.isEmpty(config.getTransactionTimeout()) ? NumberConst.ONE_THOUSAND : config.getTransactionTimeout());
        return datasource;
    }

    private JdbcMultiHolder() {

    }
}
