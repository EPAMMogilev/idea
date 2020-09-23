package com.epam.idea.core.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable {

    public static final int MIN_LENGTH_BODY = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "BODY", nullable = false)
	private String body;

	@Column(name = "CREATION_TIME", nullable = false)
	//@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime creationTime;

	@Column(name = "MODIFICATION_TIME", nullable = false)
	//@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime modificationTime;

	@Column(name = "RATING", nullable = false)
	private int rating;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User author;

	@ManyToOne
	@JoinColumn(name = "IDEA_ID")
	private Idea subject;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "COMMENT_LIKES",
			joinColumns = @JoinColumn(name = "COMMENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private List<User> likedUsers;

	@Transient
	private boolean liked;

	public Comment() {
		this.likedUsers = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public ZonedDateTime getCreationTime() {
		return creationTime;
	}

	public ZonedDateTime getModificationTime() {
		return modificationTime;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Idea getSubject() {
		return subject;
	}

	public void setSubject(Idea subject) {
		this.subject = subject;
	}

	@PreUpdate
	public void preUpdate() {
		this.modificationTime = ZonedDateTime.now();
	}

	@PrePersist
	public void prePersist() {
		ZonedDateTime now = ZonedDateTime.now();
		this.creationTime = now;
		this.modificationTime = now;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", body='" + body + '\'' +
				", rating=" + rating +
				'}';
	}

	public List<User> getLikedUsers() {
		return likedUsers;
	}

	public void setLikedUsers(List<User> likedUsers) {
		this.likedUsers = likedUsers;
	}

	public boolean getLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}
}
