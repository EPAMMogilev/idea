package com.epam.idea.core.service.impl;

import com.epam.idea.builder.model.TestCommentBuilder;
import com.epam.idea.builder.model.TestIdeaBuilder;
import com.epam.idea.builder.model.TestUserBuilder;
import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import com.epam.idea.core.repository.CommentRepository;
import com.epam.idea.core.repository.IdeaRepository;
import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.service.CommentService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Aliaksei_Liubetski on 2015-05-29.
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepositoryMock;

    @InjectMocks
    private CommentService commentService = new CommentServiceImpl();

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private IdeaRepository ideaRepositoryMock;

    @Before
    public void setUp() throws Exception {
        Mockito.reset(this.commentRepositoryMock);
    }//setUp

    @Test
    public void shouldSaveOneComment(){
        Comment comment = TestCommentBuilder.aComment().build();

        this.commentService.save(comment);

        ArgumentCaptor<Comment> userComment = ArgumentCaptor.forClass(Comment.class);
        verify(this.commentRepositoryMock, times(1)).save(userComment.capture());
        verifyNoMoreInteractions(this.commentRepositoryMock);

        Comment commentArgument = userComment.getValue();
        assertThat(commentArgument.getBody()).isEqualTo(comment.getBody());
        assertThat(commentArgument.getSubject()).isEqualTo(comment.getSubject());
        assertThat(commentArgument.getId()).isEqualTo(comment.getId());
    }//shouldAddNewComment

    @Test
    public void shouldFindOneComment(){
        Comment comment = TestCommentBuilder.aComment().build();

        given(this.commentRepositoryMock.findById(eq(comment.getId()))).willReturn(Optional.of(comment));

        Comment actual = this.commentService.findOne(comment.getId());


        actual.preUpdate();
        actual.prePersist();

        assertThat(actual).isEqualTo(comment);
        assertThat(actual.toString()).isNotNull();
    }//shouldAddNewComment

    @Test
    public  void shouldfindCommentsByUserIdComment(){
        User userToFind = TestUserBuilder.aUser().build();

        //findCommentsByUserId
        List<Comment> comments = this.commentService.findCommentsByUserId(userToFind.getId());
        assertThat(comments).isNotNull();
    }//shouldfindCommentsByUserIdComment

    @Test
    public  void shouldfindCommentsByIdeaIdComment(){
        Idea ideaToFind = TestIdeaBuilder.anIdea().build();

        //findCommentsByIdeaId
        Pageable defaultPageRequest = PageRequest.of(0, 500);
        List<Comment> comments = this.commentService.findCommentsByIdeaId(defaultPageRequest, ideaToFind.getId());
        assertThat(comments).isNotNull();
    }//shouldfindCommentsByIdeaIdComment

    @Test
    public void shouldChangeLikedForUserAndComment_whenLikedIsFalse() {
        final long commentId = 1L;
        final User user = new User();
        final Comment comment = TestCommentBuilder.aComment().withId(commentId).build();

        final int ratingBefore = comment.getRating();
        final int likedUsersBefore = comment.getLikedUsers().size();

        given(commentRepositoryMock.findByIdAndLikedByCurrentUser(commentId)).willReturn(null);
        given(userRepositoryMock.findCurrentUser()).willReturn(user);
        given(commentRepositoryMock.findById(commentId)).willReturn(Optional.of(comment));
        given(commentRepositoryMock.save(comment)).willReturn(comment);

        final Comment changedLikeComment = this.commentService.changeCommentLike(commentId);

        assertThat(changedLikeComment.getLikedUsers().size()).isEqualTo(likedUsersBefore + 1);
        assertThat(changedLikeComment.getRating()).isEqualTo(ratingBefore + 1);
    }

    @Test
    public void shouldChangeLikedForUserAndIdea_whenLikedIsTrue() {
        final long commentId = 1L;
        final User user = new User();
        final Comment likedComment = TestCommentBuilder.aComment().withId(commentId).withLikedUser(user).withLiked(true).build();

        final int ratingBefore = likedComment.getRating();
        final int likedUsersBefore = likedComment.getLikedUsers().size();

        given(commentRepositoryMock.findByIdAndLikedByCurrentUser(commentId)).willReturn(likedComment);
        given(userRepositoryMock.findCurrentUser()).willReturn(user);
        given(commentRepositoryMock.save(likedComment)).willReturn(likedComment);

        final Comment changedLikeComment = this.commentService.changeCommentLike(commentId);

        assertThat(changedLikeComment.getLikedUsers().size()).isEqualTo(likedUsersBefore - 1);
        assertThat(changedLikeComment.getRating()).isEqualTo(ratingBefore - 1);
    }

    @Test
    public void shouldCreateOneComment(){
        Comment comment = TestCommentBuilder.aComment().build();
        final Long ideaId = 1L;
        final User user = TestUserBuilder.aUser().build();
        final Optional<Idea> subject = Optional.of(new Idea());

        given(this.commentRepositoryMock.save(comment)).willReturn(comment);
        given(userRepositoryMock.findCurrentUser()).willReturn(user);
        given(ideaRepositoryMock.findById(ideaId)).willReturn(subject);

        Comment actualComment = this.commentService.create(comment, ideaId);

        assertThat(actualComment.getBody()).isEqualTo(comment.getBody());
        assertThat(actualComment.getSubject()).isEqualTo(subject.get());
        assertThat(actualComment.getAuthor()).isEqualTo(user);
        assertThat(actualComment.getId()).isEqualTo(comment.getId());
    }
}
