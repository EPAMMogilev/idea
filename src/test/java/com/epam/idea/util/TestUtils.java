package com.epam.idea.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

/**
 * {@code TestUtils} is a collection of utility methods for use in
 * unit and integration testing scenarios.
 */
public abstract class TestUtils {

	private static final char CHARACTER = 'a';
	private static final String EMAIL_SUFFIX = "@test.com";
	private static final String UTF_8 = "utf8";

	public static final String EMPTY = "";

	/** Prevent instantiation. */
	private TestUtils() {
		//empty
	}

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName(UTF_8)
	);

	public static String createStringWithLength(int length) {
		if (length > 0) {
			char[] array = new char[length];
			Arrays.fill(array, CHARACTER);
			return new String(array);
		}
		return EMPTY;
	}

	public static String createEmailWithLength(int length) {
		if (length > 0) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < length - EMAIL_SUFFIX.length(); i++) {
				builder.append(CHARACTER);
			}
			return builder.toString() + EMAIL_SUFFIX;
		}
		return EMPTY;
	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		Objects.requireNonNull(object, "Converted object must not be null");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
