package com.example.club_project.service.clubjoinstate;

import com.example.club_project.controller.club.ClubDTO;
import com.example.club_project.controller.club.ClubJoinStateDTO;
import com.example.club_project.domain.Club;
import com.example.club_project.domain.ClubJoinState;
import com.example.club_project.domain.JoinState;
import com.example.club_project.domain.User;
import com.example.club_project.exception.custom.AlreadyExistsException;
import com.example.club_project.exception.custom.NotFoundException;
import com.example.club_project.repository.ClubJoinStateRepository;
import com.example.club_project.repository.query.ClubQueryRepository;
import com.example.club_project.service.club.ClubService;
import com.example.club_project.service.user.UserService;
import com.example.club_project.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Direction.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ClubJoinStateServiceImpl implements ClubJoinStateService {

    private static final int MEMBER_SIZE = 10;

    private final ClubJoinStateRepository clubJoinStateRepository;
    private final ClubQueryRepository clubQueryRepository;
    private final UserService userService;
    private final ClubService clubService;
    private final ValidateUtil validateUtil;

    /**
     * DTO region
     * for Controller
     */
    @Override
    public List<ClubDTO.SimpleResponse> getClubDtos(List<Long> categories, String university, String name, Pageable pageable) {
        return clubQueryRepository.findAll(categories, university, name, pageable);
    }

    @Override
    public ClubDTO.DetailResponse getClubDetailDto(Long clubId) {
        checkArgument(ObjectUtils.isNotEmpty(clubId), "clubId 입력값은 필수입니다.");

        Club club = clubService.getClub(clubId);
        List<ClubJoinState> members =
                getManagerRoleMembers(clubId, PageRequest.of(0, MEMBER_SIZE, Sort.by(ASC, "joinState")))
                                                        .stream()
                                                        .filter(ClubJoinState::isUsed)
                                                        .collect(toList());

        return ClubDTO.DetailResponse.of(club, members);
    }

    @Override
    @Transactional
    public ClubJoinStateDTO.Response join(Long userId, Long clubId) {
        return convertToDTO(register(userId, clubId, JoinState.NOT_JOINED.getCode()));
    }

    @Override
    @Transactional
    public ClubJoinStateDTO.Response joinAsMaster(Long userId, Long clubId) {
        return convertToDTO(register(userId, clubId, JoinState.MASTER.getCode()));
    }

    @Override
    public List<ClubJoinStateDTO.Response> getAllMemberDtos(Long clubId, Pageable pageable) {
        return getAllMembers(clubId, pageable).stream()
                .map(this::convertToDTO)
                .collect(toList());
    }

    @Override
    public List<ClubJoinStateDTO.Response> getAppliedMemberDtos(Long clubId, Pageable pageable) {
        return getAppliedMembers(clubId, pageable).stream()
                .map(this::convertToDTO)
                .collect(toList());
    }

    @Override
    public List<ClubDTO.Response> getJoinedClubs(Long userId, Pageable pageable) {
        return getClubJoinStatesByUser(userId, pageable).stream()
                .filter(state -> !state.getJoinState().equals(JoinState.NOT_JOINED))
                .map(state -> state.getClub())
                .map(clubService::convertToDTO)
                .collect(toList());
    }

    @Override
    public List<ClubDTO.Response> getWaitingApprovalClubs(Long userId, Pageable pageable) {
        return getClubJoinStatesByUser(userId, pageable).stream()
                .filter(state -> state.getJoinState().equals(JoinState.NOT_JOINED))
                .map(state -> state.getClub())
                .map(clubService::convertToDTO)
                .collect(toList());
    }

    @Override
    @Transactional
    public ClubJoinStateDTO.Response toMember(Long userId, Long clubId) {
        return convertToDTO(update(userId, clubId, JoinState.MEMBER.getCode()));
    }

    @Override
    @Transactional
    public ClubJoinStateDTO.Response toManager(Long userId, Long clubId) {
        return convertToDTO(update(userId, clubId, JoinState.MANAGER.getCode()));
    }

    @Override
    @Transactional
    public ClubJoinStateDTO.Response changeMaster(Long fromUserId, Long toUserId, Long clubId) {
        update(fromUserId, clubId, JoinState.MANAGER.getCode());
        ClubJoinState newMaster = update(toUserId, clubId, JoinState.MASTER.getCode());

        return convertToDTO(newMaster);
    }

    @Override
    public ClubJoinStateDTO.Response convertToDTO(ClubJoinState clubJoinState) {
        checkArgument(ObjectUtils.isNotEmpty(clubJoinState), "clubJoinState 입력값은 필수입니다.");

        return ClubJoinStateDTO.Response.builder()
                .userId(clubJoinState.getUser().getId())
                .email(clubJoinState.getUser().getEmail())
                .name(clubJoinState.getUser().getName())
                .nickname(clubJoinState.getUser().getNickname())
                .profileUrl(clubJoinState.getUser().getProfileUrl())
                .clubId(clubJoinState.getClub().getId())
                .joinStateCode(clubJoinState.getJoinState().getCode())
                .build();
    }

    /**
     * Service Region
     * for other Services
     */
    /**
     * Common Region
     */
    @Override
    @Transactional
    public ClubJoinState register(Long userId, Long clubId, int joinStateCode) {
        checkArgumentUserIdAndClubId(userId, clubId);

        ClubJoinState findJoinState = clubJoinStateRepository.find(userId, clubId)
                .orElse(createClubJoinState(userId, clubId, joinStateCode));

        if (ObjectUtils.isEmpty(findJoinState.getId())) {
            validateUtil.validate(findJoinState);
            return clubJoinStateRepository.save(findJoinState);
        }

        if (findJoinState.isNotUsed()) {
            findJoinState.update(JoinState.from(joinStateCode), true);
            validateUtil.validate(findJoinState);
            return findJoinState;
        }

        throw new AlreadyExistsException("이미 존재하는 ClubJoinState 정보입니다.");
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
    public ClubJoinState getClubJoinState(Long clubJoinStateId) {
        checkArgument(ObjectUtils.isNotEmpty(clubJoinStateId), "clubJoinStateId 입력값은 필수입니다.");

        return clubJoinStateRepository.findById(clubJoinStateId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 가입상태입니다."));
    }

    @Override
    public ClubJoinState getClubJoinState(Long userId, Long clubId) {
        checkArgumentUserIdAndClubId(userId, clubId);

        return clubJoinStateRepository.find(userId, clubId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 가입상태입니다."));
    }

    @Override
    @Transactional
    public ClubJoinState update(Long userId, Long clubId, int joinStateCode) {
        checkArgumentUserIdAndClubId(userId, clubId);

        ClubJoinState updatedJoinState = getClubJoinState(userId, clubId);
        JoinState joinState = JoinState.from(joinStateCode);

        updatedJoinState.update(joinState);
        validateUtil.validate(updatedJoinState);
        return updatedJoinState;
    }

    @Override
    @Transactional
    public void delete(Long userId, Long clubId) {
        checkArgumentUserIdAndClubId(userId, clubId);

        ClubJoinState updatedJoinState = getClubJoinState(userId, clubId);
        updatedJoinState.update(false);
    }

    @Override
    public boolean isClubMaster(Long userId, Long clubId) {
        checkArgumentUserIdAndClubId(userId, clubId);

        return clubJoinStateRepository.find(userId, clubId, JoinState.MASTER)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    public boolean isClubManager(Long userId, Long clubId) {
        checkArgumentUserIdAndClubId(userId, clubId);

        return clubJoinStateRepository.find(userId, clubId, JoinState.MANAGER)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    public boolean isClubMember(Long userId, Long clubId) {
        checkArgumentUserIdAndClubId(userId, clubId);

        return clubJoinStateRepository.find(userId, clubId, JoinState.MEMBER)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    public boolean isWaitingApproval(Long userId, Long clubId) {
        checkArgumentUserIdAndClubId(userId, clubId);

        return clubJoinStateRepository.find(userId, clubId, JoinState.NOT_JOINED)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    public boolean isJoined(Long userId, Long clubId) {
        checkArgumentUserIdAndClubId(userId, clubId);

        return clubJoinStateRepository.findExceptJoinState(userId, clubId, JoinState.NOT_JOINED)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    public boolean existed(Long userId, Long clubId) {
        checkArgumentUserIdAndClubId(userId, clubId);

        return clubJoinStateRepository.find(userId, clubId)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    @Override
    public boolean isLeaveClub(Long userId, Long clubId) {
        checkArgumentUserIdAndClubId(userId, clubId);

        return clubJoinStateRepository.find(userId, clubId)
                .map(ClubJoinState::isNotUsed)
                .orElse(false);
    }

    @Override
    public boolean isRegistered(Long userId, Long clubId) {
        checkArgumentUserIdAndClubId(userId, clubId);

        return clubJoinStateRepository.find(userId, clubId).isPresent();
    }

    @Override
    public boolean hasManagerRole(Long userId, Long clubId) {
        checkArgumentUserIdAndClubId(userId, clubId);

        return hasRole(userId, clubId, JoinState.MANAGER);
    }

    @Override
    public boolean hasMemberRole(Long userId, Long clubId) {
        checkArgumentUserIdAndClubId(userId, clubId);

        return hasRole(userId, clubId, JoinState.MEMBER);
    }

    private boolean hasRole(Long userId, Long clubId, JoinState joinState) {
        checkArgumentUserIdAndClubId(userId, clubId);

        return clubJoinStateRepository.findContainingJoinState(userId, clubId, joinState)
                .map(ClubJoinState::isUsed)
                .orElse(false);
    }

    private void checkArgumentUserIdAndClubId(Long userId, Long clubId) {
        checkArgument(ObjectUtils.isNotEmpty(userId), "userId 입력값은 필수입니다.");
        checkArgument(ObjectUtils.isNotEmpty(clubId), "clubId 입력값은 필수입니다.");
    }

    /**
     * Club Region (for Club API)
     */
    @Override
    public ClubJoinState getMaster(Long clubId, Pageable pageable) {
        checkArgument(ObjectUtils.isNotEmpty(clubId), "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClub(clubId, JoinState.MASTER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList())
                .get(0);
    }

    @Override
    public List<ClubJoinState> getManagers(Long clubId, Pageable pageable) {
        checkArgument(ObjectUtils.isNotEmpty(clubId), "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClub(clubId, JoinState.MANAGER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    public List<ClubJoinState> getMembers(Long clubId, Pageable pageable) {
        checkArgument(ObjectUtils.isNotEmpty(clubId), "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClub(clubId, JoinState.MEMBER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    public List<ClubJoinState> getAllMembers(Long clubId, Pageable pageable) {
        checkArgument(ObjectUtils.isNotEmpty(clubId), "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClub(clubId, pageable)
                .stream()
                .filter(state -> !state.getJoinState().equals(JoinState.NOT_JOINED) && state.isUsed())
                .collect(toList());
    }

    @Override
    public List<ClubJoinState> getAppliedMembers(Long clubId, Pageable pageable) {
        checkArgument(ObjectUtils.isNotEmpty(clubId), "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClub(clubId, JoinState.NOT_JOINED, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    public List<ClubJoinState> getManagerRoleMembers(Long clubId, Pageable pageable) {
        checkArgument(ObjectUtils.isNotEmpty(clubId), "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClubContainingJoinState(clubId, JoinState.MANAGER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    public List<ClubJoinState> getMemberRoleMembers(Long clubId, Pageable pageable) {
        checkArgument(ObjectUtils.isNotEmpty(clubId), "clubId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByClubContainingJoinState(clubId, JoinState.MEMBER, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }


    /**
     * User Region (for User API)
     */
    @Override
    public List<ClubJoinState> getClubJoinStatesByUser(Long userId, Pageable pageable) {
        checkArgument(ObjectUtils.isNotEmpty(userId), "userId 입력값은 필수입니다.");

        return clubJoinStateRepository.findAllByUser(userId, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    public List<ClubJoinState> getClubJoinStatesByUser(Long userId, int joinStateCode, Pageable pageable) {
        checkArgument(ObjectUtils.isNotEmpty(userId), "userId 입력값은 필수입니다.");

        JoinState joinState = JoinState.from(joinStateCode);

        return clubJoinStateRepository.findAllByUser(userId, joinState, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }

    @Override
    public List<ClubJoinState> getClubJoinStatesByUserHasRole(Long userId, int joinStateCode, Pageable pageable) {
        checkArgument(ObjectUtils.isNotEmpty(userId), "userId 입력값은 필수입니다.");

        JoinState joinState = JoinState.from(joinStateCode);

        return clubJoinStateRepository.findAllByUserContainingJoinState(userId, joinState, pageable)
                .stream()
                .filter(ClubJoinState::isUsed)
                .collect(toList());
    }
}
