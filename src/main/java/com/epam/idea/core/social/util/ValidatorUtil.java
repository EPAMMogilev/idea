package com.epam.idea.core.social.util;

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class ValidatorUtil {

	public static void addValidationError(String field, ConstraintValidatorContext context) {
		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addPropertyNode(field)
				.addConstraintViolation();
	}

	public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field f = object.getClass().getDeclaredField(fieldName);
		f.setAccessible(true);
		return f.get(object);
	}
}
