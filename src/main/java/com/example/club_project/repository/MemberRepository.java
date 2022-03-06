package com.example.club_project.repository;

import com.example.club_project.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Member m WHERE m.username =:username")
    Optional<Member> findByUsername(@Param("username") String username);

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Member m WHERE m.email =:email")
    Optional<Member> findByEmail(@Param("email") String email);

    Boolean existsByEmail(String email);

}
