package com.example.club_project.controller.comment;

import com.example.club_project.security.dto.AuthUserDTO;
import com.example.club_project.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequestMapping("/api/comment")
@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;


    @GetMapping
    public CommentDTO.Response getAllCommentsWithPost(@RequestParam("postId") Long postId) {
        return commentService.getCommentDtos(postId);
    }

    @PostMapping
    public void register(@AuthenticationPrincipal AuthUserDTO authUser,
                         @RequestBody @Valid CommentDTO.PostRequest request) {

        commentService.register(
                authUser.getId(),
                request.getPostId(),
                request.getContent()
        );
    }

    @PutMapping("/{commentId}")
    public void update(@AuthenticationPrincipal AuthUserDTO authUser,
                       @RequestBody CommentDTO.PutRequest request,
                       @PathVariable("commentId") Long commentId) {

        commentService.update(
                authUser.getId(),
                commentId,
                request.getContent()
        );
    }

    @DeleteMapping("/{commentId}")
    public void delete(@AuthenticationPrincipal AuthUserDTO authUser,
                       @RequestParam("clubId") Long clubId,
                       @PathVariable("commentId") Long commentId) {

        commentService.delete(
                authUser.getId(),
                clubId,
                commentId
        );
    }
}
