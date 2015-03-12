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

@Profile(DatabaseConfigProfile.PROD)
@Configuration
@PropertySource("classpath:/db/production.properties")
public class ProdDatabaseConfig implements DatabaseConfig {

	@Autowired
	private Environment env;

	@Override
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDataSourceClassName(env.getRequiredProperty("dataSourceClassName"));
		dataSourceConfig.setDriverClassName(env.getRequiredProperty("dataSource.driver"));
		dataSourceConfig.setJdbcUrl(env.getRequiredProperty("dataSource.url"));
		dataSourceConfig.setUsername(env.getRequiredProperty("dataSource.username"));
		dataSourceConfig.setPassword(env.getRequiredProperty("dataSource.password"));
		dataSourceConfig.addDataSourceProperty("cachePrepStmts", env.getRequiredProperty("dataSource.cachePrepStmts"));
		dataSourceConfig.addDataSourceProperty("prepStmtCacheSize", env.getRequiredProperty("dataSource.prepStmtCacheSize"));
		dataSourceConfig.addDataSourceProperty("prepStmtCacheSqlLimit", env.getRequiredProperty("dataSource.prepStmtCacheSqlLimit"));
		dataSourceConfig.addDataSourceProperty("useServerPrepStmts", env.getRequiredProperty("dataSource.useServerPrepStmts"));
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
