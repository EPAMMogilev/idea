package com.epam.idea.rest.resource;

import java.time.ZonedDateTime;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.epam.idea.core.model.User;
import com.epam.idea.rest.resource.support.JsonPropertyName;
import com.epam.idea.rest.resource.support.View;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Email;
import org.springframework.hateoas.ResourceSupport;

public class UserResource extends ResourceSupport {

	@JsonProperty(JsonPropertyName.ID)
	private long userId;

	@Size(min = User.MIN_LENGTH_USERNAME, max = User.MAX_LENGTH_USERNAME)
	private String username;

	@Email
	@Size(min = User.MIN_LENGTH_EMAIL, max = User.MAX_LENGTH_EMAIL)
	private String email;

	@JsonView(View.Admin.class)
	@Transient
	@Size(min = User.MIN_LENGTH_PASSWORD, max = User.MAX_LENGTH_PASSWORD)
	private String password;

	@JsonProperty(JsonPropertyName.CREATION_TIME)
	private ZonedDateTime creationTime;

	public UserResource() {
		//empty
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ZonedDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(ZonedDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public User toUser() {
		final User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}
}
