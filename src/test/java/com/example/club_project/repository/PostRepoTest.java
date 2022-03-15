package com.example.club_project.repository;

import com.example.club_project.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostRepoTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ClubRepository clubRepository;


    @Test
    void ex() {

        User user = userRepository.save(User.builder()
                .nickname("testNickname")
                .password("abcABC123!@#")
                .email("testing@google.com")
                .introduction("test Intro")
                .name("testName")
                .university("인하대학교")
                .build());

        Category category = categoryRepository.save(
                Category.builder()
                        .name("어학")
                        .description("토익, 토플, 오픽 등등")
                        .build());


        Club club = clubRepository.save(Club.builder()
                .name("토익동아리")
                .category(category)
                .address("5호관 115")
                .description("토익공부하는 동아리")
                .imageUrl("C:\\club")
                .university("인하대학교")
                .build());

        Post post = Post.builder()
                .club(club)
                .user(user)
                .title("제목")
                .content("내용")
                .build();

        postRepository.save(post);

        Post post2 = Post.builder()
                .club(club)
                .user(user)
                .title("제목2")
                .content("내용2")
                .build();

        postRepository.save(post2);

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Comment comment = Comment.builder()
                    .post(post)
                    .user(user)
                    .content("댓글내용")
                    .build();
            commentRepository.save(comment);
        });

        IntStream.rangeClosed(1, 8).forEach(i -> {
            Comment comment = Comment.builder()
                    .post(post2)
                    .user(user)
                    .content("댓글내용2")
                    .build();
            commentRepository.save(comment);
        });

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        List<Object[]> result = postRepository.getPostWithCommentCountByClubId(1L, pageable);

        for(Object[] arr : result){
            Post post3 = (Post) arr[0];
            System.out.println(post3.getContent());
            System.out.println(Arrays.toString(arr));
        }

        List<Object[]> result2 = postRepository.getPostWithComment(1L);

        System.out.println("-----------------------------------");
        for(Object[] arr: result2){
            System.out.println(Arrays.toString(arr));
        }

    }
}
