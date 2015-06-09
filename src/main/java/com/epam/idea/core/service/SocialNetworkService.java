package com.epam.idea.core.service;

import com.epam.idea.core.model.SocialNetwork;
import com.epam.idea.core.model.User;
import org.springframework.social.google.api.plus.Person;

/**
 * Created by Ihar_Niakhlebau on 08-Jun-15.
 */
public interface SocialNetworkService {
	public User findOrCreateUserBySocialIdGoogle(Person person);
}
