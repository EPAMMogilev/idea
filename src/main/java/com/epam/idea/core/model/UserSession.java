package com.epam.idea.core.model;

import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * Created by Ihar_Niakhlebau on 25-May-15.
 */

@Entity
@Table(name = "USER_SESSION")
public class UserSession {

	@Id
	@Column(name = "ID")
	private String sessionId;

	@OneToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(name = "CREATION_TIME", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime creationTime;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ZonedDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(ZonedDateTime creationTime) {
		this.creationTime = creationTime;
	}

	@PrePersist
	public void prePersist() {
		UUID id = UUID.randomUUID();
		sessionId = id.toString();
		ZonedDateTime now = ZonedDateTime.now();
		this.creationTime = now;
	}
}
