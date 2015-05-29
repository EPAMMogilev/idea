package com.epam.idea.core.exceptions;

import com.epam.idea.core.service.exception.TagDoesNotExistException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Aliaksei_Liubetski on 2015-05-29.
 */
public class ExceptionsTest {

    public static final String TEST_MESSAGE = "Test message";

    @Test
    public void TagDoesNotExistExceptionString(){
        try{
            throw new TagDoesNotExistException(TEST_MESSAGE);
        }catch (TagDoesNotExistException exc){
            assertEquals(exc.getMessage(), TEST_MESSAGE);
        }
    }//TagDoesNotExistExceptionString

    @Test
    public void TagDoesNotExistExceptionException(){
        try{
            throw new TagDoesNotExistException(new Exception(TEST_MESSAGE));
        }catch (TagDoesNotExistException exc){
            assertEquals(exc.getCause().getMessage(), TEST_MESSAGE);
        }
    }//TagDoesNotExistExceptionString

    @Test
    public void TagDoesNotExistExceptionStringAndException(){
        try{
            throw new TagDoesNotExistException(TEST_MESSAGE, new Exception());
        }catch (TagDoesNotExistException exc){
            assertEquals(exc.getMessage(), TEST_MESSAGE);
        }
    }//TagDoesNotExistExceptionString
}
