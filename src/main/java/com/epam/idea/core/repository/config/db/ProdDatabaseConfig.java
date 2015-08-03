package com.epam.idea.core.repository.config.db;

import java.util.Properties;
import javax.sql.DataSource;

import com.epam.idea.core.repository.config.support.DatabaseConfigProfile;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
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

@Profile(DatabaseConfigProfile.PROD)
@Configuration
@PropertySource("classpath:/db/prod.properties")
public class ProdDatabaseConfig implements DatabaseConfig {

	@Autowired
	private Environment env;

    @Value(SCHEMA_SCRIPT_LOCATION_PROD)
    private Resource schemaScript;

    @Value(DATA_SCRIPT_LOCATION_PROD)
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
        try {
            dataSource.setMaximumPoolSize(Integer.getInteger(this.env.getRequiredProperty("database.connection.pool_size")));
        }catch (Exception e){
            dataSource.setMaximumPoolSize(1000);
        }
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
