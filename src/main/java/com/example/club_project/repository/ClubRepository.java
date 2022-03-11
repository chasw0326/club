package com.example.club_project.repository;

import com.example.club_project.domain.Category;
import com.example.club_project.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query("select c from Club c join fetch c.category where c.name = :name and c.university = :university")
    Optional<Club> findByNameAndUniversity(@Param("name") String name, @Param("university") String university);

    @Query("select c from Club c join fetch c.category where c.university = :university")
    List<Club> findAllByUniversity(@Param("university") String university);

    @Query("select c from Club c join fetch c.category where c.category in :categories and c.university = :university")
    List<Club> findAll(@Param("categories") List<Category> categories, @Param("university") String university);

    boolean existsByNameAndUniversity(String name, String university);

    long deleteByNameAndUniversity(String name, String university);
}
