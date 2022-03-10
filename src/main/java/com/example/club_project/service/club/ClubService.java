package com.example.club_project.service.club;

import com.example.club_project.domain.Club;

import java.util.List;

public interface ClubService {

    Club register(String name, String address, String university, String description, String categoryName, String imageUrl);

    Club getClub(String name, String university);

    List<Club> getClubs(String university);

    List<Club> getClubs(List<String> categories, String university);

    boolean existed(String name, String university);

    long delete(String name, String university);

    //TODO: updateMethod
}
