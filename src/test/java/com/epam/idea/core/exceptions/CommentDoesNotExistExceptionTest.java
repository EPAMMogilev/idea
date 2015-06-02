package com.epam.idea.core.exceptions;

import com.epam.idea.core.service.exception.CommentDoesNotExistException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Aliaksei_Liubetski on 2015-06-01.
 */
public class CommentDoesNotExistExceptionTest {

    public static final String SOME_EXCEPTION = "Some exception";

    @Test
    public void shouldCommentDoesNotExistExceptionString(){
        CommentDoesNotExistException exception = new CommentDoesNotExistException(SOME_EXCEPTION);

        assertThat(exception.getMessage()).isEqualTo(SOME_EXCEPTION);
    }

    @Test
    public void shouldCommentDoesNotExistExceptionStringWithThrowable(){
        CommentDoesNotExistException exception = new CommentDoesNotExistException(SOME_EXCEPTION, new Exception(SOME_EXCEPTION));

        assertThat(exception.getCause().getMessage()).isEqualTo(SOME_EXCEPTION);
        assertThat(exception.getMessage()).isEqualTo(SOME_EXCEPTION);
    }

    @Test
    public void shouldCommentDoesNotExistExceptionThrowable(){
        CommentDoesNotExistException exception = new CommentDoesNotExistException(new Exception(SOME_EXCEPTION));

        assertThat(exception.getCause().getMessage()).isEqualTo(SOME_EXCEPTION);
    }
}
