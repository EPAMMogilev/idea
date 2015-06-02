package com.epam.idea.core.exceptions;

import com.epam.idea.core.service.exception.UserSessionNotFoundException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Aliaksei_Liubetski on 2015-06-01.
 */
public class UserSessionNotFoundExceptionTest {

    public static final String SOME_EXCEPTION = "Some error";

    @Test
    public void shouldIdeaExistsExceptionString(){
        UserSessionNotFoundException exception = new UserSessionNotFoundException(SOME_EXCEPTION);

        String sCheck = String.format(UserSessionNotFoundException.ERROR_MSG_PATTERN_USER_SESSION_NOT_FOUND, SOME_EXCEPTION);
        assertThat(exception.getMessage()).isEqualTo(sCheck);
    }

    @Test
    public void shouldIdeaExistsExceptionWithThrowable(){
        UserSessionNotFoundException exception = new UserSessionNotFoundException(SOME_EXCEPTION, new Exception(SOME_EXCEPTION));

        assertThat(exception.getCause().getMessage()).isEqualTo(SOME_EXCEPTION);
        assertThat(exception.getMessage()).isEqualTo(SOME_EXCEPTION);
    }

    @Test
    public void shouldIdeaExistsExceptionThrowable(){
        UserSessionNotFoundException exception = new UserSessionNotFoundException(new Exception(SOME_EXCEPTION));

        assertThat(exception.getCause().getMessage()).isEqualTo(SOME_EXCEPTION);
    }
}
