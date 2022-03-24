package com.example.club_project.repository;

import com.example.club_project.domain.Category;
import com.example.club_project.domain.Club;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query("select c from Club c " +
                        "join fetch c.category " +
                    "where c.id = :id")
    Optional<Club> findById(@Param("id") Long id);

    /**
     * 이름이 정확히 일치하는 Club 정보만 리턴한다.
     */
    @Query("select c from Club c " +
                        "join fetch c.category " +
                    "where c.name = :name " +
                        "and c.university = :university")
    Optional<Club> findByNameAndUniversity(@Param("name") String name, @Param("university") String university);

    @Query("select c from Club c " +
                        "join fetch c.category " +
                    "where c.university = :university")
    List<Club> findAllByUniversity(@Param("university") String university, Pageable pageable);

    @Query("select c from Club c " +
            "join fetch c.category " +
            "where c.name like %:name% " +
            "and c.university = :university")
    List<Club> findAllByNameAndUniversity(@Param("name") String name, @Param("university") String university, Pageable pageable);

    @Query("select c from Club c " +
                        "join fetch c.category " +
                    "where c.university = :university " +
                        "and c.category in :categories")
    List<Club> findAll(@Param("categories") List<Category> categories,
                       @Param("university") String university,
                       Pageable pageable);

    @Query("select c from Club c " +
                        "join fetch c.category " +
                    "where c.name like %:name% " +
                        "and c.university = :university " +
                        "and c.category in :categories")
    List<Club> findAll(@Param("categories") List<Category> categories,
                       @Param("name") String name,
                       @Param("university") String university,
                       Pageable pageable);

    boolean existsByNameAndUniversity(String name, String university);
}
