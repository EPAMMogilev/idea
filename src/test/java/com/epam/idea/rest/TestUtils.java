package com.epam.idea.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class TestUtils {

	private static final char CHARACTER = 'a';
	private static final String EMAIL_SUFFIX = "@test.com";
	public static final String EMPTY = "";
	public static final String UTF_8 = "utf8";

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
		return "";
	}

	public static String createEmailWithLength(int length) {
		if (length > 0) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < length - EMAIL_SUFFIX.length(); i++) {
				builder.append(CHARACTER);
			}
			return builder.toString() + EMAIL_SUFFIX;
		}
		return "";
	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
