package com.example.club_project.service.club;

import com.example.club_project.controller.club.ClubDTO;
import com.example.club_project.domain.Club;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubService {

    /**
     * DTO Region (for other controllers)
     */
    ClubDTO.Response registerClub(String name, String address, String university, String description, String categoryName, String imageUrl);

    ClubDTO.Response getClubDto(String name, String university);

    ClubDTO.Response getClubDto(Long id);

    List<ClubDTO.Response> getClubDtos(List<String> categories, String university, Pageable pageable);

    List<ClubDTO.Response> getClubDtos(String university, Pageable pageable);

    ClubDTO.Response updateClub(Long id, String name, String address, String university, String description, String categoryName, String imageUrl);

    /**
     * Entity Region (for other services)
     */
    Club register(String name, String address, String university, String description, String categoryName, String imageUrl);

    Club getClub(String name, String university);

    Club getClub(Long id);

    List<Club> getClubs(List<String> categories, String university, Pageable pageable);

    List<Club> getClubs(String university, Pageable pageable);

    Club update(Long id, String name, String address, String university, String description, String categoryName, String imageUrl);

    /**
     * Common Region
     */
    void delete(Long id);

    boolean existed(String name, String university);
}
