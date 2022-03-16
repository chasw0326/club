package com.example.club_project.service.comment;

import com.example.club_project.controller.comment.CommentDTO;
import com.example.club_project.controller.post.PostDTO;
import com.example.club_project.domain.*;
import com.example.club_project.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @BeforeAll
    static void setUp(@Autowired CommentRepository commentRepository,
                      @Autowired ClubRepository clubRepository,
                      @Autowired PostRepository postRepository,
                      @Autowired UserRepository userRepository,
                      @Autowired CategoryRepository categoryRepository) {

        User user = User.builder()
                .profileUrl("C:\\user")
                .nickname("testNickname")
                .password("abcABC123!@#")
                .email("test@naver.com")
                .introduction("intro")
                .university("대학")
                .name("test")
                .build();

        userRepository.save(user);

        User user2 = User.builder()
                .profileUrl("C:\\user2")
                .nickname("testNickname2")
                .password("abcABC123!@#")
                .email("test2@naver.com")
                .university("대학2")
                .introduction("intro2")
                .name("test2")
                .build();

        userRepository.save(user2);

        User user3 = User.builder()
                .profileUrl("C:\\user3")
                .nickname("testNickname3")
                .password("abcABC123!@#")
                .email("test3@naver.com")
                .university("대학3")
                .introduction("intro3")
                .name("test3")
                .build();

        userRepository.save(user3);


        Category category = Category.builder()
                .description("언어에 대한 탐구")
                .name("어학")
                .build();

        categoryRepository.save(category);

        Club club = Club.builder()
                .category(category)
                .name("토익")
                .address("5호관")
                .description("영어공부합시다")
                .imageUrl("C:\\Club")
                .university("대학2")
                .build();

        clubRepository.save(club);

        Post post = Post.builder()
                .user(user)
                .title("title")
                .content("content")
                .club(club)
                .build();

        postRepository.save(post);

        Post post2 = Post.builder()
                .user(user2)
                .title("title2")
                .content("content2")
                .club(club)
                .build();
        postRepository.save(post2);

        IntStream.rangeClosed(1, 21).forEach(i -> {
            Post p;
            User u = User.builder()
                    .university("인하대학교")
                    .introduction("소개")
                    .password("123456789")
                    .email("email" + i + "naver.com")
                    .profileUrl("C:\\user" + i)
                    .nickname("nick" + i)
                    .name("name" + i)
                    .build();

            userRepository.save(u);

            if (i % 2 == 0) {
                p = post;
            } else {
                p = post2;
            }
            Comment comment = Comment.builder()
                    .content("댓글 내용" + i)
                    .user(u)
                    .post(p)
                    .build();

            commentRepository.save(comment);
        });


    }

    @DisplayName("getCommentDtos 글, 글작성자 정보, 댓글 작성자정보, 댓글수, 댓글들을 가져온다.")
    @Test
    @Order(1)
    void Should_GetCommentDtos() {
        CommentDTO.Response commentDtos = commentService.getCommentDtos(1L);
        List<CommentDTO.CommentData> dataList = commentDtos.getCommentData();
        int i = 2;
        for (CommentDTO.CommentData data : dataList) {
            assertEquals("댓글 내용" + i, data.getContent());
            assertEquals("nick" + i, data.getNickname());
            assertEquals("C:\\user" + i, data.getProfileUrl());
            i += 2;
        }
        PostDTO.Response postDto = commentDtos.getPostDto();
        assertEquals(10, postDto.getCommentCnt());
        assertEquals("content", postDto.getContent());
        assertEquals("C:\\user", postDto.getProfileUrl());
        assertEquals("title", postDto.getTitle());
        assertEquals("testNickname", postDto.getNickname());

        CommentDTO.Response commentDtos2 = commentService.getCommentDtos(2L);
        List<CommentDTO.CommentData> dataList2 = commentDtos2.getCommentData();
        int j = 1;
        for (CommentDTO.CommentData data : dataList2) {
            assertEquals("댓글 내용" + j, data.getContent());
            assertEquals("nick" + j, data.getNickname());
            assertEquals("C:\\user" + j, data.getProfileUrl());
            j += 2;
        }
        PostDTO.Response postDto2 = commentDtos2.getPostDto();
        assertEquals(11, postDto2.getCommentCnt());
        assertEquals("content2", postDto2.getContent());
        assertEquals("C:\\user2", postDto2.getProfileUrl());
        assertEquals("title2", postDto2.getTitle());
        assertEquals("testNickname2", postDto2.getNickname());
    }

    @DisplayName("delete, 정상적인 댓글 삭제")
    @Test
    @Order(2)
    void Should_Delete() {
        Long commentId = 21L;
        Long userId = commentService.getComment(commentId).getUser().getId();
        commentService.delete(userId, 1L, commentId);
        Throwable ex = assertThrows(EntityNotFoundException.class, () ->
                commentService.getComment(commentId)
        );
    }

    @DisplayName("delete, 다른 사람이 댓글 삭제 시도")
    @Test
    @Order(3)
    void Should_ThrowException_WhenDeleteByStranger() {
        Long commentId = 21L;
        Throwable ex = assertThrows(RuntimeException.class, () ->
                commentService.delete(9999999999L, 1L, commentId)
        );
    }

    @DisplayName("getMyComment, 내 댓글들 가져오기")
    @Test
    @Order(4)
    void Should_GetMyComment() {
        for (int i = 1; i <= 10; i++) {
            commentService.register(3L, 1L, "content " + i);
        }
        List<CommentDTO.myComment> myComments = commentService.getMyComment(3L);
        Long commentId = 31L;
        for (CommentDTO.myComment myComment : myComments) {
            CommentDTO.CommentData commentData = myComment.getCommentData();
            assertEquals(commentId--, commentData.getCommentId());
        }
    }

    @Test
    @Order(5)
    void Should_GetCommentEntity() {
        Comment comment = commentService.getComment(1L);
        assertEquals(1L, comment.getId());
    }


    @Test
    @Order(6)
    void Should_UpdateComment() {
        Comment comment = commentService.getComment(3L);
        commentService.update(comment.getUser().getId(), 3L, "내용수정");
        Comment updateComment = commentService.getComment(3L);
        assertEquals("내용수정", updateComment.getContent());
    }

    @DisplayName("다른사람이 수정 시도")
    @Test
    @Order(7)
    void Should_ThrowException_WhenUpdateByStranger() {
        Throwable ex = assertThrows(AccessDeniedException.class, () ->
                commentService.update(1000000000L, 2L, "내용수정")
        );
    }
}