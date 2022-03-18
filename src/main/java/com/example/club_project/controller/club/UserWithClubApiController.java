package com.example.club_project.controller.club;

import com.example.club_project.security.dto.AuthUserDTO;
import com.example.club_project.service.clubjoinstate.ClubJoinStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User가 조회하는 클럽 관련 정보를 담당하는 API Controller
 */
@RequestMapping("api/users")
@RestController
@RequiredArgsConstructor
public class UserWithClubApiController {

    private final ClubJoinStateService clubJoinStateService;

    /**
     * 사용자가 가입한 동아리 목록을 반환한다.
     *
     * GET /api/users/joined-club
     */
    @GetMapping("joined-club")
    public List<ClubDTO.Response> searchJoinedClubs(@AuthenticationPrincipal AuthUserDTO authUser, Pageable pageable) {
        return clubJoinStateService.getJoinedClubs(authUser.getId(), pageable);
    }

    /**
     * 가입신청을 했지만 가입이 완료되지 않은 동아리 목록을 반환한다.
     *
     * GET /api/users/not-joined-club
     */
    @GetMapping("not-joined-club")
    public List<ClubDTO.Response> searchWaitingApprovalClubs(@AuthenticationPrincipal AuthUserDTO authUser,
                                                             Pageable pageable) {

        return clubJoinStateService.getWaitingApprovalClubs(authUser.getId(), pageable);
    }
}
