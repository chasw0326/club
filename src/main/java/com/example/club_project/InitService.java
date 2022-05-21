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
        // 클럽1 [1,2,4] ,클럽2[2,3,4] 클럽3[1] 클럽6[1,3,4]
        public void initPosts() {
            postService.register(1L, 1L, "제목1", "내용1");
            postService.register(1L, 3L, "제목2", "내용2");
            postService.register(1L, 1L, "제목3", "내용3");
            postService.register(1L, 3L, "제목4", "내용4");
            postService.register(1L, 6L, "제목5", "내용5");
            postService.register(1L, 5L, "제목6", "내용6");
//            postService.register(2L, 5L, "제목7", "내용7");
            postService.register(2L, 2L, "제목8", "내용8");
            postService.register(2L, 1L, "제목9", "내용9");
            postService.register(2L, 1L, "제목10", "내용10");
            postService.register(2L, 6L, "제목11", "내용11");
            postService.register(2L, 6L, "제목12", "내용12");
            postService.register(2L, 1L, "제목13", "내용13");
            postService.register(3L, 2L, "제목14", "내용14");
            postService.register(3L, 2L, "제목15", "내용15");
            postService.register(3L, 6L, "제목16", "내용16");
            postService.register(3L, 6L, "제목17", "내용17");
            postService.register(3L, 6L, "제목18", "내용18");
            postService.register(3L, 2L, "제목19", "내용19");
            postService.register(4L, 1L, "제목20", "내용20");
            postService.register(4L, 2L, "제목21", "내용21");
            postService.register(4L, 1L, "제목22", "내용22");
            postService.register(4L, 1L, "제목23", "내용23");
            postService.register(4L, 2L, "제목23", "내용24");

        }
        // 클럽1 [1,2,4] ,클럽2[2,3,4] 클럽3[1] 클럽6[1,3,4]
        public void initComments() {
            commentService.register(1L, 1L, "댓글1");
            commentService.register(2L, 2L, "댓글2");
            commentService.register(2L, 3L, "댓글3");
            commentService.register(4L, 3L, "댓글4");
            commentService.register(2L, 5L, "댓글5");
            commentService.register(2L, 5L, "댓글6");
            commentService.register(3L, 7L, "댓글7");
            commentService.register(4L, 5L, "댓글8");
            commentService.register(3L, 13L, "댓글9");
            commentService.register(4L, 5L, "댓글10");
            commentService.register(3L, 5L, "댓글11");
            commentService.register(2L, 7L, "댓글12");
            commentService.register(2L, 5L, "댓글13");
            commentService.register(2L, 5L, "댓글14");
            commentService.register(3L, 7L, "댓글15");
            commentService.register(4L, 5L, "댓글16");
            commentService.register(3L, 13L, "댓글17");
            commentService.register(1L, 1L, "댓글18");
            commentService.register(2L, 2L, "댓글19");
            commentService.register(2L, 3L, "댓글20");
            commentService.register(4L, 3L, "댓글21");
            commentService.register(2L, 5L, "댓글22");
            commentService.register(2L, 5L, "댓글23");
            commentService.register(3L, 7L, "댓글24");
            commentService.register(4L, 5L, "댓글25");
            commentService.register(3L, 13L, "댓글26");
            commentService.register(4L, 5L, "댓글27");
            commentService.register(3L, 5L, "댓글28");
            commentService.register(2L, 7L, "댓글29");
            commentService.register(3L, 7L, "댓글30");
            commentService.register(4L, 5L, "댓글31");
            commentService.register(3L, 13L, "댓글32");
            commentService.register(1L, 1L, "댓글33");
            commentService.register(2L, 2L, "댓글34");
            commentService.register(2L, 3L, "댓글35");
            commentService.register(4L, 3L, "댓글36");
            commentService.register(2L, 5L, "댓글37");
            commentService.register(2L, 5L, "댓글38");
        }
    }
}
