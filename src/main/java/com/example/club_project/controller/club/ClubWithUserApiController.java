package com.example.club_project.controller.club;

import com.example.club_project.exception.custom.ForbiddenException;
import com.example.club_project.exception.custom.InvalidArgsException;
import com.example.club_project.security.dto.AuthUserDTO;
import com.example.club_project.service.clubjoinstate.ClubJoinStateService;
import com.example.club_project.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Club에서 관리하는 사용자 정보 처리를 담당하는 API Controller
 */
@RequestMapping("/api/clubs")
@RestController
@RequiredArgsConstructor
public class ClubWithUserApiController {

    private final ClubJoinStateService clubJoinStateService;
    private final PostService postService;

    /**
     * 클럽에 가입한 사용자 목록을 반환한다.
     *
     * GET /api/clubs/:club-id/member
     */
    @GetMapping("/{clubId}/member")
    public List<ClubJoinStateDTO.Response> searchMember(@AuthenticationPrincipal AuthUserDTO authUser,
                                                        @PathVariable("clubId") Long clubId,
                                                        Pageable pageable) {

        if (clubJoinStateService.hasMemberRole(authUser.getId(), clubId)) {
            return clubJoinStateService.getAllMemberDtos(clubId, pageable);
        }

        throw new ForbiddenException("권한이 없습니다.");
    }

    /**
     * 클럽에 가입신청을 한 사용자 목록을 반환한다.
     *
     * GET /api/clubs/:club-id/non-member
     */
    @GetMapping("/{clubId}/non-member")
    public List<ClubJoinStateDTO.Response> searchNonMember(@AuthenticationPrincipal AuthUserDTO authUser,
                                                              @PathVariable("clubId") Long clubId,
                                                              Pageable pageable) {

        if (clubJoinStateService.hasManagerRole(authUser.getId(), clubId)) {
            return clubJoinStateService.getAppliedMemberDtos(clubId, pageable);
        }

        throw new ForbiddenException("권한이 없습니다.");
    }

    /**
     * 동아리 가입신청을 할 수 있다.
     *
     * POST api/clubs/{clubId}/member
     */
    @PostMapping("/{clubId}/member")
    public void join(@AuthenticationPrincipal AuthUserDTO authUser, @PathVariable("clubId") Long clubId) {
        if (clubJoinStateService.isJoined(authUser.getId(), clubId)) {
            throw new ForbiddenException("이미 가입한 동아리입니다.");
        } else if (clubJoinStateService.isWaitingApproval(authUser.getId(), clubId)) {
            throw new ForbiddenException("이미 동아리 가입신청을 완료한 상태입니다.");
        }

        clubJoinStateService.join(authUser.getId(), clubId);
    }

    /**
     * 동아리를 탈퇴할 수 있다.
     *
     * DELETE api/clubs/{clubId}/member
     */
    @DeleteMapping("/{clubId}/member")
    public void leave(@AuthenticationPrincipal AuthUserDTO authUser, @PathVariable("clubId") Long clubId) {
        if (clubJoinStateService.isClubMaster(authUser.getId(), clubId)) {
            throw new ForbiddenException("마스터는 탈퇴할 수 없습니다. 동아리 마스터를 변경하거나 동아리를 삭제하세요.");
        } else if (clubJoinStateService.isJoined(authUser.getId(), clubId)) {
            clubJoinStateService.delete(authUser.getId(), clubId);
            postService.deleteWhenLeaveClub(authUser.getId(), clubId);
            return;
        }

        throw new ForbiddenException("가입한 동아리가 아니라서 탈퇴할 수 없습니다.");
    }

    /**
     * 사용자를 강퇴시킬 수 있다.
     *
     * DELETE api/clubs/{clubId}/member/{userId}
     */
    @DeleteMapping("/{clubId}/member/{userId}")
    public void deleteMember(@AuthenticationPrincipal AuthUserDTO authUser,
                             @PathVariable("clubId") Long clubId,
                             @PathVariable("userId") Long userId) {

        if (authUser.getId().equals(userId)) {
            throw new ForbiddenException("자기 자신은 강퇴시킬 수 없습니다.");
        } else if (clubJoinStateService.isClubMaster(authUser.getId(), clubId)) {
            clubJoinStateService.delete(userId, clubId);
            postService.deleteWhenLeaveClub(userId, clubId);
            return;
        } else if (clubJoinStateService.isClubManager(authUser.getId(), clubId)
                && clubJoinStateService.isClubMember(userId, clubId)) {

            clubJoinStateService.delete(userId, clubId);
            postService.deleteWhenLeaveClub(userId, clubId);
            return;
        }

        throw new ForbiddenException("권한이 없습니다.");
    }

