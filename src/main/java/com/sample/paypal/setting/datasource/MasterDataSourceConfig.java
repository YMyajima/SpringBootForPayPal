package com.sample.paypal.setting.datasource;

import com.sample.paypal.component.Encryptor;
import com.sample.paypal.setting.property.DataSourceProperty;
import com.sample.paypal.setting.property.EncryptProperty;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;


@Configuration
@MapperScan(
        basePackages="com.sample.paypal.**.mapper.master",
        sqlSessionTemplateRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {
    private static final String RESOURCES_PATTERN = "classpath:mapper/master/*.xml";

    private static final String MYBATIS_CONFIG_PATH = "/mybatis-config.xml";

    @Autowired
    private DataSourceProperty dataSourceProperty;

    @Autowired
    private Encryptor encryptor;

    @Autowired
    private EncryptProperty encryptProperty;

    @Primary
    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionTemplate sqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG_PATH));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(RESOURCES_PATTERN));
        return new SqlSessionTemplate(bean.getObject());
    }

    @Primary
    @Bean(name = "masterDataSource")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        String userName = encryptor.decrypt(
                dataSourceProperty.getUserName(),
                encryptProperty.getDatabaseConnectionEncrypt()
        );
        String password = encryptor.decrypt(
                dataSourceProperty.getPassword(),
                encryptProperty.getDatabaseConnectionEncrypt()
        );

        config.setUsername(userName);
        config.setPassword(password);
        config.setJdbcUrl(dataSourceProperty.getMasterJdbcUrl());
        config.setDriverClassName(dataSourceProperty.getDriverClassName());
        config.setMaximumPoolSize(dataSourceProperty.getMaxPool());
        config.setMinimumIdle(dataSourceProperty.getMinIdle());
        config.setPoolName(dataSourceProperty.getPoolName());
        config.setMaxLifetime(MINUTES.toMillis(dataSourceProperty.getMaxLifeTime()));
        config.setIdleTimeout(MINUTES.toMillis(dataSourceProperty.getIdleTimeout()));
        config.setConnectionTimeout(SECONDS.toMillis(dataSourceProperty.getConnectionTimeout()));
        config.setConnectionTestQuery(dataSourceProperty.getConnectionTestQuery());
        config.setAutoCommit(false);
        return new HikariDataSource(config);
    }
}
