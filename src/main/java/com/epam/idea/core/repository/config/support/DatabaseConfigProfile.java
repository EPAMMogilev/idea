package com.epam.idea.core.repository.config.support;

/**
 * This class defines the Spring profiles used in the project. The idea behind this class
 * is that it helps us to avoid typos when we are using these profiles. At the moment
 * there are three profiles which are described in the following:
 * <p>
 * <ul>
 * <li>The DEV profile is used during development of our application.</li>
 * <li>The TEST profile is used when we run the integration tests of our application.</li>
 * <li>The PROD profile is used when we run our application in production.</li>
 * </ul>
 */
public final class DatabaseConfigProfile {
	public static final String DEV = "dev";
	public static final String TEST = "test";
	public static final String PROD = "prod";

	/** Prevent instantiation. */
	private DatabaseConfigProfile() {
		//empty
	}
}
