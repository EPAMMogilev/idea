package com.epam.idea.rest.controller;

import javax.validation.Valid;

import com.epam.idea.core.model.User;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.social.util.SecurityUtil;
import com.epam.idea.rest.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
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

		User registered = userService.createUserAccountAndReturnIt(ideaRes.toUser());

		SecurityUtil.logInUser(registered);
		providerSignInUtils.doPostSignUp(registered.getEmail(), request);

		return "redirect:/";
	}
}
