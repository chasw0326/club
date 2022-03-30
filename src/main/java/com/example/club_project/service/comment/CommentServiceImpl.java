package com.example.club_project.service.comment;

import com.example.club_project.controller.comment.CommentDTO;
import com.example.club_project.domain.Club;
import com.example.club_project.domain.Comment;
import com.example.club_project.domain.Post;
import com.example.club_project.domain.User;
import com.example.club_project.exception.custom.ForbiddenException;
import com.example.club_project.exception.custom.NotFoundException;
import com.example.club_project.repository.CommentRepository;
import com.example.club_project.service.club.ClubService;
import com.example.club_project.service.clubjoinstate.ClubJoinStateService;
import com.example.club_project.service.post.PostService;
import com.example.club_project.service.user.UserService;
import com.example.club_project.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ValidateUtil validateUtil;
    private final ClubService clubService;

    @Override
    @Transactional
    public Long register(Long userId, Long postId, String content) {
        Post post = postService.getPost(postId);
        User user = userService.getUser(userId);

        Comment comment = Comment.builder()
                .content(content)
                .post(post)
                .user(user)
                .build();

        validateUtil.validate(comment);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getComment(Long commentId){
        return commentRepository.findById(commentId).
                orElseThrow(() -> new NotFoundException("Can not find comment by commentId: " + commentId));
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDTO.Response getCommentDtos(Long postId) {
        if (!postService.isExists(postId)) {
            throw new NotFoundException("Can not find post by postId: " + postId);
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
        Comment comment = this.getComment(commentId);
        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("No Authority to update");
        }
        comment.update(content);
        validateUtil.validate(comment);
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
        Club club = clubService.getClub(clubId);
        Comment comment = this.getComment(commentId);
        if (comment.getUser().getId().equals(userId) || clubJoinStateService.hasManagerRole(userId, clubId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new ForbiddenException("No Authority to delete");
        }
    }
}