package com.example.club_project.controller.comment;

import com.example.club_project.controller.post.PostDTO;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


public class CommentDTO {

    @Getter
    public static class PostRequest {

        @NotNull
        private Long postId;

        @NotBlank(message = "댓글내용은 필수 입니다.")
        private String content;
    }

    @Getter
    public static class PutRequest {

        @NotBlank
        private Long commentId;

        @NotBlank(message = "댓글내용은 필수 입니다.")
        private String content;
    }


    @Builder
    @Getter
    public static class Response {

        private PostDTO.Response postDto;

        private List<CommentData> commentData;
    }

    @Builder
    @Getter
    public static class CommentData {

        @NotBlank
        private Long commentId;

        private String profileUrl;
        @NotBlank
        private String nickname;
        @NotBlank
        private String content;
        @NotBlank
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    public static class myComment {

        @NotBlank
        private Long postId;

        private String postTitle;

        private String postContent;

        private CommentData commentData;
    }
}