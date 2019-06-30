package com.sample.paypal.setting.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperty {
    private String driverClassName;
    private String userName;
    private String password;
    private int maxPool;
    private int minIdle;
    private String poolName;
    private long maxLifeTime;
    private long idleTimeout;
    private long connectionTimeout;
    private String masterJdbcUrl;
    private String slaveJdbcUrl;
    private String ConnectionTestQuery;
}
