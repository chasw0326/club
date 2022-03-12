package com.example.club_project.repository;

import com.example.club_project.domain.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id =:postId")
    Long getCountByPostId(@Param("postId") Long postId);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Comment> getAllByPost_IdOrderByIdAsc(Long postId);
}
