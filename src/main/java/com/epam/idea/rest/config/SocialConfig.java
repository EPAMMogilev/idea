package com.epam.idea.rest.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:/socialConfig.properties")
public class SocialConfig implements SocialConfigurer {

	public static String GOOGLE_CLIENT_ID = "socialConfig.googleClientId";
	public static String GOOGLE_CLIENT_SECRET = "socialConfig.googleClientSecret";
	public static String FACEBOOK_CLIENT_ID = "socialConfig.facebookClientId";
	public static String FACEBOOK_CLIENT_SECRET = "socialConfig.facebookClientSecret";
	public static String VK_CLIENT_ID = "socialConfig.vkClientId";
	public static String VK_CLIENT_SECRET = "socialConfig.vkClientSecret";
	public static String FACEBOOK_CLIENT_ID_LOCAL = "socialConfig.facebookClientIdLocal";
	public static String FACEBOOK_CLIENT_SECRET_LOCAL = "socialConfig.facebookClientSecretLocal";
	public static String VK_CLIENT_ID_LOCAL = "socialConfig.vkClientIdLocal";
	public static String VK_CLIENT_SECRET_LOCAL = "socialConfig.vkClientSecretLocal";

	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
		GoogleConnectionFactory googleConnectionFactory = new GoogleConnectionFactory(this.env.getRequiredProperty(GOOGLE_CLIENT_ID), this.env.getRequiredProperty(GOOGLE_CLIENT_SECRET));
		googleConnectionFactory.setScope("https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email");
		cfConfig.addConnectionFactory(googleConnectionFactory);
		FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(this.env.getRequiredProperty(FACEBOOK_CLIENT_ID), this.env.getRequiredProperty(FACEBOOK_CLIENT_SECRET));
		facebookConnectionFactory.setScope("email");
		cfConfig.addConnectionFactory(facebookConnectionFactory);
		VKontakteConnectionFactory vKontakteConnectionFactory = new VKontakteConnectionFactory(this.env.getRequiredProperty(VK_CLIENT_ID), this.env.getRequiredProperty(VK_CLIENT_SECRET));
		vKontakteConnectionFactory.setScope("email");
		cfConfig.addConnectionFactory(vKontakteConnectionFactory);
//		FacebookConnectionFactory facebookConnectionFactoryLocal = new FacebookConnectionFactory(this.env.getRequiredProperty(FACEBOOK_CLIENT_ID_LOCAL), this.env.getRequiredProperty(FACEBOOK_CLIENT_SECRET_LOCAL));
//		facebookConnectionFactoryLocal.setScope("email");
//		cfConfig.addConnectionFactory(facebookConnectionFactoryLocal);
//		VKontakteConnectionFactory vKontakteConnectionFactoryLocal = new VKontakteConnectionFactory(this.env.getRequiredProperty(VK_CLIENT_ID_LOCAL), this.env.getRequiredProperty(VK_CLIENT_SECRET_LOCAL));
//		vKontakteConnectionFactoryLocal.setScope("email");
//		cfConfig.addConnectionFactory(vKontakteConnectionFactoryLocal);

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
