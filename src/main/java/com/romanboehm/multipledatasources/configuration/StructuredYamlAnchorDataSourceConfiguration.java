package com.romanboehm.multipledatasources.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("yaml-structured")
@Configuration
@EnableConfigurationProperties
public class StructuredYamlAnchorDataSourceConfiguration {

    @Bean("dataSourceOneProperties")
    @ConfigurationProperties("datasources.datasource-one")
    DataSourceProperties dataSourceOneProperties() {
        return new DataSourceProperties();
    }

    @Bean("dataSourceOne")
    @ConfigurationProperties("datasources.datasource-one.hikari")
    public HikariDataSource dataSourceOne(
            @Qualifier("dataSourceOneProperties") DataSourceProperties dataSourceOneProperties
    ) {
        return dataSourceOneProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean("dataSourceTwoProperties")
    @ConfigurationProperties("datasources.datasource-two")
    DataSourceProperties dataSourceTwoProperties() {
        return new DataSourceProperties();
    }

    @Bean("dataSourceTwo")
    @ConfigurationProperties("datasources.datasource-two.hikari")
    public HikariDataSource dataSourceTwo(
            @Qualifier("dataSourceTwoProperties") DataSourceProperties dataSourceTwoProperties
    ) {
        return dataSourceTwoProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

}
