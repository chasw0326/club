package com.example.club_project.controller.comment;

import com.example.club_project.controller.post.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;


@Getter
public class CommentDTO {

    public static class Request{

        @NotEmpty(message = "postId는 필수 입니다.")
        private String postId;

        @NotEmpty(message = "댓글내용은 필수 입니다.")
        private String content;
    }

    @Builder
    @AllArgsConstructor
    public static class Response{

        private PostDTO.Response postDto;

        private List<CommentObject> commentObjects;
    }

    @Builder
    @AllArgsConstructor
    public static class CommentObject {

        private String profileUrl;
        @NotEmpty
        private String nickname;
        @NotEmpty
        private String content;
        @NotEmpty
        private LocalDateTime createdAt;

    }
}
