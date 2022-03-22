package com.example.club_project.controller.club;

import com.example.club_project.security.dto.AuthUserDTO;
import com.example.club_project.service.club.ClubService;
import com.example.club_project.service.clubjoinstate.ClubJoinStateService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Club 자체에 대한 CRUD만 담당하는 API Controller
 */
@RequestMapping("api/clubs")
@RestController
@RequiredArgsConstructor
public class ClubApiController {

    private final ClubService clubService;
    private final ClubJoinStateService clubJoinStateService;

    /**
     * 사용자가 등록한 대학교와 같은 대학교에 있는 동아리를 반환한다.
     *
     * GET /api/clubs
     *
     * 검색조건1: None
     * 검색조건2: 카테고리
     * 검색조건3: 동아리 이름
     * 검색조건4: 카테고리 + 동아리 이름
     */
    @GetMapping
    public List<ClubDTO> searchClubs(@AuthenticationPrincipal AuthUserDTO authUser,
                                              ClubDTO.SearchOption searchOption,
                                              Pageable pageable) {

        if (ObjectUtils.isEmpty(searchOption.getName()) && ObjectUtils.isEmpty(searchOption.getCategories())) {
            return clubJoinStateService.getClubDtos(authUser.getUniversity(), pageable);
        } else if (ObjectUtils.isEmpty(searchOption.getCategories())) {
            return clubJoinStateService.getClubDtos(searchOption.getName(), authUser.getUniversity(), pageable);
        } else if (ObjectUtils.isEmpty(searchOption.getName())) {
            return clubJoinStateService.getClubDtos(searchOption.getCategories(), authUser.getUniversity(), pageable);
        } else {
            return clubJoinStateService.getClubDtos(searchOption.getCategories(), authUser.getUniversity(), searchOption.getName(), pageable);
        }
    }

    //TODO
    /**
     * 동아리 상세 정보를 반환한다.
     *
     * GET /api/clubs/:club-id
     */
    @GetMapping("{clubId}")
    public ClubDTO.DetailResponse searchClubDetails(@PathVariable("clubId") Long clubId) {
        return clubJoinStateService.getClubDetailDto(clubId);
    }

    /**
     * 동아리를 생성할 수 있다.
     *
     * POST /api/clubs
     */
    @PostMapping
    public ClubDTO.Response registerClub(@AuthenticationPrincipal AuthUserDTO authUser,
                                         @Valid @RequestBody ClubDTO.RegisterRequest req) {

        ClubDTO.Response registerClub = clubService.registerClub(
                req.getName(),
                req.getAddress(),
                authUser.getUniversity(),
                req.getDescription(),
                req.getCategory(),
                req.getImageUrl()
        );

        clubJoinStateService.joinAsMaster(authUser.getId(), registerClub.getId());

        return registerClub;
    }

    /**
     * 동아리 정보를 수정할 수 있다.
     *
     * PUT /api/clubs/:club-id
     *
     * TODO: 동아리 '대학교명'은 별도의 필드로 두지 않고 update시 사용자의 대학교명을 그대로 따라가게 하는 건 어떤지 (관리포인트 줄이기)
     */
    @PutMapping("{clubId}")
    public ClubDTO.Response updateClub(@AuthenticationPrincipal AuthUserDTO authUser,
                                       @PathVariable("clubId") Long clubId,
                                       @RequestBody ClubDTO.UpdateRequest req) {

        if (clubJoinStateService.isClubMaster(authUser.getId(), clubId)) {
            return clubService.updateClub(
                    clubId,
                    req.getName(),
                    req.getAddress(),
                    authUser.getUniversity(),
                    req.getDescription(),
                    req.getCategory(),
                    req.getImageUrl()
            );
        }

        throw new RuntimeException("동아리 수정 권한이 없습니다.");
    }

    /**
     * 동아리를 삭제할 수 있다.
     *
     * DELETE /api/clubs/:club-id
     */
    @DeleteMapping("{clubId}")
    public void deleteClub(@AuthenticationPrincipal AuthUserDTO authUser,
                           @PathVariable("clubId") Long clubId) {

        if (clubJoinStateService.isClubMaster(authUser.getId(), clubId)) {
            clubService.delete(clubId);
            return;
        }

        throw new RuntimeException("동아리 삭제 권한이 없습니다.");
    }
}
