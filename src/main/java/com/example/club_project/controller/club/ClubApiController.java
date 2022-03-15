package com.example.club_project.controller.club;

import com.example.club_project.service.club.ClubService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/clubs")
@RestController
@RequiredArgsConstructor
public class ClubApiController {

    private final ClubService clubService;

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
    public List<ClubDTO.Response> searchClubs(ClubDTO.SearchOption searchOption, Pageable pageable) {

        String mockUniversity = "서울사이버대학교";

        if (ObjectUtils.isEmpty(searchOption.getName()) && ObjectUtils.isEmpty(searchOption.getCategories())) {
            return clubService.getClubDtos(mockUniversity, pageable);
        } else if (ObjectUtils.isEmpty(searchOption.getCategories())) {
            return clubService.getClubDtos(searchOption.getName(), mockUniversity, pageable);
        } else if (ObjectUtils.isEmpty(searchOption.getName())) {
            return clubService.getClubDtos(searchOption.getCategories(), mockUniversity, pageable);
        } else {
            return clubService.getClubDtos(searchOption.getCategories(), mockUniversity, searchOption.getName(), pageable);
        }
    }

    /**
     * 동아리를 생성할 수 있다.
     *
     * POST /api/clubs
     */
    @PostMapping
    public ClubDTO.Response registerClub(@RequestBody ClubDTO.RegisterRequest req) {
        return clubService.registerClub(
                req.getName(),
                req.getAddress(),
                req.getUniversity(),
                req.getDescription(),
                req.getCategory(),
                req.getImageUrl()
        );
    }

    /**
     * 동아리 정보를 수정할 수 있다.
     *
     * PUT /api/clubs/:club-id
     */
    @PutMapping("{id}")
    public ClubDTO.Response updateClub(@PathVariable("id") Long id, @Valid @RequestBody ClubDTO.UpdateRequest req) {
        return clubService.updateClub(
                id,
                req.getName(),
                req.getAddress(),
                req.getUniversity(),
                req.getDescription(),
                req.getCategory(),
                req.getImageUrl()
        );
    }

    /**
     * 동아리를 삭제할 수 있다.
     *
     * DELETE /api/clubs/:club-id
     */
    @DeleteMapping("{id}")
    public void deleteClub(@PathVariable("id") Long id) {
        clubService.delete(id);
    }
}
