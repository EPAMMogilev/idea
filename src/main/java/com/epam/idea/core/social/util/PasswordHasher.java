package com.epam.idea.core.social.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * Created by Ihar_Niakhlebau on 24-Jun-15.
 */
public class PasswordHasher {
	public static String md5(String str) {

		/*MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); //use logger instead
			throw new RuntimeException("No Such Algorithm: MD5");
		}
		m.update(str.getBytes(),0,str.length());
		return new BigInteger(1,m.digest()).toString(16);*/
		
		return new Md5PasswordEncoder().encodePassword(str, null);
	}
}
