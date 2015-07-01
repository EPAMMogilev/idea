package com.epam.idea.core.exceptions;

import com.epam.idea.core.service.exception.IdeaExistsException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Aliaksei_Liubetski on 2015-06-01.
 */
public class IdeaExistsExceptionTest {

    public static final String SOME_EXCEPTION = "Some error";

    @Test
    public void shouldIdeaExistsExceptionString(){
        IdeaExistsException exception = new IdeaExistsException(SOME_EXCEPTION);

        System.out.println("exception.getMessage(): "+ exception.getMessage());

        assertThat(exception.getMessage()).isEqualTo(SOME_EXCEPTION);
    }

    @Test
    public void shouldIdeaExistsExceptionWithThrowable(){
        IdeaExistsException exception = new IdeaExistsException(SOME_EXCEPTION, new Exception(SOME_EXCEPTION));

        System.out.println("exception.getMessage(): "+ exception.getMessage());

        assertThat(exception.getCause().getMessage()).isEqualTo(SOME_EXCEPTION);
        assertThat(exception.getMessage()).isEqualTo(SOME_EXCEPTION);
    }

    @Test
    public void shouldIdeaExistsExceptionThrowable(){
        IdeaExistsException exception = new IdeaExistsException(new Exception(SOME_EXCEPTION));

        System.out.println("exception.getMessage(): "+ exception.getMessage());

        assertThat(exception.getCause().getMessage()).isEqualTo(SOME_EXCEPTION);
    }

    @Test
    public void shouldIdeaExistsExceptionDefaultConstructorThrowable(){
        IdeaExistsException exception = new IdeaExistsException();

        System.out.println("exception.getMessage(): "+ exception.getMessage());

        assertThat(exception.getCause()).isNull();
    }
}
