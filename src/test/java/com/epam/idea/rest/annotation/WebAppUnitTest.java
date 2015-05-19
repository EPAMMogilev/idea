package com.epam.idea.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epam.idea.rest.config.TestRootConfig;
import com.epam.idea.rest.config.WebAppConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * {@code @WebAppUnitTest} is a composed class-level annotation that centralizes
 * the common unit test configuration for web layer.
 *
 * Note that {@code @WebAppUnitTest} must be used in conjunction with
 * {@code @RunWith(SpringJUnit4ClassRunner.class)}, either within a single test class
 * or within a test class hierarchy.
 *
 * @see org.springframework.web.context.WebApplicationContext
 * @see org.springframework.test.context.ContextHierarchy
 * @see org.springframework.test.context.ContextConfiguration
 * @see org.springframework.test.context.junit4.SpringJUnit4ClassRunner
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextHierarchy({
		@ContextConfiguration(classes = TestRootConfig.class),
		@ContextConfiguration(classes = WebAppConfig.class)
})
@WebAppConfiguration
public @interface WebAppUnitTest {}
