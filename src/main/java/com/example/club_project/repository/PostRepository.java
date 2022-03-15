package com.example.club_project.repository;

import com.example.club_project.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByClub_IdOrderByIdDesc(Long id, Pageable pageable);

    List<Post> findAllByUser_IdAndClub_Id(Long userId, Long clubId);

    /**
     * 글, 글쓴이, 댓글 개수를 가져온다.
     * 클럽id에 따라 글목록을 보여준다.
     */
    @Query("SELECT p, u, COUNT(c) " +
            "FROM Post p " +
            "LEFT JOIN p.user u " +
            "LEFT JOIN Comment c ON c.post=p " +
            "WHERE p.club.id =:clubId " +
            "GROUP BY p")
    List<Object[]> getPostWithCommentCountByClubId(@Param("clubId") Long clubId, Pageable pageable);

    /**
     * 위 메서드랑 같고, 내가 작성한글 목록
     */
    @Query("SELECT p, u, COUNT(c) " +
            "FROM Post p " +
            "LEFT JOIN p.user u " +
            "LEFT JOIN Comment c ON c.post=p " +
            "WHERE p.user.id =:userId " +
            "GROUP BY p")
    List<Object[]> getPostWithCommentCountByUserId(@Param("userId") Long userId, Pageable pageable);


    /**
     * 위와 같지만
     * 글 하나만 가져오기
     */
    @Query("SELECT p, u, count(c) " +
            "FROM Post p LEFT JOIN p.user u " +
            "LEFT OUTER JOIN Comment c ON c.post = p " +
            "WHERE p.id =:postId")
    Object getPostById(@Param("postId") Long postId);

    boolean existsById(Long id);
}