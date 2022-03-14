package com.example.club_project.service.post;

import com.example.club_project.controller.post.PostDTO;
import com.example.club_project.domain.Category;
import com.example.club_project.domain.Club;
import com.example.club_project.domain.Post;
import com.example.club_project.domain.User;
import com.example.club_project.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @BeforeAll
    static void setUp(@Autowired UserRepository userRepository,
                      @Autowired CategoryRepository categoryRepository,
                      @Autowired ClubRepository clubRepository,
                      @Autowired PostRepository postRepository) {

        User user = userRepository.save(User.builder()
                .nickname("testNickname")
                .password("abcABC123!@#")
                .email("testing@google.com")
                .profileUrl("C:\\user")
                .introduction("test Intro")
                .name("testName")
                .university("인하대학교")
                .build());

        Category category = categoryRepository.save(
                Category.of("어학", "토익, 토플, 오픽 등등"));

        Club club = clubRepository.save(Club.builder()
                .name("토익동아리")
                .category(category)
                .address("5호관 115")
                .description("토익공부하는 동아리")
                .imageUrl("C:\\club")
                .university("인하대학교")
                .build());

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Post post = Post.builder()
                    .user(user)
                    .club(club)
                    .title("테스트 제목 " + i)
                    .content("테스트 내용 " + i)
                    .build();

            postRepository.save(post);
        });
    }

    @DisplayName("존재하지 않는 포스트 요청할 때")
    @Test
    @Order(value = 1)
    void Should_ThrowException_WhenNotExistsPost() {
        Throwable ex = assertThrows(EntityNotFoundException.class, () ->
                postService.getPost(99999999999L)
        );
        assertEquals("throw notFoundException", ex.getMessage());
    }

    @Order(value = 2)
    @DisplayName("한개의 포스트 dto 가져올 때")
    @Test
    void Should_GetPostDto() {
        PostDTO.Response dto = postService.getPostDto(5L);
        assertEquals("테스트 내용 5", dto.getContent());
        assertEquals("테스트 제목 5", dto.getTitle());
        assertEquals(0L, dto.getCommentCnt());
        assertEquals("testNickname", dto.getNickname());
        assertEquals("C:\\user", dto.getProfileUrl());

        PostDTO.Response dto2 = postService.getPostDto(2L);
        assertEquals("테스트 내용 2", dto2.getContent());
        assertEquals("테스트 제목 2", dto2.getTitle());
        assertEquals(0L, dto.getCommentCnt());
        assertEquals("testNickname", dto2.getNickname());
        assertEquals("C:\\user", dto2.getProfileUrl());

    }

    @DisplayName("여러개의 포스트 dto 가져올 때")
    @Test
    @Order(value = 3)
    void Should_GetPostDtos() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<PostDTO.Response> dtos = postService.getPostDtos(1L, pageable);
        int i = 10;
        for (PostDTO.Response dto : dtos) {
            assertEquals("C:\\user", dto.getProfileUrl());
            assertEquals("testNickname", dto.getNickname());
            assertEquals("테스트 제목 " + i, dto.getTitle());
            assertEquals("테스트 내용 " + i, dto.getContent());
            assertEquals(0L, dto.getCommentCnt());
            i--;
        }
    }


    @DisplayName("한 개의 포스트 엔티티 가져올때")
    @Test
    @Order(value = 4)
    void Should_GetOnePost() {
        Post post = postService.getPost(7L);
        assertEquals("테스트 내용 7", post.getContent());
        assertEquals("테스트 제목 7", post.getTitle());
        assertEquals(1L, post.getUser().getId());
        assertEquals(7L, post.getId());
    }


    @DisplayName("다른 사람이 글 수정 시도")
    @Test
    @Order(value = 5)
    void Should_ThrowException_WhenUpdateByStranger() {
        Throwable ex = assertThrows(AccessDeniedException.class, () ->
                postService.update(999999999L, 1L, "제목수정", "내용수정")
        );
        assertEquals("수정할 권한이 없습니다.", ex.getMessage());
    }

    @DisplayName("정상적인 글 수정")
    @Test
    @Order(value = 6)
    void Should_UpdatePost() {
        postService.update(1L, 1L, "제목 수정", "내용 수정");
        Post post = postService.getPost(1L);

        assertEquals("제목 수정", post.getTitle());
        assertEquals("내용 수정", post.getContent());
        assertEquals(1L, post.getUser().getId());
        assertEquals(1L, post.getClub().getId());
    }

    @DisplayName("다른 사람이 삭제 시도")
    @Test
    @Order(value = 7)
    void Should_ThrowException_WhenDeleteByStranger() {
        Throwable ex = assertThrows(AccessDeniedException.class, () ->
                postService.delete(1000000L, 4L)
        );
        assertEquals("삭제할 권한이 없습니다.", ex.getMessage());
    }

    @DisplayName("정상적인 삭제 요청")
    @Test
    @Order(value = 8)
    void Should_DeletePost() {
        assertDoesNotThrow(() ->
                postService.getPost(3L)
        );

        postService.delete(1L, 3L);

        Throwable ex = assertThrows(EntityNotFoundException.class, () ->
                postService.getPost(3L)
        );
        assertEquals("throw notFoundException", ex.getMessage());
    }

    @DisplayName("글 등록")
    @Test
    @Order(value = 9)
    void Should_RegisterPost(){
        Long postId = postService.register(1L, 1L,
                "등록테스트제목", "등록테스트내용");
        Post post = postService.getPost(postId);
        assertEquals("등록테스트제목", post.getTitle());
        assertEquals("등록테스트내용", post.getContent());
        assertEquals(1L, post.getClub().getId());
        assertEquals(1L, post.getUser().getId());
        assertEquals(postId, post.getId());
    }

    @Test
    @Order(value = 10)
    void verifyIsExists(){
        assertFalse(postService.isExists(9999999999L));
        assertTrue(postService.isExists(10L));
    }
}