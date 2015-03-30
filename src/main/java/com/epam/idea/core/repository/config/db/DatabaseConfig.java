package com.epam.idea.core.repository.config.db;

import java.util.Properties;
import javax.sql.DataSource;

public interface DatabaseConfig {
	String SCHEMA_SCRIPT_LOCATION = "classpath:db/sql/schema.sql";
	String TEST_SCHEMA_SCRIPT_LOCATION = "classpath:db/sql/test-schema.sql";
	String DATA_SCRIPT_LOCATION = "classpath:db/sql/test-data.sql";

	/** Name of the DataSource class provided by the JDBC driver. */
	String PROPERTY_NAME_DB_DRIVER_CLASS = "db.driver";

	/** Name of the connection url. */
	String PROPERTY_NAME_DB_URL = "db.url";

	/**
	 * The default authentication username used when obtaining Connections
	 * from the underlying driver.
	 */
	String PROPERTY_NAME_DB_USER = "db.username";

	/**
	 * The default authentication password used when obtaining Connections
	 * from the underlying driver.
	 */
	String PROPERTY_NAME_DB_PASSWORD = "db.password";

	/**
	 * Create and configure DataSource bean.
	 *
	 * @return DataSource instance
	 */
	DataSource dataSource();

	/**
	 * Specify JPA properties.
	 *
	 * @return Properties instance
	 */
	Properties jpaProperties();
}
