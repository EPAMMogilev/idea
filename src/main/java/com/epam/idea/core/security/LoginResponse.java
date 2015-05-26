package com.epam.idea.core.security;

/**
 * Created by Ihar_Niakhlebau on 25-May-15.
 */
public class LoginResponse {
	private String sessionId;
	private String status;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
