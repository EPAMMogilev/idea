package com.epam.idea.core.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum State {
    Draft("Draft"), New("New"), Assigned("Assigned"), InProgress("In Progress"), Done("Done"), Verified(
            "Verified"), Deleted("Deleted"), Undefined("Undefined");

    private final String title;

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("name")
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

    /**
     * Gets a MyEnumType from id or <tt>null</tt> if the requested type doesn't
     * exist.
     * 
     * @param id
     *            String
     * @return MyEnumType
     */
    public static State fromName(final String name) {
        if (name != null) {
            for (final State type : State.values()) {
                if (name.equalsIgnoreCase(type.name())) {
                    return type;
                }
            }
        }
        return null;
    }

}
