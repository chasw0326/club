package com.example.club_project.repository;

import com.example.club_project.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query("DELETE FROM Likes l WHERE l.board.bno =:boardId AND l.member.mno =:memberId")
    void unlike(@Param("boardId") Long boardId, @Param("memberId") Long memberId);
}
