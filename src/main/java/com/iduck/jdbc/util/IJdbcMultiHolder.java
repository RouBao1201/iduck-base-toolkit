package com.iduck.jdbc.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.iduck.common.constant.NumberConst;
import com.iduck.common.util.ISpringContextHolder;
import com.iduck.exception.model.BaseException;
import com.iduck.exception.util.IExceptionHandler;
import com.iduck.jdbc.config.DataSourceConfig;
import com.iduck.jdbc.config.MultiSourcePropConfig;
import com.iduck.jdbc.service.MultiDatasourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源连接持有工具类【多数据源适用-JdbcTemplate】
 *
 * @author songYanBin
 * @since 2022/11/21
 */
@Component
public class IJdbcMultiHolder {
    private static final Logger log = LoggerFactory.getLogger(IJdbcMultiHolder.class);

    private static final String SERVICE_IMPL = "service";

    private static final String PROPERTIES = "properties";

    private static final String DEFAULT_DATASOURCE = "DEFAULT-DATASOURCE";

    /**
     * JdbcTemplate集合【多数据源】
     */
    private static final Map<String, JdbcTemplate> JDBC_MAP = new ConcurrentHashMap<>();

    private static IJdbcMultiHolder jdbcMultiHolder;

    @Autowired
    private JdbcTemplate defaultJdbcTemplate;

    @Autowired
    private MultiSourcePropConfig config;

    @PostConstruct
    public void init() {
        log.info("JdbcMultiHolder => PostConstruct init...");
        jdbcMultiHolder = this;
        jdbcMultiHolder.defaultJdbcTemplate = this.defaultJdbcTemplate;
        jdbcMultiHolder.config = this.config;
        initTemplate();
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
    private void initTemplate() {
        // 注册默认的JdbcTemplate
        JDBC_MAP.put(DEFAULT_DATASOURCE, jdbcMultiHolder.defaultJdbcTemplate);

        // 注册剩余的数据源连接
        log.info("JdbcMultiHolder ==> Initialize the data source mode to:[{}]", jdbcMultiHolder.config.getType());
        switch (jdbcMultiHolder.config.getType()) {
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
        Map<String, DataSourceConfig> configMap = jdbcMultiHolder.config.getConfig();
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
        MultiDatasourceService bean = ISpringContextHolder.getBean(MultiDatasourceService.class, true);

        // 判断用户是否实现MultiDatasourceService接口
        if (ObjUtil.isNull(bean)) {
            log.error("JdbcMultiHolder =>Init Multi datasource[type:service] fail. If not implements interface[MultiDatasourceService]. Please change config[multi.datasource.type] to 'properties'.");
            return;
        }
        bean.getDatasourceConfig().forEach(IJdbcMultiHolder::initJdbcTemplate);
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
        if (ObjUtil.isEmpty(config) || ObjUtil.isEmpty(config.getKey())
                || ObjUtil.isEmpty(config.getDriverClassName()) || ObjUtil.isEmpty(config.getUrl())
                || ObjUtil.isEmpty(config.getUsername()) || ObjUtil.isEmpty(config.getPassword())) {
            IExceptionHandler.pushExc("500", "Datasource init error.", BaseException.class);
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

    private IJdbcMultiHolder() {

    }
}
