package com.epam.idea.util;

import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.support.AbstractConnection;

/**
 * Created by Ihar_Niakhlebau on 16-Jun-15.
 */
public class TestConnection extends AbstractConnection {

	private ConnectionData connectionData;

	private UserProfile userProfile;

	public TestConnection(ConnectionData connectionData, UserProfile userProfile) {
		super(connectionData, null);
		this.connectionData = connectionData;
		this.userProfile = userProfile;
	}

	@Override
	public UserProfile fetchUserProfile() {
		return userProfile;
	}

	@Override
	public Object getApi() {
		return null;
	}

	@Override
	public ConnectionData createData() {
		return connectionData;
	}
}
