package com.epam.idea.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epam.idea.core.repository.config.PersistenceConfig;
import com.epam.idea.core.repository.config.support.DatabaseConfigProfile;
import com.epam.idea.rest.config.RootConfig;
import com.epam.idea.rest.config.WebAppConfig;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * {@code @IntegrationTest} is a class-level annotation that is is used to define
 * common configuration for integration test scenarios.
 *
 * Note that {@code @IntegrationTest} must be used in conjunction with
 * {@code @RunWith(SpringJUnit4ClassRunner.class)}, either within a single test class
 * or within a test class hierarchy.
 *
 * @see org.springframework.web.context.WebApplicationContext
 * @see org.springframework.test.context.ContextHierarchy
 * @see org.springframework.test.context.ContextConfiguration
 * @see org.springframework.test.context.TestExecutionListeners
 * @see org.springframework.test.context.junit4.SpringJUnit4ClassRunner
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles(DatabaseConfigProfile.TEST)
@ContextHierarchy({
		@ContextConfiguration(classes = {RootConfig.class, PersistenceConfig.class}),
		@ContextConfiguration(classes = WebAppConfig.class)
})
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class
})
@WebAppConfiguration
public @interface IntegrationTest {}
