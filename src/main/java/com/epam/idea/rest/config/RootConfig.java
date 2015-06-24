package com.epam.idea.rest.config;

import com.epam.idea.core.repository.config.PersistenceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Import({PersistenceConfig.class})
@ComponentScan("com.epam.idea.core.service.impl")
public class RootConfig {

	// PropertySourcesPlaceholderConfigurer has to be static in order to kick in very early in the context initialization process.
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
