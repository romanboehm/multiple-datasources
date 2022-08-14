package com.romanboehm.multipledatasources.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("yaml-simple")
@Configuration
@EnableConfigurationProperties
public class SimpleYamlAnchorDataSourceConfiguration {

    @Bean("dataSourceOneProperties")
    @ConfigurationProperties("datasources.datasource-one")
    HikariConfig dataSourceOneProperties() {
        return new HikariConfig();
    }

    @Bean("dataSourceOne")
    public HikariDataSource dataSourceOne(
            @Qualifier("dataSourceOneProperties") HikariConfig dataSourceOneProperties
    ) {
        return new HikariDataSource(dataSourceOneProperties);
    }

    @Bean("dataSourceTwoProperties")
    @ConfigurationProperties("datasources.datasource-two")
    HikariConfig dataSourceTwoProperties() {
        return new HikariConfig();
    }

    @Bean("dataSourceTwo")
    public HikariDataSource dataSourceTwo(
            @Qualifier("dataSourceTwoProperties") HikariConfig dataSourceTwoProperties
    ) {
        return new HikariDataSource(dataSourceTwoProperties);
    }

}
