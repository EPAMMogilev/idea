package com.epam.idea.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

/**
 * {@code @TransactionalIntegrationTest} is a class-level annotation same as
 * {@code @IntegrationTest} but specifies that a test should rollback all transactions
 * after finishing.
 *
 * @see com.epam.idea.annotation.IntegrationTest
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@IntegrationTest
@Transactional
public @interface TransactionalIntegrationTest {}
