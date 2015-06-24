package com.epam.idea.rest.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.vkontakte.connect.VKontakteConnectionFactory;

/**
 * Created by Ihar_Niakhlebau on 04-Jun-15.
 */
@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {

	public static String googleClientId = "312001620105-udtemaddk1ii34rqq0cs07lso3co1gbl.apps.googleusercontent.com";
	public static String googleClientSecret = "CTK9wEzU7VePQS7PhNcbwd3E";
	public static String facebookClientId = "1461506590828890";
	public static String facebookClientSecret = "1dc9045cb99ae97b3686cb10648b29b8";
	public static String vkClientId = "4960267";
	public static String vkClientSecret = "uu1iDGVhSi8h6EGsL0Gm";

	@Autowired
	private DataSource dataSource;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
		GoogleConnectionFactory googleConnectionFactory = new GoogleConnectionFactory(googleClientId, googleClientSecret);
		googleConnectionFactory.setScope("https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email");
		cfConfig.addConnectionFactory(googleConnectionFactory);
		FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(facebookClientId, facebookClientSecret);
		facebookConnectionFactory.setScope("email");
		cfConfig.addConnectionFactory(facebookConnectionFactory);
		VKontakteConnectionFactory vKontakteConnectionFactory = new VKontakteConnectionFactory(vkClientId, vkClientSecret);
		vKontakteConnectionFactory.setScope("email");
		cfConfig.addConnectionFactory(vKontakteConnectionFactory);

	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Bean
	public ConnectController connectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		return new ConnectController(connectionFactoryLocator, connectionRepository);
	}

	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator,UsersConnectionRepository connectionRepository) {
		ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
		return providerSignInUtils;
	}
}
