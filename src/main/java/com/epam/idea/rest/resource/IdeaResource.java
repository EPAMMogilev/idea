package com.epam.idea.rest.resource;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.util.StateDeserializer;
import com.epam.idea.core.util.State;
import com.epam.idea.rest.resource.support.JsonPropertyName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class IdeaResource extends RepresentationModel<IdeaResource> {

    @JsonProperty(JsonPropertyName.ID)
    private long ideaId;

    @Size(min = Idea.MIN_LENGTH_TITLE, max = Idea.MAX_LENGTH_TITLE)
    private String title;

    @Size(max = Idea.MAX_LENGTH_DESCRIPTION)
    private String description;

    @JsonProperty(JsonPropertyName.CREATION_TIME)
    private ZonedDateTime creationTime;

    @JsonProperty(JsonPropertyName.MODIFICATION_TIME)
    private ZonedDateTime modificationTime;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private String imageUrl;

    // @JsonView({View.Basic.class})
    private int rating;

    private boolean liked;

    private UserResource author;

    private List<TagResource> tags;

    private List<UserResource> likedUsers;

    @JsonProperty("state")
    private State state;

    public IdeaResource() {
        this.tags = new ArrayList<>();
        this.setLikedUsers(new ArrayList<>());
    }

    public long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(final long ideaId) {
        this.ideaId = ideaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(final ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public ZonedDateTime getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(final ZonedDateTime modificationTime) {
        this.modificationTime = modificationTime;
    }

    public int getRating() {
        return rating;
    }

    public UserResource getAuthor() {
        return author;
    }

    public void setAuthor(final UserResource author) {
        this.author = author;
    }

    public void setRating(final int rating) {
        this.rating = rating;
    }

    public List<TagResource> getTags() {
        return tags;
    }

    public void setTags(final List<TagResource> tags) {
        this.tags = tags;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(final BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(final BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Idea toIdea() {
        final Idea idea = new Idea();
        idea.setTitle(title);
        idea.setDescription(description);
        idea.setRating(rating);
        idea.setRelatedTags(tags.parallelStream().map(TagResource::toTag).collect(Collectors.toList()));
        idea.setLikedUsers(likedUsers.stream().map(UserResource::toUser).collect(Collectors.toList()));
        idea.setLatitude(latitude);
        idea.setLongitude(longitude);
        idea.setImageUrl(imageUrl);
        idea.setLiked(liked);
        idea.setState(state);
        return idea;
    }

    public boolean getLiked() {
        return liked;
    }

    public void setLiked(final boolean liked) {
        this.liked = liked;
    }

    public List<UserResource> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(final List<UserResource> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public State getState() {
        return state;
    }

    @JsonDeserialize(using = StateDeserializer.class)
    public void setState(final State value) {
        this.state = value;
    }

}
