package com.epam.idea.core.social.util;
import com.epam.idea.core.model.CommonUserDetails;
import com.epam.idea.core.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

	public static void logInUser(User user) {
		CommonUserDetails userDetails = CommonUserDetails.getBuilder()
//				.firstName(user.getFirstName())
//				.lastName(user.getUsername())
//				.role(user.getRole())
				.fullName(user.getUsername())
				.id(user.getId())
				.password(user.getPassword())
				.socialSignInProvider(user.getSocialMediaService())
				.username(user.getEmail())
				.build();

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
