package com.epam.idea.core.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Column(name = "BODY", nullable = false)
	private String body;

	@Column(name = "CREATION_TIME", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime creationTime;

	@Column(name = "MODIFICATION_TIME", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime modificationTime;

	@Column(name = "RATING", nullable = false)
	private int rating;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User author;

	@ManyToOne
	@JoinColumn(name = "IDEA_ID")
	private Idea subject;

	public Comment() {
		//empty
	}

	public long getId() {
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

	@Override
	public int hashCode() {
		return Objects.hash(body, creationTime, modificationTime, rating, author, subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Comment other = (Comment) obj;
		return Objects.equals(this.body, other.body)
				&& Objects.equals(this.creationTime, other.creationTime)
				&& Objects.equals(this.modificationTime, other.modificationTime)
				&& Objects.equals(this.rating, other.rating)
				&& Objects.equals(this.author, other.author)
				&& Objects.equals(this.subject, other.subject);
	}
}
