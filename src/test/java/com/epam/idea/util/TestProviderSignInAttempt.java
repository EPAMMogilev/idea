package com.epam.idea.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInAttempt;

/**
 * Created by Ihar_Niakhlebau on 16-Jun-15.
 */
public class TestProviderSignInAttempt extends ProviderSignInAttempt {

	private Connection<?> connection;

	private Set<String> connections = new HashSet<>();

	public TestProviderSignInAttempt(Connection<?> connection) {
		super(connection);
		this.connection = connection;
	}

	@Override
	public Connection<?> getConnection(ConnectionFactoryLocator connectionFactoryLocator) {
		return connection;
	}


	void addConnection(String userId, ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository connectionRepository) {
		connections.add(userId);
	}


	public Set<String> getConnections() {
		return connections;
	}
}
