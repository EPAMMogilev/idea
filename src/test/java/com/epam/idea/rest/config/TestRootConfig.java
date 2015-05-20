package com.epam.idea.rest.config;

import com.epam.idea.core.service.CommentService;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.TagService;
import com.epam.idea.core.service.UserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestRootConfig {

	@Bean
	public UserService userServiceMock() {
		return Mockito.mock(UserService.class);
	}

	@Bean
	public TagService tagServiceMock() {
		return Mockito.mock(TagService.class);
	}

	@Bean
	public IdeaService ideaServiceMock() {
		return Mockito.mock(IdeaService.class);
	}

	@Bean
	public CommentService commentServiceMock() {
		return Mockito.mock(CommentService.class);
	}
}
