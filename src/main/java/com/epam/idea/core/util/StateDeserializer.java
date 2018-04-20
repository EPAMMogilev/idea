package com.epam.idea.core.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;

public class StateDeserializer extends JsonDeserializer<State> {

    @Override
    public State deserialize(final JsonParser jp, final DeserializationContext dc)
            throws IOException, JsonProcessingException {

        jp.nextFieldName();

        final State type = State.fromName(jp.nextTextValue());

        jp.nextFieldName();

        if (type != null) {
            return type;
        }
        throw new JsonMappingException("invalid value for type State");
    }

}
