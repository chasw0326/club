package com.example.club_project.service.clubjoinstate;

import com.example.club_project.domain.Club;
import com.example.club_project.domain.ClubJoinState;
import com.example.club_project.domain.JoinState;
import com.example.club_project.domain.User;
import com.example.club_project.repository.ClubJoinStateRepository;
import com.example.club_project.service.club.ClubService;
import com.example.club_project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ClubJoinStateServiceImpl implements ClubJoinStateService {

    private final ClubJoinStateRepository clubJoinStateRepository;
    private final UserService userService;
    private final ClubService clubService;

    /**
     * Common Region
     */
    @Override
    @Transactional
    public ClubJoinState register(long userId, long clubId, int joinStateCode) {

        User user = userService.getUser(userId);
        Club club = clubService.getClub(clubId);
        JoinState joinState = JoinState.from(joinStateCode);

        ClubJoinState clubJoinState = ClubJoinState.builder()
                .club(club)
                .user(user)
                .joinState(joinState)
                .build();

        return clubJoinStateRepository.save(clubJoinState);
    }

    @Override
    @Transactional(readOnly = true)
    public ClubJoinState getClubJoinState(long clubJoinStateId) {
        return clubJoinStateRepository.findById(clubJoinStateId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 가입상태입니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public ClubJoinState getClubJoinState(long userId, long clubId) {
        return clubJoinStateRepository.find(userId, clubId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 가입상태입니다."));
    }

    @Override
    @Transactional
    public ClubJoinState update(long userId, long clubId, boolean isUsed) {
        ClubJoinState updatedJoinState = this.getClubJoinState(userId, clubId);

        updatedJoinState.update(isUsed);
        return updatedJoinState;
    }

    @Override
    @Transactional
    public ClubJoinState update(long userId, long clubId, int joinStateCode) {
        ClubJoinState updatedJoinState = this.getClubJoinState(userId, clubId);
        JoinState joinState = JoinState.from(joinStateCode);

        updatedJoinState.update(joinState);
        return updatedJoinState;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existed(long userId, long clubId) {
        return this.clubJoinStateRepository.find(userId, clubId)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    @Transactional
    public void delete(long userId, long clubId) {
        this.update(userId, clubId, false);
    }


    /**
     * Club Region (for Club API)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getMasters(long clubId, Pageable pageable) {
        return clubJoinStateRepository.findAllByClub(clubId, JoinState.MASTER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getManagers(long clubId, Pageable pageable) {
        return clubJoinStateRepository.findAllByClub(clubId, JoinState.MANAGER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getMembers(long clubId, Pageable pageable) {
        return clubJoinStateRepository.findAllByClub(clubId, JoinState.MEMBER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getAllMembers(long clubId, Pageable pageable) {
        return clubJoinStateRepository.findAllByClub(clubId, pageable)
                .stream()
                .filter(state -> !state.getJoinState().equals(JoinState.NOT_JOINED) && state.isUsed())
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getAppliedMembers(long clubId, Pageable pageable) {
        return clubJoinStateRepository.findAllByClub(clubId, JoinState.NOT_JOINED, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getManagerRoleMembers(long clubId, Pageable pageable) {
        return clubJoinStateRepository.findAllByClubContainingJoinState(clubId, JoinState.MANAGER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getMemberRoleMembers(long clubId, Pageable pageable) {
        return clubJoinStateRepository.findAllByClubContainingJoinState(clubId, JoinState.MEMBER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }


    /**
     * User Region (for User API)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getClubJoinStatesByUser(long userId, Pageable pageable) {
        return clubJoinStateRepository.findAllByUser(userId, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getClubJoinStatesByUser(long userId, int joinStateCode, Pageable pageable) {
        JoinState joinState = JoinState.from(joinStateCode);

        return clubJoinStateRepository.findAllByUser(userId, joinState, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getClubJoinStatesByUserHasRole(long userId, int joinStateCode, Pageable pageable) {
        JoinState joinState = JoinState.from(joinStateCode);

        return clubJoinStateRepository.findAllByUserContainingJoinState(userId, joinState, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isClubMaster(long userId, long clubId) {
        return this.clubJoinStateRepository.find(userId, clubId, JoinState.MASTER)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isClubManager(long userId, long clubId) {
        return this.clubJoinStateRepository.find(userId, clubId, JoinState.MANAGER)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isClubMember(long userId, long clubId) {
        return this.clubJoinStateRepository.find(userId, clubId, JoinState.MEMBER)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isJoined(long userId, long clubId) {
        return clubJoinStateRepository.findExceptJoinState(userId, clubId, JoinState.NOT_JOINED)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasMasterRole(long userId, long clubId) {
        return this.hasRole(userId, clubId, JoinState.MASTER);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasManagerRole(long userId, long clubId) {
        return this.hasRole(userId, clubId, JoinState.MANAGER);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasMemberRole(long userId, long clubId) {
        return this.hasRole(userId, clubId, JoinState.MEMBER);
    }

    private boolean hasRole(long userId, long clubId, JoinState joinState) {
        return clubJoinStateRepository.findContainingJoinState(userId, clubId, joinState).map(ClubJoinState::isUsed).orElse(false);
    }
}
