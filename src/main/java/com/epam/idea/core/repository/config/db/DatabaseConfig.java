package com.epam.idea.core.repository.config.db;

import java.util.Properties;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public abstract class DatabaseConfig {
	protected static final String SCHEMA_SCRIPT_LOCATION = "classpath:db/sql/schema.sql";
	protected static final String DATA_SCRIPT_LOCATION = "classpath:db/sql/test-data.sql";

	protected static final String SCHEMA_SCRIPT_LOCATION_PROD = "classpath:db/sql/schema_mysql.sql";
	protected static final String DATA_SCRIPT_LOCATION_PROD = "classpath:db/sql/test-data_mysql.sql";

	/** Name of the DataSource class provided by the JDBC driver. */
	private static final String PROPERTY_NAME_DB_DRIVER_CLASS = "db.driver";

	/** Name of the connection url. */
	private static final String PROPERTY_NAME_DB_URL = "db.url";

	/**
	 * The default authentication username used when obtaining Connections
	 * from the underlying driver.
	 */
	private static final String PROPERTY_NAME_DB_USER = "db.username";

	/**
	 * The default authentication password used when obtaining Connections
	 * from the underlying driver.
	 */
	private static final String PROPERTY_NAME_DB_PASSWORD = "db.password";
	
	/** The runtime environment of our application. */
	@Autowired
	protected Environment env;

	/**
	 * Create and configure DataSource bean.
	 *
	 * @return DataSource instance
	 */
	protected abstract DataSource dataSource();


	protected HikariDataSource createDataSource() {
		final HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(this.env.getRequiredProperty(PROPERTY_NAME_DB_DRIVER_CLASS));
		hikariConfig.setJdbcUrl(this.env.getRequiredProperty(PROPERTY_NAME_DB_URL));
		hikariConfig.setUsername(this.env.getRequiredProperty(PROPERTY_NAME_DB_USER));
		hikariConfig.setPassword(this.env.getRequiredProperty(PROPERTY_NAME_DB_PASSWORD));

		final HikariDataSource dataSource = new HikariDataSource(hikariConfig);

		dataSource.addDataSourceProperty("connectionProperties", "useUnicode=yes;characterEncoding=UTF-8");

		return dataSource;
	}

	protected DatabasePopulator createDatabasePopulator(Resource schemaScript, Resource dataScript) {
		final ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(schemaScript);
		databasePopulator.addScript(dataScript);
		return databasePopulator;
	}

	protected DatabasePopulator createDatabasePopulator(Resource schemaScript) {
		final ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(schemaScript);
		return databasePopulator;
	}

	/**
	 * Specify JPA properties.
	 *
	 * @return Properties instance
	 */
	@Bean
	public Properties jpaProperties() {
		final Properties jpaProperties = new Properties();
		jpaProperties.put(AvailableSettings.DIALECT,
				this.env.getRequiredProperty(AvailableSettings.DIALECT));
		jpaProperties.put(AvailableSettings.MAX_FETCH_DEPTH,
				this.env.getRequiredProperty(AvailableSettings.MAX_FETCH_DEPTH)
		);
		jpaProperties.put(AvailableSettings.STATEMENT_FETCH_SIZE,
				this.env.getRequiredProperty(AvailableSettings.STATEMENT_FETCH_SIZE)
		);
		jpaProperties.put(AvailableSettings.STATEMENT_BATCH_SIZE,
				this.env.getRequiredProperty(AvailableSettings.STATEMENT_BATCH_SIZE)
		);
		jpaProperties.put(AvailableSettings.SHOW_SQL,
				this.env.getRequiredProperty(AvailableSettings.SHOW_SQL)
		);
		jpaProperties.put(AvailableSettings.FORMAT_SQL,
				this.env.getRequiredProperty(AvailableSettings.FORMAT_SQL)
		);
		return jpaProperties;
	}
}
