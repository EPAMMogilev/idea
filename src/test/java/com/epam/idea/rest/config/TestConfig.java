package com.epam.idea.rest.config;

import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.service.CommentService;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.RoleService;
import com.epam.idea.core.service.TagService;
import com.epam.idea.core.service.UserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

	@Bean
	public UserService userServiceMock() {
		return Mockito.mock(UserService.class);
	}

	@Bean
	public TagService tagServiceMock() {
		return Mockito.mock(TagService.class);
	}

	@Bean
	public RoleService roleServiceMock() {
		return Mockito.mock(RoleService.class);
	}

	@Bean
	public IdeaService ideaServiceMock() {
		return Mockito.mock(IdeaService.class);
	}

	@Bean
	public CommentService commentServiceMock() {
		return Mockito.mock(CommentService.class);
	}

	@Bean
	public UserRepository userRepositoryMock() {
		return Mockito.mock(UserRepository.class);
	}
}
