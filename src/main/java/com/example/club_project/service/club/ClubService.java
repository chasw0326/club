package com.example.club_project.service.club;

import com.example.club_project.controller.club.ClubDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubService {

    ClubDTO.Response register(String name, String address, String university, String description, String categoryName, String imageUrl);

    ClubDTO.Response update(Long id, String name, String address, String university, String description, String categoryName, String imageUrl);

    ClubDTO.Response getClub(Long id);

    ClubDTO.Response getClub(String name, String university);

    List<ClubDTO.Response> getClubs(String university, Pageable pageable);

    List<ClubDTO.Response> getClubs(List<String> categories, String university, Pageable pageable);

    boolean existed(String name, String university);

    void delete(Long id);
}
