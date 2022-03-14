package com.example.club_project.service.clubjoinstate;

import com.example.club_project.domain.Club;
import com.example.club_project.domain.ClubJoinState;
import com.example.club_project.domain.JoinState;
import com.example.club_project.domain.User;
import com.example.club_project.repository.ClubJoinStateRepository;
import com.example.club_project.service.club.ClubService;
import com.example.club_project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

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
    public ClubJoinState register(Long userId, Long clubId, int joinStateCode) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        ClubJoinState findJoinState = clubJoinStateRepository.find(userId, clubId)
                .orElse(createClubJoinState(userId, clubId, joinStateCode));

        if (ObjectUtils.isEmpty(findJoinState.getId())) {
            return clubJoinStateRepository.save(findJoinState);
        }

        if (findJoinState.isNotUsed()) {
            findJoinState.update(JoinState.from(joinStateCode), true);
            return findJoinState;
        }

        throw new RuntimeException("이미 존재하는 ClubJoinState 정보입니다.");
    }

    private ClubJoinState createClubJoinState(Long userId, Long clubId, int joinStateCode) {
        User user = userService.getUser(userId);
        Club club = clubService.getClub(clubId);
        JoinState joinState = JoinState.from(joinStateCode);

        return ClubJoinState.builder()
                .club(club)
                .user(user)
                .joinState(joinState)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ClubJoinState getClubJoinState(Long clubJoinStateId) {
        Objects.requireNonNull(clubJoinStateId, "clubJoinStateId 입력값은 필수입니다.");

        return clubJoinStateRepository.findById(clubJoinStateId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 가입상태입니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public ClubJoinState getClubJoinState(Long userId, Long clubId) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.find(userId, clubId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 가입상태입니다."));
    }

    @Override
    @Transactional
    public ClubJoinState update(Long userId, Long clubId, int joinStateCode) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        ClubJoinState updatedJoinState = this.getClubJoinState(userId, clubId);
        JoinState joinState = JoinState.from(joinStateCode);

        updatedJoinState.update(joinState);
        return updatedJoinState;
    }

    @Override
    @Transactional
    public void delete(Long userId, Long clubId) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        ClubJoinState updatedJoinState = this.getClubJoinState(userId, clubId);
        updatedJoinState.update(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isClubMaster(Long userId, Long clubId) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return this.clubJoinStateRepository.find(userId, clubId, JoinState.MASTER)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isClubManager(Long userId, Long clubId) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return this.clubJoinStateRepository.find(userId, clubId, JoinState.MANAGER)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isClubMember(Long userId, Long clubId) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return this.clubJoinStateRepository.find(userId, clubId, JoinState.MEMBER)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isJoined(Long userId, Long clubId) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findExceptJoinState(userId, clubId, JoinState.NOT_JOINED)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existed(Long userId, Long clubId) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return this.clubJoinStateRepository.find(userId, clubId)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLeaveClub(Long userId, Long clubId) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return this.clubJoinStateRepository.find(userId, clubId)
                .map(ClubJoinState::isNotUsed)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isRegistered(Long userId, Long clubId) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return this.clubJoinStateRepository.find(userId, clubId).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasManagerRole(Long userId, Long clubId) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return this.hasRole(userId, clubId, JoinState.MANAGER);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasMemberRole(Long userId, Long clubId) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return this.hasRole(userId, clubId, JoinState.MEMBER);
    }

    private boolean hasRole(Long userId, Long clubId, JoinState joinState) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findContainingJoinState(userId, clubId, joinState)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    /**
     * Club Region (for Club API)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getMasters(Long clubId, Pageable pageable) {
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClub(clubId, JoinState.MASTER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getManagers(Long clubId, Pageable pageable) {
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClub(clubId, JoinState.MANAGER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getMembers(Long clubId, Pageable pageable) {
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClub(clubId, JoinState.MEMBER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getAllMembers(Long clubId, Pageable pageable) {
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClub(clubId, pageable)
                .stream()
                .filter(state -> !state.getJoinState().equals(JoinState.NOT_JOINED) && state.isUsed())
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getAppliedMembers(Long clubId, Pageable pageable) {
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClub(clubId, JoinState.NOT_JOINED, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getManagerRoleMembers(Long clubId, Pageable pageable) {
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClubContainingJoinState(clubId, JoinState.MANAGER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getMemberRoleMembers(Long clubId, Pageable pageable) {
        Objects.requireNonNull(clubId, "clubId 입력값은 필수입니다.");

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
    public List<ClubJoinState> getClubJoinStatesByUser(Long userId, Pageable pageable) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByUser(userId, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getClubJoinStatesByUser(Long userId, int joinStateCode, Pageable pageable) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");

        JoinState joinState = JoinState.from(joinStateCode);

        return clubJoinStateRepository.findAllByUser(userId, joinState, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubJoinState> getClubJoinStatesByUserHasRole(Long userId, int joinStateCode, Pageable pageable) {
        Objects.requireNonNull(userId, "userId 입력값은 필수입니다.");

        JoinState joinState = JoinState.from(joinStateCode);

        return clubJoinStateRepository.findAllByUserContainingJoinState(userId, joinState, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }
}
