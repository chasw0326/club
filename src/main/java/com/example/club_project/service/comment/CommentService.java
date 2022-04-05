package com.example.club_project.service.comment;

import com.example.club_project.controller.comment.CommentDTO;
import com.example.club_project.domain.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    /*
    글에 딸린 댓글들 가져오기 with 글
    에브리타임 구조랑 동일
     */
    CommentDTO.Response getCommentDtos(Long postId);

    /*
    내가 쓴 댓글 목록 with postId
     */
    List<CommentDTO.myComment> getMyComments(Long userId, Pageable pageable);

    Long register(Long userId, Long postId, String content);

    Comment getComment(Long commentId);

    /*
    수정은 본인만 가능
     */
    Long update(Long userId, Long commentId, String content);

    /*
    삭제는 본인, 운영진 가능
     */
    void delete(Long userId, Long clubId, Long commentId);
}