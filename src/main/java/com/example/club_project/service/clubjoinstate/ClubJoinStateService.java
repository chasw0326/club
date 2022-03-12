package com.example.club_project.service.clubjoinstate;

import com.example.club_project.domain.ClubJoinState;
import com.example.club_project.domain.JoinState;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubJoinStateService {

    /**
     * Entity Region (for other services)
     */
    ClubJoinState register(Long clubId, Long userId, JoinState joinState);

    ClubJoinState getJoinState(Long id);

    ClubJoinState getJoinState(Long clubId, Long userId);

    List<ClubJoinState> getJoinStatesByClub(Long clubId, Pageable pageable);

    List<ClubJoinState> getJoinStatesByUser(Long userId, Pageable pageable);

    List<ClubJoinState> getClubJoinStates(String university, Pageable pageable);

    ClubJoinState update(Long clubId, Long userId, boolean isUsed);

    ClubJoinState update(Long clubId, Long userId, JoinState joinState);

    ClubJoinState update(Long clubId, Long userId, boolean isUsed, JoinState joinState);

    /**
     * Common Region
     */
    boolean existed(Long userId, Long clubId);

    boolean isClubUser(Long userId, Long clubId);
}
