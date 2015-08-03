package com.epam.idea.core;

import com.epam.idea.rest.resource.support.JsonPropertyName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Aliaksei_Liubetski on 2015-06-01.
 */
@RunWith(MockitoJUnitRunner.class)
public class CostantsTest {

    @Test
    public void checkConstants(){
        assertThat(JsonPropertyName.ID).isEqualTo("id");
        assertThat(JsonPropertyName.CREATION_TIME).isEqualTo("createdAt");
        assertThat(JsonPropertyName.MODIFICATION_TIME).isEqualTo("lastModifiedAt");
    }//checkConstants
}