    /**
     * 가입신청 승인
     *
     * PUT api/clubs/{clubId}/join-approval/{userId}
     */
    @PutMapping("/{clubId}/join-approval/{userId}")
    public void joinApproval(@AuthenticationPrincipal AuthUserDTO authUser,
                         @PathVariable("clubId") Long clubId,
                         @PathVariable("userId") Long userId) {

        if (clubJoinStateService.hasManagerRole(authUser.getId(), clubId)) {

            if (clubJoinStateService.isWaitingApproval(userId, clubId)) {
                clubJoinStateService.toMember(userId, clubId);
                return;
            }

            throw new InvalidArgsException("가입 신청 대기중인 회원만 가입신청 승인을 할 수 있습니다.");
        }

        throw new ForbiddenException("권한이 없습니다.");
    }

    /**
     * 동아리 멤버로 변경
     *
     * PUT api/clubs/{clubId}/member/{userId}
     */
    @PutMapping("/{clubId}/member/{userId}")
    public void toMember(@AuthenticationPrincipal AuthUserDTO authUser,
                         @PathVariable("clubId") Long clubId,
                         @PathVariable("userId") Long userId) {

        if (clubJoinStateService.isClubMaster(authUser.getId(), clubId)) {

            if (authUser.getId().equals(userId)) {
                throw new InvalidArgsException("마스터는 동아리 멤버로 변경할 수 없습니다.");
            }

            if (!clubJoinStateService.isJoined(userId, clubId)) {
                throw new InvalidArgsException("동아리 회원이 아닌 사용자는 동아리 멤버로 변경할 수 없습니다.");
            }

            clubJoinStateService.toMember(userId, clubId);
            return;
        }

        throw new ForbiddenException("권한이 없습니다.");
    }

    /**
     * 동아리 운영진으로 변경
     *
     * PUT api/clubs/{clubId}/manager/{userId}
     */
    @PutMapping("/{clubId}/manager/{userId}")
    public void toManager(@AuthenticationPrincipal AuthUserDTO authUser,
                          @PathVariable("clubId") Long clubId,
                          @PathVariable("userId") Long userId) {

        if (clubJoinStateService.isClubMaster(authUser.getId(), clubId)) {

            if (authUser.getId().equals(userId)) {
                throw new InvalidArgsException("마스터는 운영진으로 변경할 수 없습니다.");
            }

            if (!clubJoinStateService.isJoined(userId, clubId)) {
                throw new InvalidArgsException("동아리 회원이 아닌 사용자는 운영진으로 변경할 수 없습니다.");
            }

            clubJoinStateService.toManager(userId, clubId);
            return;
        }

        throw new ForbiddenException("권한이 없습니다.");
    }

    /**
     * 동아리 마스터 변경
     *
     * PUT api/clubs/{clubId}/master/{userId}
     */
    @PutMapping("/{clubId}/master/{userId}")
    public void changeMaster(@AuthenticationPrincipal AuthUserDTO authUser,
                             @PathVariable("clubId") Long clubId,
                             @PathVariable("userId") Long userId) {

        if (clubJoinStateService.isClubMaster(authUser.getId(), clubId)) {

            if (authUser.getId().equals(userId)) {
                throw new InvalidArgsException("자기 자신을 마스터로 변경할 수 없습니다.");
            }

            clubJoinStateService.changeMaster(authUser.getId(), userId, clubId);
            return;
        }

        throw new ForbiddenException("권한이 없습니다.");
    }

    /**
     * 동아리 마스터인지
     *
     * GET api/clubs/{clubId}/is-master
     *
     * master: true
     * manager: false
     * member: false
     */
    @GetMapping("/{clubId}/is-master")
    public ClubJoinStateDTO.JoinStateResponse isClubMaster(@AuthenticationPrincipal AuthUserDTO authUser,
                                                           @PathVariable("clubId") Long clubId) {

        boolean result = clubJoinStateService.isClubMaster(authUser.getId(), clubId);
        return new ClubJoinStateDTO.JoinStateResponse(result);
    }

    /**
     * 동아리 운영진인지
     *
     * GET api/clubs/{clubId}/is-manager
     *
     * master: true
     * manager: true
     * member: false
     */
    @GetMapping("/{clubId}/is-manager")
    public ClubJoinStateDTO.JoinStateResponse isClubManager(@AuthenticationPrincipal AuthUserDTO authUser,
                                                            @PathVariable("clubId") Long clubId) {

        boolean result = clubJoinStateService.hasManagerRole(authUser.getId(), clubId);
        return new ClubJoinStateDTO.JoinStateResponse(result);
    }

    /**
     * 동아리 회원인지
     *
     * GET api/clubs/{clubId}/is-member
     *
     * master: true
     * manager: true
     * member: true
     */
    @GetMapping("/{clubId}/is-member")
    public ClubJoinStateDTO.JoinStateResponse isClubMember(@AuthenticationPrincipal AuthUserDTO authUser,
                                                           @PathVariable("clubId") Long clubId) {

        boolean result = clubJoinStateService.hasMemberRole(authUser.getId(), clubId);
        return new ClubJoinStateDTO.JoinStateResponse(result);
    }
}
