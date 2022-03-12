package com.example.club_project.repository;

import com.example.club_project.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByClub_IdOrderByIdDesc(Long id, Pageable pageable);

    boolean existsById(Long id);
}
