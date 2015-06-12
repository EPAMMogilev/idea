package com.epam.idea.rest.config;

import javax.inject.Inject;
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
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

/**
 * Created by Ihar_Niakhlebau on 04-Jun-15.
 */
@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {

	public static String clientId = "133307390078-irhbk08u5omsjfra8qorte4qgkqsvuuv.apps.googleusercontent.com";
	public static String clientSecret = "_Ai9_dc-5en7Noiyf6IqKUMR";

	@Autowired
	private DataSource dataSource;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
		GoogleConnectionFactory googleConnectionFactory = new GoogleConnectionFactory(clientId,clientSecret);
		googleConnectionFactory.setScope("https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email");
		cfConfig.addConnectionFactory(googleConnectionFactory);

	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
	}

//	public ConnectionRepository getConnectionRepository() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication == null) {
//			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
//		}
//		return usersConnectionRepository().createConnectionRepository(authentication.getName());
//	}
//
//	public UsersConnectionRepository usersConnectionRepository() {
//		return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(),
//				textEncryptor);
//	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}



	@Bean
	public ConnectController connectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		ConnectController controller = new ConnectController(
				connectionFactoryLocator, connectionRepository);
		return new ConnectController(connectionFactoryLocator, connectionRepository);
	}
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator,UsersConnectionRepository connectionRepository) {
		ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
		return providerSignInUtils;
	}
}
