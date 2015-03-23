package com.epam.idea.rest.controller;

import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.builders.TestTagBuilder;
import com.epam.idea.core.service.TagService;
import com.epam.idea.core.service.exception.TagDoesNotExistException;
import com.epam.idea.rest.config.TestConfig;
import com.epam.idea.rest.config.WebAppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static com.epam.idea.rest.TestUtils.APPLICATION_JSON_UTF8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static com.epam.idea.rest.resource.support.JsonPropertyName.ID;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Ihar_Niakhlebau on 18-Mar-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class TagControllerClass {

    @Autowired
    private TagService tagServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        Mockito.reset(tagServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void shouldReturnAllFoundTags() throws Exception {
        Tag foundTag = TestTagBuilder.aTag().build();
        when(tagServiceMock.findAll()).thenReturn(Arrays.asList(foundTag));
        mockMvc.perform(get("/api/v1/tags")
                .accept(APPLICATION_JSON_UTF8)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0]." + ID).value(is(((int) foundTag.getId()))))
                .andExpect(jsonPath("$[0].name").value(is(foundTag.getName())))
                .andExpect(jsonPath("$[0].links", hasSize(0)));
        verify(tagServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(tagServiceMock);
    }

    @Test
    public void shouldReturnTagByIdWithHttpCode200() throws Exception {
        Tag foundTag = TestTagBuilder.aTag().build();
        when(tagServiceMock.findOne(foundTag.getId())).thenReturn(foundTag);
        mockMvc.perform(get("/api/v1/tags/{tagId}", foundTag.getId())
                .accept(APPLICATION_JSON_UTF8)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$." + ID).value(is(((int) foundTag.getId()))))
                .andExpect(jsonPath("$.name").value(is(foundTag.getName())))
                ;


        verify(tagServiceMock, times(1)).findOne(foundTag.getId());
        verifyNoMoreInteractions(tagServiceMock);
    }

    @Test
    public void shouldReturnErrorWithHttpStatus404WhenTagNotFound() throws Exception {
        when(tagServiceMock.findOne(TestTagBuilder.DEFAULT_ID)).thenThrow(new TagDoesNotExistException());
        mockMvc.perform(get("/api/v1/tags/{tagId}", TestTagBuilder.DEFAULT_ID)
                .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].logref").value(is(RestErrorHandler.TAG_NOT_FOUND_LOGREF)))
                .andExpect(jsonPath("$[0].message").value(is("Could not find tag")));
        ;
        verify(tagServiceMock, times(1)).findOne(TestTagBuilder.DEFAULT_ID);
        verifyNoMoreInteractions(tagServiceMock);
    }
}