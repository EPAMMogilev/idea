package com.epam.idea.core.model;

import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USER")
public class User implements Serializable {

	public static final int MIN_LENGTH_USERNAME = 1;
	public static final int MAX_LENGTH_USERNAME = 20;
	public static final int MIN_LENGTH_EMAIL = 3;
	public static final int MAX_LENGTH_EMAIL = 20;
	public static final int MIN_LENGTH_PASSWORD = 6;
	public static final int MAX_LENGTH_PASSWORD = 20;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Column(name = "USERNAME", nullable = false)
	private String username;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "CREATION_TIME", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime creationTime;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Idea> ideas;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Comment> comments;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE",
			joinColumns = @JoinColumn(name = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<Role> roles;

	public User() {
		this.ideas = new ArrayList<>();
		this.comments = new ArrayList<>();
		this.roles = new ArrayList<>();
	}

	public void updateWith(final User source) {
		this.email = source.email;
		this.password = source.password;
		this.username = source.username;
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<Idea> getIdeas() {
		return ideas;
	}

	public void setIdeas(List<Idea> ideas) {
		this.ideas = ideas;
	}

	public void addIdea(Idea idea) {
		this.ideas.add(idea);
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(Comment comment) {
		this.comments.add(comment);
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	@PrePersist
	public void prePersist() {
		this.creationTime = ZonedDateTime.now();
	}


	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", creationTime=" + creationTime +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(username, user.username) &&
				Objects.equals(email, user.email) &&
				Objects.equals(password, user.password) &&
				Objects.equals(creationTime, user.creationTime) &&
				Objects.equals(ideas, user.ideas) &&
				Objects.equals(comments, user.comments) &&
				Objects.equals(roles, user.roles);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, email, password, creationTime, ideas, comments, roles);
	}
}
