package com.epam.idea.core.repository.config.db;

import java.util.Properties;
import javax.sql.DataSource;

import com.epam.idea.core.repository.config.support.DatabaseConfigProfile;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Profile(DatabaseConfigProfile.PROD)
@Configuration
@PropertySource("classpath:/db/prod.properties")
public class ProdDatabaseConfig implements DatabaseConfig {

	@Autowired
	private Environment env;

	@Override
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		final HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDataSourceClassName(this.env.getRequiredProperty("dataSourceClassName"));
		dataSource.addDataSourceProperty("url", this.env.getRequiredProperty("database.url"));
		dataSource.addDataSourceProperty("user", this.env.getRequiredProperty("database.username"));
		dataSource.addDataSourceProperty("password", this.env.getRequiredProperty("database.password"));
		dataSource.addDataSourceProperty("cachePrepStmts", this.env.getRequiredProperty("database.cachePrepStmts"));
		dataSource.addDataSourceProperty("prepStmtCacheSize", this.env.getRequiredProperty("database.prepStmtCacheSize"));
		dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", this.env.getRequiredProperty("database.prepStmtCacheSqlLimit"));
		dataSource.addDataSourceProperty("useServerPrepStmts", this.env.getRequiredProperty("database.useServerPrepStmts"));
		dataSource.setMaximumPoolSize(100);
		return dataSource;
	}

	@Override
	@Bean
	public Properties jpaProperties() {
		final Properties jpaProperties = new Properties();
		jpaProperties.put(AvailableSettings.DIALECT,
				this.env.getRequiredProperty(AvailableSettings.DIALECT));
		jpaProperties.put(AvailableSettings.HBM2DDL_AUTO,
				this.env.getRequiredProperty(AvailableSettings.HBM2DDL_AUTO)
		);
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
