package com.epam.idea.core.exceptions;

import com.epam.idea.core.service.exception.IdeaNotFoundException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Aliaksei_Liubetski on 2015-06-01.
 */
public class IdeaNotFoundExceptionTest {

    public static final String SOME_EXCEPTION = "Some error";

    @Test
    public void shouldIdeaExistsExceptionString(){
        IdeaNotFoundException exception = new IdeaNotFoundException(SOME_EXCEPTION);

        assertThat(exception.getMessage()).isEqualTo(SOME_EXCEPTION);
    }

    @Test
    public void shouldIdeaExistsExceptionWithThrowable(){
        IdeaNotFoundException exception = new IdeaNotFoundException(SOME_EXCEPTION, new Exception(SOME_EXCEPTION));

        assertThat(exception.getCause().getMessage()).isEqualTo(SOME_EXCEPTION);
        assertThat(exception.getMessage()).isEqualTo(SOME_EXCEPTION);
    }

    @Test
    public void shouldIdeaExistsExceptionThrowable(){
        IdeaNotFoundException exception = new IdeaNotFoundException(new Exception(SOME_EXCEPTION));

        assertThat(exception.getCause().getMessage()).isEqualTo(SOME_EXCEPTION);
    }
}
