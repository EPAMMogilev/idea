package com.epam.idea.core.util;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum State {
    Draft("Draft"), New("New"), Assigned("Assigned"), InProgress("In Progress"), Done("Done"), Verified(
            "Verified"), Deleted("Deleted"), Undefined("Undefined");

    private final String title;

    public String getTitle() {
        return title;
    }

    public String getName() {
        return this.name();
    }

    State(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

}
