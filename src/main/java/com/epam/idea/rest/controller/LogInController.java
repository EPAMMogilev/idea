package com.epam.idea.rest.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import javax.validation.Valid;

import com.epam.idea.core.model.CommonUserDetails;
import com.epam.idea.core.model.User;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.service.exception.DuplicateUserException;
import com.epam.idea.core.service.exception.UserNotFoundException;
import com.epam.idea.core.social.util.SecurityUtil;
import com.epam.idea.rest.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;


/**
 * Created by Ihar_Niakhlebau on 09-Jun-15.
 */
@RestController
public class LogInController {

	@Autowired(required=false)
	ProviderSignInUtils providerSignInUtils;

	@Autowired
	public UserService userService;

	@RequestMapping("/user")
	public Object user(Authentication user) {
		return user.getPrincipal();
	}

	@RequestMapping(value ="/user/register", method = RequestMethod.POST)
	public String registerUserAccount(@Valid @RequestBody final UserResource ideaRes, WebRequest request) {
//		if (result.hasErrors()) {
//			return "user/registrationForm";
//		}

		User registered = createUserAccount(ideaRes);

//		if (registered == null) {
//			return "user/registrationForm";
//		}
		SecurityUtil.logInUser(registered);
		providerSignInUtils.doPostSignUp(registered.getEmail(), request);

		return "redirect:/";
	}

	private User createUserAccount(UserResource userAccountData) {
		User registered = null;
		User user = userAccountData.toUser();
		user.setPassword(md5(user.getPassword()));
		try{
			userService.findRegisteredUserByEmail(user.getEmail());
			new DuplicateUserException(user.getEmail());
		}
		catch (UserNotFoundException ex) {
			registered = userService.save(user);
		}

		return registered;
	}

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
