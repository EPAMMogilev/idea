package com.epam.idea.core.service.impl;

import com.epam.idea.builder.model.TestCommentBuilder;
import com.epam.idea.core.model.Comment;
import com.epam.idea.core.repository.CommentRepository;
import com.epam.idea.core.service.CommentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;

/**
 * Created by Aliaksei_Liubetski on 2015-05-29.
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepositoryMock;

    @InjectMocks
    private CommentService commentService = new CommentServiceImpl();

    @Before
    public void setUp() throws Exception {
        Mockito.reset(this.commentRepositoryMock);
    }//setUp

    @Test
    public void shouldFindOneComment(){
        Comment comment = TestCommentBuilder.aComment().build();

        given(this.commentRepositoryMock.findOne(eq(comment.getId()))).willReturn(Optional.of(comment));

        Comment actual = this.commentService.findOne(comment.getId());

        assertThat(actual).isEqualTo(comment);
    }//shouldAddNewComment
}
