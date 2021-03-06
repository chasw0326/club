package com.example.club_project.service.post;

import com.example.club_project.controller.post.PostDTO;
import com.example.club_project.domain.*;
import com.example.club_project.repository.CategoryRepository;
import com.example.club_project.repository.ClubRepository;
import com.example.club_project.repository.PostRepository;
import com.example.club_project.repository.UserRepository;
import com.example.club_project.service.clubjoinstate.ClubJoinStateService;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private ClubJoinStateService clubJoinStateService;

    @BeforeAll
    static void setUp(@Autowired UserRepository userRepository,
                      @Autowired CategoryRepository categoryRepository,
                      @Autowired ClubRepository clubRepository,
                      @Autowired PostRepository postRepository,
                      @Autowired ClubJoinStateService clubJoinStateService) {

        User user = userRepository.save(User.builder()
                .nickname("testNickname")
                .password("abcABC123!@#")
                .email("testing@google.com")
                .profileUrl("C:\\user")
                .introduction("test Intro")
                .name("testName")
                .university("???????????????")
                .build());


        Category category = categoryRepository.save(
                Category.builder()
                        .name("??????")
                        .description("??????, ??????, ?????? ??????")
                        .build());


        Club club = clubRepository.save(Club.builder()
                .name("???????????????")
                .category(category)
                .address("5?????? 115")
                .description("?????????????????? ?????????")
                .imageUrl("C:\\club")
                .university("???????????????")
                .build());

        clubJoinStateService.register(1L, 1L, JoinState.MEMBER.getCode());

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Post post = Post.builder()
                    .user(user)
                    .club(club)
                    .title("????????? ?????? " + i)
                    .content("????????? ?????? " + i)
                    .build();

            postRepository.save(post);
        });
    }

    @DisplayName("???????????? ?????? ????????? ????????? ???")
    @Test
    @Order(value = 1)
    void Should_ThrowException_WhenNotExistsPost() {
        Throwable ex = assertThrows(EntityNotFoundException.class, () ->
                postService.getPost(99999999999L)
        );
        assertEquals("throw notFoundException", ex.getMessage());
    }

    @Order(value = 2)
    @DisplayName("????????? ????????? dto ????????? ???")
    @Test
    void Should_GetPostDto() {
        PostDTO.Response dto = postService.getPostDto(5L);
        assertEquals("????????? ?????? 5", dto.getContent());
        assertEquals("????????? ?????? 5", dto.getTitle());
        assertEquals(0L, dto.getCommentCnt());
        assertEquals("testNickname", dto.getNickname());
        assertEquals("C:\\user", dto.getProfileUrl());

        PostDTO.Response dto2 = postService.getPostDto(2L);
        assertEquals("????????? ?????? 2", dto2.getContent());
        assertEquals("????????? ?????? 2", dto2.getTitle());
        assertEquals(0L, dto.getCommentCnt());
        assertEquals("testNickname", dto2.getNickname());
        assertEquals("C:\\user", dto2.getProfileUrl());

    }

    @DisplayName("???????????? ????????? dto ????????? ???")
    @Test
    @Order(value = 3)
    void Should_GetPostDtos() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<PostDTO.Response> dtos = postService.getClubPosts(1L, 1L, pageable);
        int i = 10;
        for (PostDTO.Response dto : dtos) {
            assertEquals("C:\\user", dto.getProfileUrl());
            assertEquals("testNickname", dto.getNickname());
            assertEquals("????????? ?????? " + i, dto.getTitle());
            assertEquals("????????? ?????? " + i, dto.getContent());
            assertEquals(0L, dto.getCommentCnt());
            i--;
        }
    }

    @DisplayName("?????? ????????? ?????????")
    @Test
    void Should_ThrowExceptionWhenNotClubMember() {
        clubJoinStateService.delete(1L, 1L);
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        try {
            assertThrows(AccessDeniedException.class, () ->
                    postService.getClubPosts(1L, 1L, pageable)
            );
        }finally {
            clubJoinStateService.register(1L, 1L,JoinState.MEMBER.getCode());
        }
    }

    @DisplayName("??? ?????? ????????? ????????? ????????????")
    @Test
    @Order(value = 4)
    void Should_GetOnePost() {
        Post post = postService.getPost(7L);
        assertEquals("????????? ?????? 7", post.getContent());
        assertEquals("????????? ?????? 7", post.getTitle());
        assertEquals(1L, post.getUser().getId());
        assertEquals(7L, post.getId());
    }


    @DisplayName("?????? ????????? ??? ?????? ??????")
    @Test
    @Order(value = 5)
    void Should_ThrowException_WhenUpdateByStranger() {
        Throwable ex = assertThrows(AccessDeniedException.class, () ->
                postService.update(999999999L, 1L,1L, "????????????", "????????????")
        );
        assertEquals("????????? ????????? ????????????.", ex.getMessage());
    }

    @DisplayName("???????????? ??? ??????")
    @Test
    @Order(value = 6)
    void Should_UpdatePost() {
        postService.update(1L, 1L, 4L, "?????? ??????", "?????? ??????");
        Post post = postService.getPost(4L);

        assertEquals("?????? ??????", post.getTitle());
        assertEquals("?????? ??????", post.getContent());
        assertEquals(1L, post.getUser().getId());
        assertEquals(1L, post.getClub().getId());
    }

    @DisplayName("?????? ????????? ?????? ??????")
    @Test
    @Order(value = 7)
    void Should_ThrowException_WhenDeleteByStranger() {
        Throwable ex = assertThrows(AccessDeniedException.class, () ->
                postService.delete(1000000L, 1L, 1L)
        );
        assertEquals("????????? ????????? ????????????.", ex.getMessage());
    }

    @DisplayName("???????????? ?????? ??????")
    @Test
    @Order(value = 8)
    void Should_DeletePost() {
        assertDoesNotThrow(() ->
                postService.getPost(3L)
        );
        postService.delete(1L, 1L, 3L);
        Throwable ex = assertThrows(EntityNotFoundException.class, () ->
                postService.getPost(3L)
        );
        assertEquals("throw notFoundException", ex.getMessage());
    }

    @DisplayName("??? ??????")
    @Test
    @Order(value = 9)
    void Should_RegisterPost() {
        Long postId = postService.register(1L, 1L,
                "?????????????????????", "?????????????????????");
        Post post = postService.getPost(postId);
        assertEquals("?????????????????????", post.getTitle());
        assertEquals("?????????????????????", post.getContent());
        assertEquals(1L, post.getClub().getId());
        assertEquals(1L, post.getUser().getId());
        assertEquals(postId, post.getId());
    }

    @Test
    @Order(value = 10)
    void verifyIsExists() {
        assertFalse(postService.isExists(9999999999L));
        assertTrue(postService.isExists(10L));
    }

    @DisplayName("?????? ??? ??? ????????????")
    @Test
    @Order(value = 11)
    void Should_GetMyPosts() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        int size = postService.getMyPosts(1L, pageable).size();
        assertEquals(10, size);
    }

    @Test
    @Order(value = 12)
    void Sholud_DeletePostsWhenLeaveClub() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        postService.deleteWhenLeaveClub(1L, 1L);
        assertEquals(0, postService.getMyPosts(1L, pageable).size());
    }
}
