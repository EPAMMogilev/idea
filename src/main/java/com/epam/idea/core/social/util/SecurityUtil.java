package com.epam.idea.core.social.util;

import com.epam.idea.core.model.CommonUserDetails;
import com.epam.idea.core.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

	public static void logInUser(User user) {
		CommonUserDetails userDetails = CommonUserDetails.getBuilder()
				.id(user.getId())
				.password(user.getPassword())
				.roles(user.getRoles())
				.socialSignInProvider(user.getSocialMediaService())
				.username(user.getUsername())
				.email(user.getEmail())
				.build();

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
