package com.example.club_project.service.club;

import com.example.club_project.controller.club.ClubDTO;
import com.example.club_project.domain.Club;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubService {

    /**
     * DTO Region (for other controllers)
     */
    ClubDTO.Response registerClub(String name, String address, String university, String description, Long categoryId);

    ClubDTO.Response updateClub(Long id, String name, String address, String university, String description, Long categoryId);

    ClubDTO.Response convertToDTO(Club club);

    /**
     * Entity Region (for other services)
     */
    Club register(String name, String address, String university, String description, Long categoryId);

    Club getClub(Long id);

    /* 정확히 동아리명이 일치하는 경우만 리턴 */
    Club getClub(String name, String university);

    List<Club> getClubs(String university, Pageable pageable);

    /* 동아리명이 검색어를 포함하는 경우도 리턴 (ex. clubName = ABCD & ABCDE, 검색어 = BC인 경우 ABCD, ABCDE 모두 결과값으로 리턴 */
    List<Club> getClubs(String name, String university, Pageable pageable);

    List<Club> getClubs(List<Long> categories, String university, Pageable pageable);

    List<Club> getClubs(List<Long> categories, String university, String name, Pageable pageable);

    Club update(Long id, String name, String address, String university, String description, Long categoryId);

    void updateImage(Long id, String imageUrl);

    /**
     * Common Region
     */
    void delete(Long id);

    boolean existed(String name, String university);
}
