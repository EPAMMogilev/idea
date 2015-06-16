package com.epam.idea.rest.controller;

import com.epam.idea.core.model.SocialMediaService;
import com.epam.idea.core.model.User;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.social.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

@Controller
@SessionAttributes("user")
public class RegistrationController {

	@Autowired(required=false)
	ProviderSignInUtils providerSignInUtils;

	@Autowired
	private UserService userService;

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signupForm(WebRequest request) {
		return "redirect:/user/register";

	}

	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	public String loginOrRegistrationNewUser(WebRequest request) {
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);

		User registration = createUserDTO(connection);

		User registered = userService.findUserOrRegisterNewUserAccount(registration);

//		User registered = findUserAccount(registration);
//
//		if (registered == null) {
//			registered = createUserAccount(registration);
//		}
		SecurityUtil.logInUser(registered);
		providerSignInUtils.doPostSignUp(registered.getEmail(), request);

		return "redirect:/";
	}
//
//	private RegistrationForm createRegistrationDTO(Connection<?> connection) {
//		RegistrationForm dto = new RegistrationForm();
//
//		if (connection != null) {
//			UserProfile socialMediaProfile = connection.fetchUserProfile();
//			dto.setEmail(socialMediaProfile.getEmail());
//			dto.setFirstName(socialMediaProfile.getFirstName());
//			dto.setLastName(socialMediaProfile.getLastName());
//			dto.setFullName(socialMediaProfile.getUsername());
//			ConnectionKey providerKey = connection.getKey();
//			dto.setSignInProvider(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));
//		}
//
//		return dto;
//	}

	private User createUserDTO(Connection<?> connection) {

		User dto = new User();
		if (connection != null) {
			UserProfile socialMediaProfile = connection.fetchUserProfile();
			dto.setEmail(socialMediaProfile.getEmail());
			dto.setUsername(socialMediaProfile.getFirstName() + " " + socialMediaProfile.getLastName());
			ConnectionKey providerKey = connection.getKey();
			dto.setSocialMediaService(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));
		}

		return dto;
	}





}
