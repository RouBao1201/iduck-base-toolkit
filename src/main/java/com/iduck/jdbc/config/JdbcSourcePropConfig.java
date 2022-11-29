package com.iduck.jdbc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author songYanBin
 * @since 2022/11/21
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "multi.datasource")
public class JdbcSourcePropConfig {

    @Value("${multi.datasource.type:properties}")
    private String type;

    private Map<String, DataSourceConfig> config;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, DataSourceConfig> getConfig() {
        return config;
    }

    public void setConfig(Map<String, DataSourceConfig> config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "JdbcConfig{" +
                "type='" + type + '\'' +
                ", config=" + config +
                '}';
    }
}
