package com.example.club_project.service.clubjoinstate;

import com.example.club_project.domain.ClubJoinState;
import com.example.club_project.domain.JoinState;
import com.example.club_project.repository.ClubJoinStateRepository;
import com.example.club_project.service.club.ClubService;
import com.example.club_project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubJoinStateServiceImpl implements ClubJoinStateService {

    private final ClubJoinStateRepository clubJoinStateRepository;
    private final UserService userService;
    private final ClubService clubService;

    public ClubJoinState register(Long clubId, Long userId, JoinState joinState) {
        return null;
    }

    public ClubJoinState getJoinState(Long id) {
        return null;
    }

    public ClubJoinState getJoinState(Long clubId, Long userId) {
        return null;
    }

    public List<ClubJoinState> getJoinStatesByClub(Long clubId, Pageable pageable) {
        return null;
    }

    public List<ClubJoinState> getJoinStatesByUser(Long userId, Pageable pageable) {
        return null;
    }

    public List<ClubJoinState> getClubJoinStates(String university, Pageable pageable) {
        return null;
    }

    public ClubJoinState update(Long clubId, Long userId, boolean isUsed) {
        return null;
    }

    public ClubJoinState update(Long clubId, Long userId, JoinState joinState) {
        return null;
    }

    public ClubJoinState update(Long clubId, Long userId, boolean isUsed, JoinState joinState) {
        return null;
    }

    public boolean existed(Long userId, Long clubId) {
        return false;
    }

    public boolean isClubUser(Long userId, Long clubId) {
        return false;
    }
}
