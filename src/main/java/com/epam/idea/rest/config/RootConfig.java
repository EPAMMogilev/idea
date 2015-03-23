package com.epam.idea.rest.config;

import com.epam.idea.core.repository.config.PersistenceConfig;
import com.epam.idea.core.service.config.ServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({PersistenceConfig.class, ServiceConfig.class})
public class RootConfig {
}
