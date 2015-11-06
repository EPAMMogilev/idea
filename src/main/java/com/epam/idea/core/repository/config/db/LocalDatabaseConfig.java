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

@Profile(DatabaseConfigProfile.LOCAL)
@Configuration
@PropertySource("classpath:/db/local.properties")
public class LocalDatabaseConfig extends DatabaseConfig {

	@Value(SCHEMA_SCRIPT_LOCATION_PROD)
	private Resource schemaScript;

	@Value(DATA_SCRIPT_LOCATION_PROD)
	private Resource dataScript;

	@Override
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {

		final HikariDataSource dataSource = createDataSource();
		
		try {
			dataSource.setMaximumPoolSize(Integer.getInteger(this.env.getRequiredProperty("database.connection.pool_size")));
		} catch (Exception e){
			dataSource.setMaximumPoolSize(1000);
		}
		//DatabasePopulatorUtils.execute(createDatabasePopulator(this.schemaScript, this.dataScript), dataSource);
		return dataSource;
	}
}
