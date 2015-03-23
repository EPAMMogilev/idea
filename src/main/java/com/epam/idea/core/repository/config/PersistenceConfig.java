package com.epam.idea.core.repository.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Common Settings for JPA
 * <ul>
 * <li>{@link Configuration} - defines this class as a Spring Configuration class</li>
 * <li>{@link org.springframework.context.annotation.ComponentScan} - replaces &lt;context:component-scan/&gt;</li>
 * <li>{@link org.springframework.transaction.annotation.EnableTransactionManagement} - replaces &lt;tx:annotation-driven/&gt;</li>
 * <li>{@link org.springframework.data.jpa.repository.config.EnableJpaRepositories} - replaces Spring Data Jpa &lt;jpa:repositories/&gt;</li>
 * </ul>
 */
@Configuration
@ComponentScan("com.epam.idea.core.repository.config.db")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.epam.idea.core.repository"})
public class PersistenceConfig {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private Properties jpaProperties;

	@Value("classpath:db/sql/schema.sql")
	private Resource schemaScript;

	@Value("classpath:db/sql/test-data.sql")
	private Resource dataScript;

	@Bean
	@DependsOn({"dataSourceInitializer"})
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.epam.idea.core.model");
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer() {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(databasePopulator());
		return initializer;
	}

	private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(schemaScript);
		//populator.addScript(dataScript);
		return populator;
	}

	// PropertySourcesPlaceholderConfigurer has to be static in order to kick in very early in the context initialization process.
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
