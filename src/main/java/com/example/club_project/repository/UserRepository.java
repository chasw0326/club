package com.example.club_project.repository;

import com.example.club_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {



    boolean existsByEmail(String email);
}
