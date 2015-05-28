package com.epam.idea.rest.resource;

import java.time.ZonedDateTime;

import com.epam.idea.rest.resource.support.JsonPropertyName;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class UserSessionResource extends ResourceSupport {

	@JsonProperty(JsonPropertyName.ID)
	private String sessionId;

	private UserResource user;

	@JsonProperty(JsonPropertyName.CREATION_TIME)
	private ZonedDateTime creationTime;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public UserResource getUser() {
		return user;
	}

	public void setUserResource(UserResource user) {
		this.user = user;
	}

	public ZonedDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(ZonedDateTime creationTime) {
		this.creationTime = creationTime;
	}
}
