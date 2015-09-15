package com.epam.idea.builder.resource;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.epam.idea.rest.resource.CommentResource;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.UserResource;

public class TestCommentResourceBuilder {

    public static final String DEFAULT_BODY = "Great";
    public static final int DEFAULT_RATING = 5;

    private String body;
    private ZonedDateTime creationTime;
    private ZonedDateTime modificationTime;
    private int rating;
    private UserResource author;
    private IdeaResource subject;
    private List<UserResource> likedUsers = new ArrayList<>();
    private boolean liked;

    private TestCommentResourceBuilder() {
    }

    public static TestCommentResourceBuilder aCommentResource() {
        return new TestCommentResourceBuilder().withBody(DEFAULT_BODY)
                .withRating(DEFAULT_RATING)
                .withLiked(false);
    }

    public TestCommentResourceBuilder withBody(final String body) {
        this.body = body;
        return this;
    }


    public TestCommentResourceBuilder withCreationTime(final ZonedDateTime creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public TestCommentResourceBuilder withModificationTime(final ZonedDateTime modificationTime) {
        this.modificationTime = modificationTime;
        return this;
    }

    public TestCommentResourceBuilder withRating(final int rating) {
        this.rating = rating;
        return this;
    }

    public TestCommentResourceBuilder withAuthor(final UserResource author) {
        this.author = author;
        return this;
    }


    public TestCommentResourceBuilder withSubject(final IdeaResource subject) {
        this.subject = subject;
        return this;
    }

    public TestCommentResourceBuilder withLikedUsers(final List<UserResource> likedUsers) {
        this.likedUsers = likedUsers;
        return this;
    }

    public TestCommentResourceBuilder withLiked(final boolean liked) {
        this.liked = liked;
        return this;
    }

    public TestCommentResourceBuilder but() {
        return aCommentResource().withBody(body).withCreationTime(creationTime)
                .withModificationTime(modificationTime).withRating(rating).withAuthor(author).withSubject(subject)
                .withLikedUsers(likedUsers).withLiked(liked);
    }

    public CommentResource build() {
        final CommentResource commentResource = new CommentResource();
        commentResource.setBody(body);
        commentResource.setCreationTime(creationTime);
        commentResource.setModificationTime(modificationTime);
        commentResource.setRating(rating);
        commentResource.setLiked(liked);
        commentResource.setLikedUsers(likedUsers);
        return commentResource;
    }
}
