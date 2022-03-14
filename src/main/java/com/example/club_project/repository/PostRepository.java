package com.example.club_project.repository;

import com.example.club_project.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByClub_IdOrderByIdDesc(Long id, Pageable pageable);

    @Query("SELECT p, u, COUNT(c) " +
            "FROM Post p " +
            "LEFT JOIN p.user u " +
            "LEFT JOIN Comment c ON c.post=p " +
            "WHERE p.club.id =:clubId " +
            "GROUP BY p")
    List<Object[]> getPostWithCommentCountByClubId(@Param("clubId") Long clubId, Pageable pageable);

    @Query("SELECT p, u, count(c) " +
            "FROM Post p LEFT JOIN p.user u " +
            "LEFT OUTER JOIN Comment c ON c.post = p " +
            "WHERE p.id =:postId")
    Object getPostById(@Param("postId") Long postId);

    @Query("SELECT p, c FROM Post p LEFT JOIN Comment c " +
            "ON c.post = p " +
            "WHERE p.id =:postId")
    List<Object[]> getPostWithComment(@Param("postId") Long postId);

    boolean existsById(Long id);
}
