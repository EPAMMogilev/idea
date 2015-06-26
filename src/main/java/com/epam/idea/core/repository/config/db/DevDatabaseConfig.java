package com.epam.idea.core.repository.config.db;

import java.util.Properties;
import javax.sql.DataSource;

import com.epam.idea.core.repository.config.support.DatabaseConfigProfile;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.FORMAT_SQL;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.MAX_FETCH_DEPTH;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.STATEMENT_BATCH_SIZE;
import static org.hibernate.cfg.AvailableSettings.STATEMENT_FETCH_SIZE;

@Profile(DatabaseConfigProfile.DEV)
@Configuration
@PropertySource("classpath:/db/dev.properties")
public class DevDatabaseConfig implements DatabaseConfig {

	/** The runtime environment of our application. */
	@Autowired
	private Environment env;

	@Value(SCHEMA_SCRIPT_LOCATION)
	private Resource schemaScript;

	@Value(DATA_SCRIPT_LOCATION)
	private Resource dataScript;

	@Override
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		final HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(this.env.getRequiredProperty(PROPERTY_NAME_DB_DRIVER_CLASS));
		hikariConfig.setJdbcUrl(this.env.getRequiredProperty(PROPERTY_NAME_DB_URL));
		hikariConfig.setUsername(this.env.getRequiredProperty(PROPERTY_NAME_DB_USER));
		hikariConfig.setPassword(this.env.getRequiredProperty(PROPERTY_NAME_DB_PASSWORD));
		final HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		DatabasePopulatorUtils.execute(createDatabasePopulator(), dataSource);
		return dataSource;
	}

	private DatabasePopulator createDatabasePopulator() {
		final ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(this.schemaScript);
		databasePopulator.addScript(this.dataScript);
		return databasePopulator;
	}

	@Override
	@Bean
	public Properties jpaProperties() {
		final Properties jpaProperties = new Properties();
		jpaProperties.put(DIALECT,
				this.env.getRequiredProperty(DIALECT));
		jpaProperties.put(HBM2DDL_AUTO,
				this.env.getRequiredProperty(HBM2DDL_AUTO)
		);
		jpaProperties.put(MAX_FETCH_DEPTH,
				this.env.getRequiredProperty(MAX_FETCH_DEPTH)
		);
		jpaProperties.put(STATEMENT_FETCH_SIZE,
				this.env.getRequiredProperty(STATEMENT_FETCH_SIZE)
		);
		jpaProperties.put(STATEMENT_BATCH_SIZE,
				this.env.getRequiredProperty(STATEMENT_BATCH_SIZE)
		);
		jpaProperties.put(SHOW_SQL,
				this.env.getRequiredProperty(SHOW_SQL)
		);
		jpaProperties.put(FORMAT_SQL,
				this.env.getRequiredProperty(FORMAT_SQL)
		);
		return jpaProperties;
	}
}
