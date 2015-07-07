package com.epam.idea.rest.config;

import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.service.impl.UserDetailsServiceImpl;
import com.epam.idea.core.service.impl.SocialUserDetailsServiceImpl;
import com.epam.idea.rest.resource.filters.CsrfHeaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	UserRepository userRepository;

	@Bean
	public SocialUserDetailsService socialUserDetailsService() { return new SocialUserDetailsServiceImpl(userDetailsService()); }

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl(userRepository);
	}

	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() { return new SecurityEvaluationContextExtension(); }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsService()).passwordEncoder(new Md5PasswordEncoder());
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		protected void configure(HttpSecurity http) throws Exception {
			http
					.formLogin()
					.loginPage("/login")
					.permitAll()
				.and()
					.httpBasic()
				.and()
					.authorizeRequests()
					.antMatchers("/index.html", "/", "/css/**", "/js/**", "/bower_components/**", "/templates/**", "/pages/login.html", "/pages/registration.html", "/pages/app.html", "/pages/details.html", "/fonts/**", "/images/**", "/api/**", "/auth/**", "/signup", "/user/*", "/login", "/languages/*")
					.permitAll()
					.anyRequest().authenticated()
				.and()
					.logout().deleteCookies("JSESSIONID").logoutUrl("/logout").logoutSuccessUrl("/")
				.and()
					.apply(new SpringSocialConfigurer())
				.and()
					.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
					.csrf().csrfTokenRepository(csrfTokenRepository());

		}

		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName("X-XSRF-TOKEN");
			return repository;
		}
	}
}