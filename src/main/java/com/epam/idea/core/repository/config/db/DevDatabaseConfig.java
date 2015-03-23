package com.epam.idea.core.repository.config.db;

import com.epam.idea.core.repository.config.support.DatabaseConfigProfile;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

@Profile(DatabaseConfigProfile.INTEGRATION_TEST)
@Configuration
@PropertySource("classpath:/db/development.properties")
public class DevDatabaseConfig implements DatabaseConfig {
	
	@Autowired
	private Environment env;
	
	@Override
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		final HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDriverClassName(env.getRequiredProperty("database.driver"));
		dataSourceConfig.setJdbcUrl(env.getRequiredProperty("database.url"));
		dataSourceConfig.setUsername(env.getRequiredProperty("database.username"));
		dataSourceConfig.setPassword(env.getRequiredProperty("database.password"));
		return new HikariDataSource(dataSourceConfig);
	}

	@Override
	@Bean
	public Properties jpaProperties() {
		final Properties jpaProperties = new Properties();
		jpaProperties.put(AvailableSettings.DIALECT,
				env.getRequiredProperty(AvailableSettings.DIALECT));
		jpaProperties.put(AvailableSettings.HBM2DDL_AUTO,
				env.getRequiredProperty(AvailableSettings.HBM2DDL_AUTO)
		);
		jpaProperties.put(AvailableSettings.MAX_FETCH_DEPTH,
				env.getRequiredProperty(AvailableSettings.MAX_FETCH_DEPTH)
		);
		jpaProperties.put(AvailableSettings.STATEMENT_FETCH_SIZE,
				env.getRequiredProperty(AvailableSettings.STATEMENT_FETCH_SIZE)
		);
		jpaProperties.put(AvailableSettings.STATEMENT_BATCH_SIZE,
				env.getRequiredProperty(AvailableSettings.STATEMENT_BATCH_SIZE)
		);
		jpaProperties.put(AvailableSettings.SHOW_SQL,
				env.getRequiredProperty(AvailableSettings.SHOW_SQL)
		);
		jpaProperties.put(AvailableSettings.FORMAT_SQL,
				env.getRequiredProperty(AvailableSettings.FORMAT_SQL)
		);
		return jpaProperties;
	}
}
