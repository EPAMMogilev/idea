package com.epam.idea.core.exceptions;

import com.epam.idea.core.service.exception.DuplicateUserException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Aliaksei_Liubetski on 2015-06-01.
 */
public class DuplicateUserExceptionTest {

    public static final String SOME_EXCEPTION = "Some error";

    @Test
    public void shouldIdeaExistsExceptionString(){
	    DuplicateUserException exception = new DuplicateUserException(SOME_EXCEPTION);

        assertThat(exception.getMessage()).isEqualTo("User with email \"" + SOME_EXCEPTION + ".\" already exist");
    }

    @Test
    public void shouldIdeaExistsExceptionWithThrowable(){
	    DuplicateUserException exception = new DuplicateUserException(SOME_EXCEPTION, new Exception(SOME_EXCEPTION));

        assertThat(exception.getCause().getMessage()).isEqualTo(SOME_EXCEPTION);
        assertThat(exception.getMessage()).isEqualTo(SOME_EXCEPTION);
    }

    @Test
    public void shouldIdeaExistsExceptionThrowable(){
	    DuplicateUserException exception = new DuplicateUserException(new Exception(SOME_EXCEPTION));

        assertThat(exception.getCause().getMessage()).isEqualTo(SOME_EXCEPTION);
    }
}
