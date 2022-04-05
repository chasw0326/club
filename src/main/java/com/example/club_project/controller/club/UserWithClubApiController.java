package com.example.club_project.controller.club;

import com.example.club_project.security.dto.AuthUserDTO;
import com.example.club_project.service.clubjoinstate.ClubJoinStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * User가 조회하는 클럽 관련 정보를 담당하는 API Controller
 */
@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserWithClubApiController {

    private final ClubJoinStateService clubJoinStateService;

    /**
     * 사용자가 가입한 동아리 목록을 반환한다.
     *
     * GET /api/users/joined-club
     */
    @Async
    @GetMapping("/joined-club")
    public CompletableFuture<List<ClubDTO.Response>> searchJoinedClubs(@AuthenticationPrincipal AuthUserDTO authUser,
                                                                       Pageable pageable) {

        return completedFuture(clubJoinStateService.getJoinedClubs(authUser.getId(), pageable));
    }

    /**
     * 가입신청을 했지만 가입이 완료되지 않은 동아리 목록을 반환한다.
     *
     * GET /api/users/not-joined-club
     */
    @Async
    @GetMapping("/not-joined-club")
    public CompletableFuture<List<ClubDTO.Response>> searchWaitingApprovalClubs(@AuthenticationPrincipal AuthUserDTO authUser,
                                                                                Pageable pageable) {

        return completedFuture(clubJoinStateService.getWaitingApprovalClubs(authUser.getId(), pageable));
    }
}
