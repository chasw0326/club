package com.example.club_project.controller.comment;

import com.example.club_project.security.dto.AuthUserDTO;
import com.example.club_project.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping("/comment")
    public CommentDTO.Response getAllCommentsWithPost(Long postId){
        return commentService.getCommentDtos(postId);
    }

    @PostMapping("/comment")
    public void register(@AuthenticationPrincipal AuthUserDTO authUser,
                         @RequestBody CommentDTO.PostRequest request){

        commentService.register(
                authUser.getId(),
                request.getPostId(),
                request.getContent()
        );
    }

    @PutMapping("/comment")
    public void update(@AuthenticationPrincipal AuthUserDTO authUser,
                       @RequestBody CommentDTO.PutRequest request){
        commentService.update(authUser.getId(), request.getCommentId(), request.getContent());
    }

    @DeleteMapping("/comment")
    public void delete(@AuthenticationPrincipal AuthUserDTO authUser,
                       Long clubId,
                       Long commentId){

        commentService.delete(
                authUser.getId(),
                clubId,
                commentId
        );
    }
}
