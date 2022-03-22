package com.example.club_project.service.comment;

import com.example.club_project.controller.comment.CommentDTO;
import com.example.club_project.domain.Comment;
import com.example.club_project.repository.CommentRepository;
import com.example.club_project.service.clubjoinstate.ClubJoinStateService;
import com.example.club_project.service.post.PostService;
import com.example.club_project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;
    private final ClubJoinStateService clubJoinStateService;

    @Override
    @Transactional
    public Long register(Long userId, Long postId, String content) {
        if (!postService.isExists(postId)) {
            throw new EntityNotFoundException("throw notFoundException");
        }
        log.info("userId: {} register Comment to postId: {}", userId, postId);

        Comment comment = Comment.builder()
                .content(content)
                .post(postService.getPost(postId))
                .user(userService.getUser(userId))
                .build();
        commentRepository.save(comment);
        return comment.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getComment(Long commentId){
        return commentRepository.findById(commentId).
                orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDTO.Response getCommentDtos(Long postId) {
        if (!postService.isExists(postId)) {
            throw new EntityNotFoundException("throw notFoundException");
        }
        List<CommentDTO.CommentData> commentData = new ArrayList<>();
        List<Comment> commentList = commentRepository.getAllByPost_IdOrderByIdAsc(postId);
        for (Comment comment : commentList) {
            commentData.add(CommentDTO.CommentData.builder()
                    .commentId(comment.getId())
                    .nickname(comment.getUser().getNickname())
                    .profileUrl(comment.getUser().getProfileUrl())
                    .content(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .build());
        }

        return CommentDTO.Response.builder()
                .postDto(postService.getPostDto(postId))
                .commentData(commentData)
                .build();
    }

    @Override
    @Transactional
    public Long update(Long userId, Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("수정 권한 없음");
        }
        comment.update(content);
        return comment.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO.myComment> getMyComment(Long userId, Pageable pageable) {
        List<Comment> myCommentList = commentRepository.getAllByUser_IdOrderByIdDesc(userId, pageable);
        List<CommentDTO.myComment> myComments = new ArrayList<>();
        for (Comment comment : myCommentList) {
            myComments.add(CommentDTO.myComment.builder()
                    .postId(comment.getPost().getId())
                    .commentData(CommentDTO.CommentData.builder()
                            .commentId(comment.getId())
                            .content(comment.getContent())
                            .nickname(comment.getUser().getNickname())
                            .profileUrl(comment.getUser().getProfileUrl())
                            .createdAt(comment.getCreatedAt())
                            .build())
                    .build());
        }
        return myComments;
    }

    @Override
    @Transactional
    public void delete(Long userId, Long clubId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
        if (comment.getUser().getId().equals(userId) || clubJoinStateService.hasManagerRole(userId, clubId)) {
            commentRepository.deleteById(commentId);
        } else { // TODO: 예외 다른걸로 수정 해야한다.
            throw new RuntimeException("지울 수 있는 권한이 없습니다.");
        }
    }
}