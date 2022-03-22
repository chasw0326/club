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

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @PostMapping("/post")
    public void register(@AuthenticationPrincipal AuthUserDTO authUser,
                         Long clubId,
                         @RequestBody @Valid PostDTO.Request request) {
        postService.register(
                authUser.getId(),
                clubId,
                request.getTitle(),
                request.getContent());
    }

    @GetMapping("/post")
    public List<PostDTO.Response> getClubPosts(@AuthenticationPrincipal AuthUserDTO authUser,
                                               Long clubId,
                                               @PageableDefault(
                                                       size = 20,
                                                       sort = "id",
                                                       direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getClubPosts(authUser.getId(), clubId, pageable);
    }

    @PutMapping("/post/{postId}")
    public void update(@AuthenticationPrincipal AuthUserDTO authUser,
                       Long clubId,
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

    @DeleteMapping("/post/{postId}")
    public void delete(@AuthenticationPrincipal AuthUserDTO authUser,
                       Long clubId,
                       @PathVariable("postId") Long postId) {

        postService.delete(
                authUser.getId(),
                clubId,
                postId);
    }
}
