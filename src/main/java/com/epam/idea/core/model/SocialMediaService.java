package com.epam.idea.core.model;

/**
 * Created by Ihar_Niakhlebau on 08-Jun-15.
 */
public enum SocialMediaService {
	NONE(""), GOOGLE("GOOGLE"), FACEBOOK("FACEBOOK"), VKONTAKTE("VKONTAKTE");

	SocialMediaService(String socialName) {
		this.socialName = socialName;
	}

	public String getSocialName() {
		return socialName;
	}

	private String socialName;
}
