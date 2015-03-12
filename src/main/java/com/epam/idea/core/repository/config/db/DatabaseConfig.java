package com.epam.idea.core.repository.config.db;

import javax.sql.DataSource;
import java.util.Properties;

public interface DatabaseConfig {
	DataSource dataSource();
	Properties jpaProperties();
}
