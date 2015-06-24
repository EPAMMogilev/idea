package com.epam.idea.rest.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Ihar_Niakhlebau on 09-Jun-15.
 */
@RestController
public class LogInController {

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
}
