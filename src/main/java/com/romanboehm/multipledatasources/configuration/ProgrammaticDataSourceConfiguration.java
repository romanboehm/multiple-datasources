package com.romanboehm.multipledatasources.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prog")
@Configuration
@EnableConfigurationProperties
public class ProgrammaticDataSourceConfiguration {

    @Bean("hikariConfigBase")
    @ConfigurationProperties("datasources.hikari-base")
    HikariConfig hikariBaseConfig() {
        return new HikariConfig();
    }

    @Bean("hikariConfigOne")
    @ConfigurationProperties("datasources.datasource-one")
    HikariConfig hikariConfigOne(@Qualifier("hikariConfigBase") HikariConfig hikariConfigBase) {
        HikariConfig compositeConfig = new HikariConfig();
        hikariConfigBase.copyStateTo(compositeConfig);
        return compositeConfig;
    }

    @Bean("dataSourceOne")
    public HikariDataSource dataSourceOne(@Qualifier("hikariConfigOne") HikariConfig hikariConfigOne) {
        return new HikariDataSource(hikariConfigOne);
    }


    @Bean("hikariConfigTwo")
    @ConfigurationProperties("datasources.datasource-two")
    HikariConfig hikariConfigTwo(@Qualifier("hikariConfigBase") HikariConfig hikariConfigBase) {
        HikariConfig compositeConfig = new HikariConfig();
        hikariConfigBase.copyStateTo(compositeConfig);
        return compositeConfig;
    }

    @Bean("dataSourceTwo")
    public HikariDataSource dataSourceTwo(@Qualifier("hikariConfigTwo") HikariConfig hikariConfigTwo) {
        return new HikariDataSource(hikariConfigTwo);
    }

}
