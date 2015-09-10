package com.epam.idea.core.repository;

import java.util.List;
import java.util.Optional;

import com.epam.idea.core.model.Idea;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface IdeaRepository extends BaseRepository<Idea, Long> {

    @Query("select i from Idea i")
    List<Idea> findAll(Pageable pageable);

    @Query("select i from Idea i where (i.state != 'Draft' and i.state != 'Deleted')")
    List<Idea> findAllVisible(Pageable pageable);

    @Query("select i from Idea i where UPPER(i.title) like %?1% or UPPER(i.description) like %?1%")
    List<Idea> findAllByQuery(Pageable pageable, String query);

    @Query("select i from Idea i where (i.state != 'Draft' and i.state != 'Deleted') and (UPPER(i.title) like %?1% or UPPER(i.description) like %?1%)")
    List<Idea> findAllVisibleByQuery(Pageable pageable, String query);

    /**
     * Return a list of ideas which marked by the tag with given id, or an empty
     * list if no ideas marked by tag..
     *
     * @param tagId
     *            The id of the tag.
     * @return All the ideas marked by the tag.
     */

    @Query("select i from Idea i left join i.relatedTags t where t.id = ?1")
    List<Idea> findAllByTagId(Pageable pageable, Long tagId);

    @Query("select i from Idea i left join i.relatedTags t where t.id = ?1 and (i.state != 'Draft' and i.state != 'Deleted')")
    List<Idea> findAllVisibleByTagId(Pageable pageable, Long tagId);

    @Query("select i from Idea i left join i.relatedTags t where t.id = ?1 and (UPPER(i.title) like %?2% or UPPER(i.description) like %?2%)")
    List<Idea> findAllByTagIdAndByQuery(Pageable pageable, Long tagId, String query);

    @Query("select i from Idea i left join i.relatedTags t where t.id = ?1 and (UPPER(i.title) like %?2% or UPPER(i.description) like %?2%) and (i.state != 'Draft' and i.state != 'Deleted')")
    List<Idea> findAllVisibleByTagIdAndByQuery(Pageable pageable, Long tagId, String query);

    @Query("select i from Idea i left join i.likedUsers u where i.id = ?1 and u.id = ?#{ principal?.id }")
    Idea findByIdAndLikedByCurrentUser(long ideaId);

    @Query("select i from Idea i where i.author.id = ?1")
    List<Idea> findAllByUserId(Pageable pageable, Long userId);

    @Query("select i from Idea i left join i.relatedTags t where i.author.id = ?1 and t.id = ?2")
    List<Idea> findAllByUserIdAndByTagId(Pageable pageable, Long userId, Long tagId);

    @Query("select i from Idea i where i.author.id = ?1 and (UPPER(i.title) like %?2% or UPPER(i.description) like %?2%)")
    List<Idea> findAllByUserIdAndByQuery(Pageable pageable, Long userId, String query);

    @Query("select i from Idea i left join i.relatedTags t where i.author.id = ?1 and t.id = ?2 and (UPPER(i.title) like %?3% or UPPER(i.description) like %?3%)")
    List<Idea> findAllByUserIdByTagIdAndByQuery(Pageable pageable, Long userId, Long tagId, String query);
}