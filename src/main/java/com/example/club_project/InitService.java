package com.example.club_project;

import com.example.club_project.domain.JoinState;
import com.example.club_project.domain.User;
import com.example.club_project.service.category.CategoryService;
import com.example.club_project.service.club.ClubService;
import com.example.club_project.service.clubjoinstate.ClubJoinStateService;
import com.example.club_project.service.comment.CommentService;
import com.example.club_project.service.post.PostService;
import com.example.club_project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Slf4j
@Profile("local")
@Component
@RequiredArgsConstructor
public class InitService {

    private final InitTestService initTestService;

    @PostConstruct
    public void init() {
        log.info("start initTestSetting");
        initTestService.initUsers();
        initTestService.initCategories();
        initTestService.initClubs();
        initTestService.initClubJoinStates();
        initTestService.initPosts();
        initTestService.initComments();
        log.info("end initTestSetting");
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitTestService {

        private static final Pair<String, String>[] CATEGORIES =
            new Pair[]{
                Pair.of("문화/예술/공연", "문화/예술/공연 관련 동아리입니다."),
                Pair.of("봉사/사회활동", "봉사/사회활동 관련 동아리입니다."),
                Pair.of("학술/교양", "학술/교양 관련 동아리입니다."),
                Pair.of("창업/취업", "창업/취업 관련 동아리입니다."),
                Pair.of("어학", "어학 관련 동아리입니다."),
                Pair.of("체육", "체육 관련 동아리입니다."),
                Pair.of("친목", "친목 관련 동아리입니다."),
            };

        private final UserService userService;
        private final CategoryService categoryService;
        private final ClubService clubService;
        private final ClubJoinStateService clubJoinStateService;
        private final PostService postService;
        private final CommentService commentService;

        public void initUsers() {
            User testUser1 = User.builder()
                    .email("test@gmail.com")
                    .password("1q2w3eQWE!@#")
                    .name("test")
                    .nickname("testNickname")
                    .university("서울사이버대학교")
                    .introduction("안녕하세요")
                    .build();

            User testUser2 = User.builder()
                    .email("test2@gmail.com")
                    .password("1q2w3eQWE!@#")
                    .name("test11")
                    .nickname("testNickname2")
                    .university("서울사이버대학교")
                    .introduction("안녕하세요")
                    .build();

            User testUser3 = User.builder()
                    .email("test3@gmail.com")
                    .password("1q2w3eQWE!@#")
                    .name("test12")
                    .nickname("testNickname3")
                    .university("서울사이버대학교")
                    .introduction("안녕하세요")
                    .build();

            User testUser4 = User.builder()
                    .email("test4@gmail.com")
                    .password("1q2w3eQWE!@#")
                    .name("test13")
                    .nickname("testNickname4")
                    .university("서울사이버대학교")
                    .introduction("안녕하세요")
                    .build();

            userService.signup(testUser1);
            userService.signup(testUser2);
            userService.signup(testUser3);
            userService.signup(testUser4);
        }

        public void initCategories() {
            for (Pair<String, String> category : CATEGORIES) {
                categoryService.register(category.getLeft(), category.getRight());
            }
        }

        public void initClubs() {
            clubService.registerClub("문화/예술/공연1", "A동", "서울사이버대학교", "문화/예술/공연 동아리입니다.", 1L);
            clubService.registerClub("봉사/사회활동1", "A동", "서울사이버대학교", "봉사/사회활동 동아리입니다.", 2L);
            clubService.registerClub("학술/교양1", "A동", "서울사이버대학교", "학술/교양 동아리입니다.", 3L);
            clubService.registerClub("창업/취업1", "A동", "서울사이버대학교", "창업/취업 동아리입니다.", 4L);
            clubService.registerClub("어학1", "A동", "서울사이버대학교", "어학 동아리입니다.", 5L);
            clubService.registerClub("체육1", "A동", "서울사이버대학교", "체육 동아리입니다.", 6L);
            clubService.registerClub("친목1", "A동", "서울사이버대학교", "친목 동아리입니다.", 7L);
            clubService.registerClub("문화/예술/공연2", "A동", "서울사이버대학교", "문화/예술/공연 동아리입니다.", 1L);
            clubService.registerClub("봉사/사회활동2", "A동", "서울사이버대학교", "봉사/사회활동 동아리입니다.", 2L);
            clubService.registerClub("학술/교양2", "A동", "서울사이버대학교", "학술/교양 동아리입니다.", 3L);
            clubService.registerClub("창업/취업2", "A동", "서울사이버대학교", "창업/취업 동아리입니다.", 4L);
            clubService.registerClub("어학2", "A동", "서울사이버대학교", "어학 동아리입니다.", 5L);
            clubService.registerClub("체육2", "A동", "서울사이버대학교", "체육 동아리입니다.", 6L);
            clubService.registerClub("친목2", "A동", "서울사이버대학교", "친목 동아리입니다.", 7L);
            clubService.registerClub("문화/예술/공연3", "A동", "테스트대학교", "문화/예술/공연 동아리입니다.", 1L);
            clubService.registerClub("봉사/사회활동3", "A동", "테스트대학교", "봉사/사회활동 동아리입니다.", 2L);
            clubService.registerClub("학술/교양3", "A동", "테스트대학교", "학술/교양 동아리입니다.", 3L);
            clubService.registerClub("창업/취업3", "A동", "테스트대학교", "창업/취업 동아리입니다.", 4L);
            clubService.registerClub("어학3", "A동", "테스트대학교", "어학 동아리입니다.", 5L);
            clubService.registerClub("체육3", "A동", "테스트대학교", "체육 동아리입니다.", 6L);
            clubService.registerClub("친목3", "A동", "테스트대학교", "친목 동아리입니다.", 7L);
        }

        public void initClubJoinStates() {
            int masterCode = JoinState.MASTER.getCode();
            int managerCode = JoinState.MANAGER.getCode();
            int memberCode = JoinState.MEMBER.getCode();
            int notJoinedCode = JoinState.NOT_JOINED.getCode();

            clubJoinStateService.register(1L,1L, masterCode);
            clubJoinStateService.register(1L,3L, memberCode);
            clubJoinStateService.register(1L,4L, notJoinedCode);
            clubJoinStateService.register(1L,5L, masterCode);
            clubJoinStateService.register(1L,6L, memberCode);

            clubJoinStateService.register(2L,6L, memberCode);
            clubJoinStateService.register(3L,6L, managerCode);
            clubJoinStateService.register(4L,6L, masterCode);

            clubJoinStateService.register(2L,1L, managerCode);
            clubJoinStateService.register(3L,1L, notJoinedCode);
            clubJoinStateService.register(4L,1L, managerCode);

            clubJoinStateService.register(2L,2L, masterCode);
            clubJoinStateService.register(3L,2L, managerCode);
            clubJoinStateService.register(4L,2L, memberCode);
            clubJoinStateService.register(1L,2L, notJoinedCode);
        }

        public void initPosts() {
            postService.register(2L, 6L, "클럽6유저2제목1", "클럽6유저1내용1");
            postService.register(2L, 6L, "클럽6유저2제목2", "클럽6유저1내용2");
            postService.register(3L, 6L, "클럽6유저3제목1", "클럽6유저1내용1");
            postService.register(3L, 6L, "클럽6유저3제목2", "클럽6유저1내용2");
            postService.register(3L, 6L, "클럽6유저3제목3", "클럽6유저1내용3");
        }

        public void initComments() {
            commentService.register(4L, 1L, "글1유저4댓글1");
            commentService.register(4L, 1L, "글1유저4댓글2");
            commentService.register(4L, 3L, "글3유저4댓글1");
            commentService.register(4L, 4L, "글4유저4댓글1");
            commentService.register(4L, 5L, "글5유저4댓글1");
            commentService.register(4L, 5L, "글5유저4댓글2");
            commentService.register(4L, 5L, "글5유저4댓글3");
            commentService.register(4L, 5L, "글5유저4댓글4");
        }
    }
}
