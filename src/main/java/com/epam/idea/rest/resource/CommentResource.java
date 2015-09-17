package com.epam.idea.rest.resource;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.epam.idea.core.model.Comment;
import com.epam.idea.rest.resource.support.JsonPropertyName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.hateoas.ResourceSupport;

public class CommentResource extends ResourceSupport {

	@JsonProperty(JsonPropertyName.ID)
	private long commentId;

	@Size(min = Comment.MIN_LENGTH_BODY)
	private String body;

	@JsonProperty(JsonPropertyName.CREATION_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private ZonedDateTime creationTime;

	@JsonProperty(JsonPropertyName.MODIFICATION_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private ZonedDateTime modificationTime;

	private int rating;

	//@JsonView({View.ExtendedBasic.class})
	private UserResource author;

	private IdeaResource subject;

	private boolean liked;

	private List<UserResource> likedUsers;

	public CommentResource() {
		this.setLikedUsers(new ArrayList<>());
	}

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
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

	public void setCreationTime(ZonedDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public ZonedDateTime getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(ZonedDateTime modificationTime) {
		this.modificationTime = modificationTime;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public UserResource getAuthor() {
		return author;
	}

	public void setAuthor(UserResource author) {
		this.author = author;
	}

	public Comment toComment() {
		final Comment comment = new Comment();
		comment.setBody(body);
		comment.setRating(rating);
		return comment;
	}

	public IdeaResource getSubject() {
		return subject;
	}

	public void setSubject(IdeaResource subject) {
		this.subject = subject;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public List<UserResource> getLikedUsers() {
		return likedUsers;
	}

	public void setLikedUsers(List<UserResource> likedUsers) {
		this.likedUsers = likedUsers;
	}
}
