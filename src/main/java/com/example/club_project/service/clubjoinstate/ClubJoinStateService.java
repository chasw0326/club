package com.example.club_project.service.clubjoinstate;

import com.example.club_project.domain.ClubJoinState;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubJoinStateService {

    /**
     * Common Region
     */
    ClubJoinState register(long userId, long clubId, int joinStateCode);

    ClubJoinState getClubJoinState(long clubJoinStateId);

    ClubJoinState getClubJoinState(long userId, long clubId);

    ClubJoinState update(long userId, long clubId, boolean isUsed);

    ClubJoinState update(long userId, long clubId, int joinStateCode);

    void delete(long userId, long clubId);

    boolean existed(long userId, long clubId);


    /**
     * Club Region (for Club API)
     */
    /* 가입상태가 Master인 유저 목록을 반환한다. */
    List<ClubJoinState> getMasters(long clubId, Pageable pageable);

    /* 가입상태가 Manager인 유저 목록을 반환한다. */
    List<ClubJoinState> getManagers(long clubId, Pageable pageable);

    /* 가입상태가 Member인 유저 목록을 반환한다. */
    List<ClubJoinState> getMembers(long clubId, Pageable pageable);

    /* 가입상태가 Master, Manager, Member인 유저 목록을 반환한다. */
    List<ClubJoinState> getAllMembers(long clubId, Pageable pageable);

    /* 가입상태가 NOT_JOINED인 유저 목록을 반환한다. */
    List<ClubJoinState> getAppliedMembers(long clubId, Pageable pageable);

    /* 가입상태가 Master인, Manager인 유저 목록을 반환한다. */
    List<ClubJoinState> getManagerRoleMembers(long clubId, Pageable pageable);

    /* 가입상태가 Master, Manager, Member인 유저 목록을 반환한다. */
    List<ClubJoinState> getMemberRoleMembers(long clubId, Pageable pageable);


    /**
     * User Region (for User API)
     */
    /* 사용자의 모든 동아리 가입 상태를 반환한다. */
    List<ClubJoinState> getClubJoinStatesByUser(long userId, Pageable pageable);

    /* 사용자의 가입 상태가 joinState인 모든 동아리 가입 상태를 반환한다. */
    List<ClubJoinState> getClubJoinStatesByUser(long userId, int joinStateCode, Pageable pageable);

    /* 사용자가 특정 권한 이상을 가진 모든 동아리 가입 상태를 반환한다. */
    List<ClubJoinState> getClubJoinStatesByUserHasRole(long userId, int joinStateCode, Pageable pageable);

    /**
     * if joinState MASTER => true
     * if joinState MANAGER => false
     * if joinState MEMBER => false
     * if joinState NOT_JOINED => false
     */
    boolean isClubMaster(long userId, long clubId);

    /**
     * if joinState MASTER => false
     * if joinState MANAGER => true
     * if joinState MEMBER => false
     * if joinState NOT_JOINED => false
     */
    boolean isClubManager(long userId, long clubId);

    /**
     * if joinState MASTER => false
     * if joinState MANAGER => false
     * if joinState MEMBER => true
     * if joinState NOT_JOINED => false
     */
    boolean isClubMember(long userId, long clubId);

    /**
     * if joinState MASTER => true
     * if joinState MANAGER => true
     * if joinState MEMBER => true
     * if joinState NOT_JOINED => false
     */
    boolean isJoined(long userId, long clubId);


    /**
     * 권한 판단(hasRole) 메서드 Region
     * 권한 판단 메서드에서 joinState는 포함관계를 가진다.
     * 포함관계란,하위 joinState 결과가 true이면, 상위 joinState 결과도 true이다.
     * (최상위 joinState == MASTER, 최하위 joinState == MEMBER)
     */

    /**
     * if joinState MASTER => true
     * if joinState MANAGER => false
     * if joinState MEMBER => false
     * if joinState NOT_JOINED => false
     */
    boolean hasMasterRole(long userId, long clubId);

    /**
     * if joinState MASTER => true
     * if joinState MANAGER => true
     * if joinState MEMBER => false
     * if joinState NOT_JOINED => false
     */
    boolean hasManagerRole(long userId, long clubId);

    /**
     * if joinState MASTER => true
     * if joinState MANAGER => true
     * if joinState MEMBER => true
     * if joinState NOT_JOINED => false
     */
    boolean hasMemberRole(long userId, long clubId);
}
