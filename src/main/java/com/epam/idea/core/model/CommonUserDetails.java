package com.epam.idea.core.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CommonUserDetails extends SocialUser implements Principal {

	public static Builder getBuilder() {
		return new Builder();
	}

	private Long id;

	private Role role;

	private String email;

	private SocialMediaService socialSignInProvider;

	public CommonUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public SocialMediaService getSocialSignInProvider() {
		return socialSignInProvider;
	}

	public void setSocialSignInProvider(SocialMediaService socialSignInProvider) {
		this.socialSignInProvider = socialSignInProvider;
	}

	@Override
	public String getName() {
		return id.toString();
	}

	//Getters are omitted for the sake of clarity.

	public static class Builder {

		private Long id;

		private String username;

		private String firstName;

		private String lastName;

		private String fullName;

		private String email;

		private String password;

		private Role role;

		private SocialMediaService socialSignInProvider;

		private Set<GrantedAuthority> authorities;

		public Builder() {
			this.authorities = new HashSet<>();
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}


		public Builder password(String password) {
			if (password == null) {
				password = "SocialUser";
			}

			this.password = password;
			return this;
		}

		public Builder role(Role role) {
			this.role = role;

			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
			this.authorities.add(authority);

			return this;
		}

		public Builder socialSignInProvider(SocialMediaService socialSignInProvider) {
			this.socialSignInProvider = socialSignInProvider;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public CommonUserDetails build() {
			CommonUserDetails user = new CommonUserDetails(username, password, authorities);
			user.id = id;
			user.role = role;
			user.socialSignInProvider = socialSignInProvider;
			user.email = email;
			return user;
		}
	}
}
