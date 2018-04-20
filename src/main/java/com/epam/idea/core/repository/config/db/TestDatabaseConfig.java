package com.epam.idea.core.repository.config.db;

import javax.sql.DataSource;

import com.epam.idea.core.repository.config.support.DatabaseConfigProfile;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;


@Profile(DatabaseConfigProfile.TEST)
@Configuration
@PropertySource("classpath:/db/test.properties")
public class TestDatabaseConfig extends DatabaseConfig {

	@Value(SCHEMA_SCRIPT_LOCATION)
	private Resource schemaScript;

	@Override
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		final HikariDataSource dataSource = createDataSource();

		DatabasePopulatorUtils.execute(createDatabasePopulator(this.schemaScript), dataSource);
		return dataSource;
	}
}
