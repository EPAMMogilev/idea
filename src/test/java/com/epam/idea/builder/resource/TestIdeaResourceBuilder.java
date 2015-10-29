package com.epam.idea.builder.resource;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.epam.idea.core.model.User;
import com.epam.idea.core.util.State;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.TagResource;
import com.epam.idea.rest.resource.UserResource;

public class TestIdeaResourceBuilder {

    public static final String DEFAULT_TITLE = "Bar";
    public static final String DEFAULT_DESCRIPTION = "Lorem ipsum";
    public static final State DEFAULT_STATE = State.Draft;
    public static final int DEFAULT_RATING = 5;

    private String title;
    private String description;
    private State state;
    private ZonedDateTime creationTime;
    private ZonedDateTime modificationTime;
    private int rating;
    private UserResource author;
    private List<TagResource> tags = new ArrayList<>();
    private List<UserResource> likedUsers = new ArrayList<>();
    private boolean liked;

    private TestIdeaResourceBuilder() {
    }

    public static TestIdeaResourceBuilder anIdeaResource() {
        return new TestIdeaResourceBuilder()
                .withTitle(DEFAULT_TITLE)
                .withState(DEFAULT_STATE)
                .withRating(DEFAULT_RATING)
                .withDescription(DEFAULT_DESCRIPTION)
                .withLiked(false);
    }

    public TestIdeaResourceBuilder withTitle(final String title) {
        this.title = title;
        return this;
    }

    public TestIdeaResourceBuilder withDescription(final String description) {
        this.description = description;
        return this;
    }

    public TestIdeaResourceBuilder withState(final State state) {
        this.state = state;
        return this;
    }

    public TestIdeaResourceBuilder withCreationTime(final ZonedDateTime creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public TestIdeaResourceBuilder withModificationTime(final ZonedDateTime modificationTime) {
        this.modificationTime = modificationTime;
        return this;
    }

    public TestIdeaResourceBuilder withRating(final int rating) {
        this.rating = rating;
        return this;
    }

    public TestIdeaResourceBuilder withAuthor(final UserResource author) {
        this.author = author;
        return this;
    }

    public TestIdeaResourceBuilder withRelatedTags(final List<TagResource> relatedTags) {
        this.tags = relatedTags;
        return this;
    }

    public TestIdeaResourceBuilder withLikedUsers(final List<UserResource> likedUsers) {
        this.likedUsers = likedUsers;
        return this;
    }

    public TestIdeaResourceBuilder withLiked(final boolean liked) {
        this.liked = liked;
        return this;
    }

    public TestIdeaResourceBuilder but() {
        return anIdeaResource().withTitle(title).withDescription(description).withState(state).withCreationTime(creationTime)
                .withModificationTime(modificationTime).withRating(rating).withAuthor(author).withRelatedTags(tags)
                .withLikedUsers(likedUsers).withLiked(liked);
    }

    public IdeaResource build() {
        final IdeaResource ideaResource = new IdeaResource();
        ideaResource.setTitle(title);
        ideaResource.setDescription(description);
        ideaResource.setState(state);
        ideaResource.setCreationTime(creationTime);
        ideaResource.setModificationTime(modificationTime);
        ideaResource.setRating(rating);
        ideaResource.setLiked(liked);
        // if (author != null) {
        // ideaResource.setAuthor(author);
        // }
        ideaResource.setTags(tags);
        ideaResource.setLikedUsers(likedUsers);
        return ideaResource;
    }
}
