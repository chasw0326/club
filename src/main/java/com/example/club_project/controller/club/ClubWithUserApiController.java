package com.example.club_project.controller.club;

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

        throw new RuntimeException("권한이 없습니다.");
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

        throw new RuntimeException("권한이 없습니다.");
    }

    /**
     * 동아리 가입신청을 할 수 있다.
     *
     * POST api/clubs/{clubId}/member
     */
    @PostMapping("/{clubId}/member")
    public void join(@AuthenticationPrincipal AuthUserDTO authUser, @PathVariable("clubId") Long clubId) {
        if (clubJoinStateService.isJoined(authUser.getId(), clubId)) {
            throw new RuntimeException("이미 가입한 동아리입니다.");
        } else if (clubJoinStateService.isWaitingApproval(authUser.getId(), clubId)) {
            throw new RuntimeException("이미 동아리 가입신청을 완료한 상태입니다.");
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
        if (clubJoinStateService.isClubMember(authUser.getId(), clubId)) {
            clubJoinStateService.delete(authUser.getId(), clubId);
            postService.deleteWhenLeaveClub(authUser.getId(), clubId);
            return;
        }

        throw new RuntimeException("가입한 동아리가 아니라서 탈퇴할 수 없습니다.");
    }

    /**
     * 사용자를 탈퇴시킬 수 있다.
     *
     * DELETE api/clubs/{clubId}/member/{userId}
     */
    @DeleteMapping("/{clubId}/member/{userId}")
    public void deleteMember(@AuthenticationPrincipal AuthUserDTO authUser,
                             @PathVariable("clubId") Long clubId,
                             @PathVariable("userId") Long userId) {

        if (clubJoinStateService.isClubMaster(authUser.getId(), clubId)) {
            clubJoinStateService.delete(userId, clubId);
            postService.deleteWhenLeaveClub(userId, clubId);
            return;
        } else if (clubJoinStateService.isClubManager(authUser.getId(), clubId)
                && clubJoinStateService.isClubMember(userId, clubId)) {

            clubJoinStateService.delete(userId, clubId);
            postService.deleteWhenLeaveClub(userId, clubId);
            return;
        }

        throw new RuntimeException("유효하지 않은 요청입니다.");
    }

    /**
     * 동아리 멤버로 변경(ex.가입신청 승인)
     *
     * PUT api/clubs/{clubId}/member/{userId}
     *
     * requestBody userId
     */
    @PutMapping("/{clubId}/member/{userId}")
    public void toMember(@AuthenticationPrincipal AuthUserDTO authUser,
                         @PathVariable("clubId") Long clubId,
                         @PathVariable("userId") Long userId) {

        if (clubJoinStateService.hasManagerRole(authUser.getId(), clubId)) {
            clubJoinStateService.toMember(userId, clubId);
            return;
        }

        throw new RuntimeException("권한이 없습니다.");
    }

    /**
     * 동아리 운영진으로 변경
     *
     * PUT api/clubs/{clubId}/manager/{userId}
     *
     * requestBody userId
     */
    @PutMapping("/{clubId}/manager/{userId}")
    public void toManager(@AuthenticationPrincipal AuthUserDTO authUser,
                          @PathVariable("clubId") Long clubId,
                          @PathVariable("userId") Long userId) {

        if (clubJoinStateService.isClubMaster(authUser.getId(), clubId)) {
            clubJoinStateService.toManager(userId, clubId);
            return;
        }

        throw new RuntimeException("권한이 없습니다.");
    }

    /**
     * 동아리 마스터 변경
     *
     * PUT api/clubs/{clubId}/master/{userId}
     *
     * requestBody userId
     */
    @PutMapping("/{clubId}/master/{userId}")
    public void changeMaster(@AuthenticationPrincipal AuthUserDTO authUser,
                             @PathVariable("clubId") Long clubId,
                             @PathVariable("userId") Long userId) {

        if (clubJoinStateService.isClubMaster(authUser.getId(), clubId)) {
            clubJoinStateService.changeMaster(authUser.getId(), userId, clubId);
            return;
        }

        throw new RuntimeException("권한이 없습니다.");
    }
}