package com.epam.idea.rest.controller;

import com.epam.idea.core.model.User;
import com.epam.idea.core.service.UserService;
import com.epam.idea.rest.annotation.WebAppUnitTest;
import com.epam.idea.util.TestConnection;
import com.epam.idea.util.TestProviderSignInAttempt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;

import static com.epam.idea.builder.model.TestUserBuilder.aUser;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Ihar_Niakhlebau on 15-Jun-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppUnitTest
public class RegistrationControllerTest {

	@Autowired
	private UserService userServiceMock;

	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		Mockito.reset(this.userServiceMock);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void shouldRedirect() throws Exception {
		User user = aUser().build();

		ConnectionData connectionData = new ConnectionData("google",
				"providerUserId",
				"displayName",
				"profileUrl",
				"imageUrl",
				"accessToken",
				"secret",
				"refreshToken",
				1000L);


		UserProfile userProfile = new UserProfileBuilder()
				.setEmail("email")
				.setFirstName("firstName")
				.setLastName("lastName")
				.build();

		TestConnection connection = new TestConnection(connectionData, userProfile);
		TestProviderSignInAttempt signIn = new TestProviderSignInAttempt(connection);
		when(this.providerSignInUtils.getConnectionFromSession(any(WebRequest.class))).thenReturn(connection);
		when(this.userServiceMock.findUserOrRegisterNewUserAccount(any(User.class))).thenReturn(user);


		mockMvc.perform(get("/user/register", any(WebRequest.class)))
				.andExpect(status().isFound())
;
		verify(providerSignInUtils, times(1)).getConnectionFromSession(any(WebRequest.class));
		verify(userServiceMock, times(1)).findUserOrRegisterNewUserAccount(any(User.class));
		verify(providerSignInUtils, times(1)).doPostSignUp(any(String.class), any(WebRequest.class));
		verifyZeroInteractions(userServiceMock);
		verifyZeroInteractions(providerSignInUtils);
	}
}

