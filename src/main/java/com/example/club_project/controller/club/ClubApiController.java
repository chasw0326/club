package com.example.club_project.controller.club;

import com.example.club_project.service.club.ClubService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@RestController
@RequiredArgsConstructor
public class ClubApiController {

    private final ClubService clubService;

    /**
     * 복수 건 조회 요청
     */
    @GetMapping("clubs")
    public List<ClubDTO.Response> searchClubs(@RequestBody ClubDTO.MultiSearchOption searchOption, Pageable pageable) {
        if (ObjectUtils.isEmpty(searchOption.getCategories())) {
            return clubService.getClubDtos(searchOption.getUniversity(), pageable);
        }
        return clubService.getClubDtos(searchOption.getCategories(), searchOption.getUniversity(), pageable);
    }

    /**
     * 단 건 조회 요청
     */
    @GetMapping("club")
    public ClubDTO.Response searchClub(@RequestBody ClubDTO.SingleSearchOption searchOption) {
        return clubService.getClubDto(searchOption.getName(), searchOption.getUniversity());
    }

    @GetMapping("club/{id}")
    public ClubDTO.Response searchClub(@PathVariable("id") Long id) {
        return clubService.getClubDto(id);
    }

    /**
     * 등록 요청
     */
    @PostMapping("club")
    public ClubDTO.Response registerClub(@RequestBody ClubDTO.RegisterRequest req) {
        return clubService.registerClub(
                req.getName(),
                req.getAddress(),
                req.getUniversity(),
                req.getDescription(),
                req.getCategoryName(),
                req.getImageUrl()
        );
    }

    /**
     * 수정 요청
     */
    @PutMapping("club/{id}")
    public ClubDTO.Response updateClub(@PathVariable("id") Long id, @RequestBody ClubDTO.UpdateRequest req) {
        return clubService.updateClub(
                id,
                req.getName(),
                req.getAddress(),
                req.getUniversity(),
                req.getDescription(),
                req.getCategoryName(),
                req.getImageUrl()
        );
    }

    /**
     * 삭제 요청
     */
    @DeleteMapping("club/{id}")
    public void deleteClub(@PathVariable("id") Long id) {
        clubService.delete(id);
    }
}
