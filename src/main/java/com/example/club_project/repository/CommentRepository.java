package com.example.club_project.repository;

import com.example.club_project.domain.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 글에서는 댓글을 오름차순으로
     */
    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Comment> getAllByPost_IdOrderByIdAsc(Long postId);

    /**
     * 내가쓴 댓글 목록에서는 내림차순으로
     */
    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Comment> getAllByUser_IdOrderByIdDesc(Long userId);
}
