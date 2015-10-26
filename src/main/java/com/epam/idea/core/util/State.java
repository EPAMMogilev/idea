package com.epam.idea.core.util;

public enum State {
    Draft("Draft"), New("New"), Assigned("Assigned"), InProgress("In Progress"), Done("Done"), Verified(
            "Verified"), Deleted("Deleted"), Undefined("Undefined");

    private final String title;

    State(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

}
