package com.example.club_project.repository;

import com.example.club_project.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query("select c from Category c where c.id in :categories")
    List<Category> findAllById(@Param("categories") List<Long> categories);

    @Query("select c from Category c where c.name in :categoryNames")
    List<Category> findAllNames(@Param("categoryNames") List<String> categoryNames);
}
