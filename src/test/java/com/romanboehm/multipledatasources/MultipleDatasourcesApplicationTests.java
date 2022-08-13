package com.romanboehm.multipledatasources;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class MultipleDatasourcesApplicationTests {

	@Container
	static PostgreSQLContainer<?> databaseOne = new PostgreSQLContainer<>("postgres:latest");

	@Container
	static PostgreSQLContainer<?> databaseTwo = new PostgreSQLContainer<>("postgres:latest");

	@DynamicPropertySource
	static void loadDatabaseProperties(DynamicPropertyRegistry registry) {
		registry.add("datasources.datasource-one.url", databaseOne::getJdbcUrl);
		registry.add("datasources.datasource-one.jdbcUrl", databaseOne::getJdbcUrl); // For `HikariConfig`.
		registry.add("datasources.datasource-one.password", databaseOne::getPassword);
		registry.add("datasources.datasource-one.username", databaseOne::getUsername);
		registry.add("datasources.datasource-two.url", databaseTwo::getJdbcUrl);
		registry.add("datasources.datasource-two.jdbcUrl", databaseTwo::getJdbcUrl); // For `HikariConfig`.
		registry.add("datasources.datasource-two.password", databaseTwo::getPassword);
		registry.add("datasources.datasource-two.username", databaseTwo::getUsername);
	}

	@Autowired
	@Qualifier("dataSourceOne")
	private DataSource one;

	@Autowired
	@Qualifier("dataSourceTwo")
	private DataSource two;

	@Test
	void canUseBothDataSources() throws SQLException {
		try (
				var connOne = one.getConnection();
				var connTwo = two.getConnection();
		) {
			boolean oneSucceeded = connOne.createStatement().execute("SELECT 1;");
			boolean twoSucceeded = connTwo.createStatement().execute("SELECT 1;");

			assertThat(oneSucceeded).isTrue();
			assertThat(twoSucceeded).isTrue();
		}
	}

	@Test
	void dataSourceOneConfiguredCorrectly() {
		assertThat(one).isInstanceOfSatisfying(HikariDataSource.class, hikariDataSource -> {
			assertThat(hikariDataSource.getPoolName()).isEqualTo("one");
			assertThat(hikariDataSource.getConnectionTimeout()).isEqualTo(30001);
			assertThat(hikariDataSource.getIdleTimeout()).isEqualTo(600001);
			assertThat(hikariDataSource.getMaxLifetime()).isEqualTo(1800001);
		});
	}

	@Test
	void dataSourceTwoConfiguredCorrectly() {
		assertThat(two).isInstanceOfSatisfying(HikariDataSource.class, hikariDataSource -> {
			assertThat(hikariDataSource.getPoolName()).isEqualTo("two");
			assertThat(hikariDataSource.getConnectionTimeout()).isEqualTo(30001);
			assertThat(hikariDataSource.getIdleTimeout()).isEqualTo(600001);
			assertThat(hikariDataSource.getMaxLifetime()).isEqualTo(1800001);
		});
	}

}
