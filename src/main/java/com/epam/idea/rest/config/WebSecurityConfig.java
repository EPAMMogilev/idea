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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserRepository userRepository;


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsService()).passwordEncoder(new Md5PasswordEncoder())

		;
	}

	protected void configure(HttpSecurity http) throws Exception {
		http
				.formLogin()//.loginProcessingUrl("/pages/login.html")
				.loginPage("/pages/login.html")
				.permitAll()
				.and()
				.httpBasic()
				.and()
				.authorizeRequests()
				.antMatchers("/index.html", "/", "/css/**", "/js/**", "/bower_components/**", "/templates/**", "/pages/login.html", "/pages/app.html", "/pages/details.html", "/fonts/**", "/images/**", "/api/**", "/auth/**", "/signup", "/user/*")
				.permitAll()
				.anyRequest().authenticated()
				.and()
				.logout().deleteCookies("JSESSIONID").logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
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

	@Bean
	public SocialUserDetailsService socialUserDetailsService() {
		return new SocialUserDetailsServiceImpl(userDetailsService());
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl(userRepository);
	}
}
