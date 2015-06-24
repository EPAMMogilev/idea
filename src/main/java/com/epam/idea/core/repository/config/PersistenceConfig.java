package com.epam.idea.core.repository.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This configuration class configures the persistence layer of our example application and
 * enables annotation driven transaction management.
 */
@Configuration
@ComponentScan("com.epam.idea.core.repository.config.db")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.epam.idea.core.repository"})
public class PersistenceConfig {

	/**
	 * Specify packages to search for autodetection of your entity
	 * classes in the classpath.
	 */
	private static final String[] ENTITY_PACKAGES = {"com.epam.idea.core.model"};

	/** The datasource that provides the database connections. */
	@Autowired
	private DataSource dataSource;

	@Autowired
	private Properties jpaProperties;

	/**
	 * Create the JPA entity manager factory bean.
	 *
	 * @return LocalContainerEntityManagerFactoryBean instance
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(this.dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(ENTITY_PACKAGES);
		entityManagerFactoryBean.setJpaProperties(this.jpaProperties);
		return entityManagerFactoryBean;
	}

	/**
	 * Create the transaction manager bean that integrates the used JPA provider with the
	 * Spring transaction mechanism.
	 *
	 * @param entityManagerFactory The used JPA entity manager factory.
	 * @return JpaTransactionManager instance
	 */
	@Bean
	public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
}
