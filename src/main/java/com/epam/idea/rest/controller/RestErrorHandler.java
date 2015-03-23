package com.epam.idea.rest.controller;

import com.epam.idea.core.service.exception.IdeaNotFoundException;
import com.epam.idea.core.service.exception.TagDoesNotExistException;
import com.epam.idea.core.service.exception.UserNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestErrorHandler {

	public static final String USER_NOT_FOUND_LOGREF = "error";
	public static final String IDEA_NOT_FOUND_LOGREF = "error";
    public static final String TAG_NOT_FOUND_LOGREF = "error";

	@ResponseBody
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public VndErrors userNotFoundExceptionHandler(final UserNotFoundException ex) {
		return new VndErrors(USER_NOT_FOUND_LOGREF, ex.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(IdeaNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public VndErrors ideaNotFoundExceptionHandler(final IdeaNotFoundException ex) {
		return new VndErrors(IDEA_NOT_FOUND_LOGREF, ex.getMessage());
	}

    @ResponseBody
    @ExceptionHandler(TagDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public VndErrors tagNotFoundExceptionHandler(final TagDoesNotExistException ex) {
        return new VndErrors(TAG_NOT_FOUND_LOGREF, ex.getMessage());
    }

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public VndErrors validationErrorHandler(final MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<VndErrors.VndError> vndErrorList = result.getFieldErrors()
				.stream()
				.map(error -> new VndErrors.VndError(error.getField(), error.getDefaultMessage()))
				.collect(Collectors.toList());
		return new VndErrors(vndErrorList);
	}
}
