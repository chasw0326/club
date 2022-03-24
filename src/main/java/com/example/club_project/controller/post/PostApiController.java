package com.example.club_project.controller.post;

import com.example.club_project.security.dto.AuthUserDTO;
import com.example.club_project.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/post")
@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @PostMapping
    public void register(@AuthenticationPrincipal AuthUserDTO authUser,
                         @RequestParam(value = "clubId") Long clubId,
                         @RequestBody @Valid PostDTO.Request request) {
        postService.register(
                authUser.getId(),
                clubId,
                request.getTitle(),
                request.getContent());
    }

    @GetMapping
    public List<PostDTO.Response> getClubPosts(@AuthenticationPrincipal AuthUserDTO authUser,
                                               Long clubId,
                                               @PageableDefault(
                                                       size = 20,
                                                       sort = "id",
                                                       direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getClubPosts(authUser.getId(), clubId, pageable);
    }

    @PutMapping("/{postId}")
    public void update(@AuthenticationPrincipal AuthUserDTO authUser,
                       @RequestParam(value = "clubId") Long clubId,
                       @PathVariable("postId") Long postId,
                       @RequestBody PostDTO.Request request) {

        postService.update(
                authUser.getId(),
                clubId,
                postId,
                request.getTitle(),
                request.getContent()
        );
    }

    @DeleteMapping("/{postId}")
    public void delete(@AuthenticationPrincipal AuthUserDTO authUser,
                       @RequestParam(value = "clubId") Long clubId,
                       @PathVariable("postId") Long postId) {

        postService.delete(
                authUser.getId(),
                clubId,
                postId);
    }
}
