package com.epam.idea.core.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
	public static String md5(String str) {

		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
				e.printStackTrace(); //use logger instead
				throw new RuntimeException("No Such Algorithm: MD5");
		}
		m.update(str.getBytes(),0,str.length());
		return new BigInteger(1,m.digest()).toString(16);
	}
}
