package com.example.club_project.service.clubjoinstate;

import com.example.club_project.domain.ClubJoinState;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubJoinStateService {
    /**
     * Common Region
     */
    ClubJoinState register(Long userId, Long clubId, int joinStateCode);

    ClubJoinState getClubJoinState(Long clubJoinStateId);

    ClubJoinState getClubJoinState(Long userId, Long clubId);

    ClubJoinState update(Long userId, Long clubId, int joinStateCode);

    void delete(Long userId, Long clubId);

    /**
     * if joinState MASTER => true
     * if joinState MANAGER => false
     * if joinState MEMBER => false
     * if joinState NOT_JOINED => false
     */
    boolean isClubMaster(Long userId, Long clubId);

    /**
     * if joinState MASTER => false
     * if joinState MANAGER => true
     * if joinState MEMBER => false
     * if joinState NOT_JOINED => false
     */
    boolean isClubManager(Long userId, Long clubId);

    /**
     * if joinState MASTER => false
     * if joinState MANAGER => false
     * if joinState MEMBER => true
     * if joinState NOT_JOINED => false
     */
    boolean isClubMember(Long userId, Long clubId);

    /**
     * if joinState MASTER => true
     * if joinState MANAGER => true
     * if joinState MEMBER => true
     * if joinState NOT_JOINED => false
     */
    /* 사용자의 동아리 가입 여부를 판단한다. existed(Long userId, Long clubId) == true && joinState is not NOT_JOINED */
    boolean isJoined(Long userId, Long clubId);

    /* 사용자의 동아리 가입정보 DB row의 유효성(사용 여부)을 판단한다. isRegistered(Long userId, Long clubId) == true && isUsed == true */
    boolean existed(Long userId, Long clubId);

    /* 사용자의 동아리 탈퇴 여부를 판단한다. isRegistered(Long userId, Long clubId) == true && isUsed == false */
    boolean isLeaveClub(Long userId, Long clubId);

    /* 사용자의 동아리 등록 이력 여부를 판단한다. userId, clubId로 ClubJoinState 엔티티를 생성한 적이 있다면 == true */
    boolean isRegistered(Long userId, Long clubId);

    /**
     * 권한 판단(hasRole) 메서드 Region
     * 권한 판단 메서드에서 joinState는 포함관계를 가진다.
     * 포함관계란,하위 joinState 결과가 true이면, 상위 joinState 결과도 true이다.
     * (joinState 순서: MEMBER(최하위), MANAGER, MASTER(최상위))
     */

    /**
     * if joinState MASTER => true
     * if joinState MANAGER => true
     * if joinState MEMBER => false
     * if joinState NOT_JOINED => false
     */
    boolean hasManagerRole(Long userId, Long clubId);

    /**
     * if joinState MASTER => true
     * if joinState MANAGER => true
     * if joinState MEMBER => true
     * if joinState NOT_JOINED => false
     */
    boolean hasMemberRole(Long userId, Long clubId);

    /**
     * Club Region (for Club API)
     */
    /* 가입상태가 Master인 유저 목록을 반환한다. */
    List<ClubJoinState> getMasters(Long clubId, Pageable pageable);

    /* 가입상태가 Manager인 유저 목록을 반환한다. */
    List<ClubJoinState> getManagers(Long clubId, Pageable pageable);

    /* 가입상태가 Member인 유저 목록을 반환한다. */
    List<ClubJoinState> getMembers(Long clubId, Pageable pageable);

    /* 가입상태가 Master, Manager, Member인 유저 목록을 반환한다. */
    List<ClubJoinState> getAllMembers(Long clubId, Pageable pageable);

    /* 가입상태가 NOT_JOINED인 유저 목록을 반환한다. */
    List<ClubJoinState> getAppliedMembers(Long clubId, Pageable pageable);

    /* 가입상태가 Master인, Manager인 유저 목록을 반환한다. */
    List<ClubJoinState> getManagerRoleMembers(Long clubId, Pageable pageable);

    /* 가입상태가 Master, Manager, Member인 유저 목록을 반환한다. */
    List<ClubJoinState> getMemberRoleMembers(Long clubId, Pageable pageable);


    /**
     * User Region (for User API)
     */
    /* 사용자의 모든 동아리 가입 상태를 반환한다. */
    List<ClubJoinState> getClubJoinStatesByUser(Long userId, Pageable pageable);

    /* 사용자의 가입 상태가 joinState인 모든 동아리 가입 상태를 반환한다. */
    List<ClubJoinState> getClubJoinStatesByUser(Long userId, int joinStateCode, Pageable pageable);

    /* 사용자가 특정 권한 이상을 가진 모든 동아리 가입 상태를 반환한다. */
    List<ClubJoinState> getClubJoinStatesByUserHasRole(Long userId, int joinStateCode, Pageable pageable);
}
