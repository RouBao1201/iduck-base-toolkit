package com.iduck.jdbc.service;

import com.iduck.jdbc.config.DataSourceConfig;

import java.util.List;

/**
 * 多数据源配置接口（提供重写,注入bean）
 *
 * @author SongYanBin
 * @since 2022/11/29
 **/
public interface MultiDatasourceService {
    /**
     * 获取数据库配置接口
     *
     * @return DataSourceConfig集合
     */
    List<DataSourceConfig> getDatasourceConfig();
}
