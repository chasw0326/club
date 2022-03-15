package com.example.club_project.controller.clubjoinstate;

import com.example.club_project.domain.JoinState;
import com.example.club_project.service.clubjoinstate.ClubJoinStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("api/clubs")
@RestController
@RequiredArgsConstructor
public class ClubJoinStateApiController {

    private static Long mockUserId = 1L;
    private final ClubJoinStateService clubJoinStateService;

    /**
     * 동아리 상세 정보를 반환한다.
     *
     * GET /api/clubs/:club-id
     */
    @GetMapping("{id}")
    public void searchClubDetails(@PathVariable("id") Long clubId, Pageable pageable) {
        clubJoinStateService.getAllMembers(clubId, pageable);
    }

    /**
     * 사용자가 가입한 동아리 목록을 반환한다.
     *
     * GET /api/clubs/member
     */
    @GetMapping("member")
    public void searchMemberClub(Long userId, Pageable pageable) {
        clubJoinStateService.getClubJoinStatesByUserHasRole(mockUserId, JoinState.MEMBER.getCode(), pageable);
    }

    /**
     * 가입신청을 했지만 가입이 완료되지 않은 동아리 목록을 반환한다.
     *
     * GET /api/clubs/non-member
     */
    @GetMapping("non-member")
    public void searchNonMemberClub(Long userId, Pageable pageable) {
        clubJoinStateService.getClubJoinStatesByUser(mockUserId, JoinState.NOT_JOINED.getCode(), pageable);
    }

    /**
     * 동아리 가입신청을 할 수 있다.
     *
     * POST api/clubs/member
     */
    @PostMapping("member")
    public void registerClub() {
//        clubJoinStateService.register();
    }

    /**
     * 동아리를 탈퇴할 수 있다.
     *
     * DELETE api/clubs/member
     */
    @DeleteMapping("member")
    public void leaveClub(Long clubId) {
        clubJoinStateService.delete(mockUserId, clubId);
    }
}
