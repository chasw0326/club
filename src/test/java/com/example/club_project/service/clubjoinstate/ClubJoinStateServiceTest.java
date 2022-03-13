package com.example.club_project.service.clubjoinstate;

import com.example.club_project.domain.*;
import com.example.club_project.service.category.CategoryService;
import com.example.club_project.service.club.ClubService;
import com.example.club_project.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ClubJoinStateServiceTest {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ClubService clubService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClubJoinStateService clubJoinStateService;

    /**
     * Mock User
     */
    private User mockUser1, mockUser2, mockUser3, mockUser4, mockUser5, mockUser6, mockUser7, mockUser8, mockUser9, mockUser10;

    /**
     * Mock Club
     */
    private Club mockClub1, mockClub2, mockClub3, mockClub4, mockClub5;

    private int testPagingOffsetSize = 0;
    private int testPagingLimitSize = 50;
    private PageRequest testPageRequest = PageRequest.of(testPagingOffsetSize, testPagingLimitSize);

    @BeforeEach
    public void setupUserAndClub() {
        //init user
        User user1 = User.builder().email("email1").name("na").password("pw").nickname("nick").university("uni").introduction("intro").build();
        User user2 = User.builder().email("email2").name("na").password("pw").nickname("nick").university("uni").introduction("intro").build();
        User user3 = User.builder().email("email3").name("na").password("pw").nickname("nick").university("uni").introduction("intro").build();
        User user4 = User.builder().email("email4").name("na").password("pw").nickname("nick").university("uni").introduction("intro").build();
        User user5 = User.builder().email("email5").name("na").password("pw").nickname("nick").university("uni").introduction("intro").build();
        User user6 = User.builder().email("email6").name("na").password("pw").nickname("nick").university("uni").introduction("intro").build();
        User user7 = User.builder().email("email7").name("na").password("pw").nickname("nick").university("uni").introduction("intro").build();
        User user8 = User.builder().email("email8").name("na").password("pw").nickname("nick").university("uni").introduction("intro").build();
        User user9 = User.builder().email("email9").name("na").password("pw").nickname("nick").university("uni").introduction("intro").build();
        User user10 = User.builder().email("email10").name("na").password("pw").nickname("nick").university("uni").introduction("intro").build();

        Long userId1 = userService.signup(user1);
        Long userId2 = userService.signup(user2);
        Long userId3 = userService.signup(user3);
        Long userId4 = userService.signup(user4);
        Long userId5 = userService.signup(user5);
        Long userId6 = userService.signup(user6);
        Long userId7 = userService.signup(user7);
        Long userId8 = userService.signup(user8);
        Long userId9 = userService.signup(user9);
        Long userId10 = userService.signup(user10);

        mockUser1 = userService.getUser(userId1);
        mockUser2 = userService.getUser(userId2);
        mockUser3 = userService.getUser(userId3);
        mockUser4 = userService.getUser(userId4);
        mockUser5 = userService.getUser(userId5);
        mockUser6 = userService.getUser(userId6);
        mockUser7 = userService.getUser(userId7);
        mockUser8 = userService.getUser(userId8);
        mockUser9 = userService.getUser(userId9);
        mockUser10 = userService.getUser(userId10);

        //init Category
        Category category1 = Category.builder().name("문화/예술/공연").description("문화/예술/공연 관련 동아리입니다.").build();
        Category category2 = Category.builder().name("봉사/사회활동").description("봉사/사회활동 관련 동아리입니다.").build();
        Category category3 = Category.builder().name("학술/교양").description("학술/교양 관련 동아리입니다.").build();
        Category category4 = Category.builder().name("창업/취업").description("창업/취업 관련 동아리입니다.").build();
        Category category5 = Category.builder().name("어학").description("어학 관련 동아리입니다.").build();

        Category mockCategory1 = categoryService.register(category1.getName(), category1.getDescription());
        Category mockCategory2 = categoryService.register(category2.getName(), category2.getDescription());
        Category mockCategory3 = categoryService.register(category3.getName(), category3.getDescription());
        Category mockCategory4 = categoryService.register(category4.getName(), category4.getDescription());
        Category mockCategory5 = categoryService.register(category5.getName(), category5.getDescription());

        //init Club
        Club club1 = Club.builder().name("club1").address("add").university("uni").description("des").category(mockCategory1).imageUrl(null).build();
        Club club2 = Club.builder().name("club2").address("add").university("uni").description("des").category(mockCategory2).imageUrl(null).build();
        Club club3 = Club.builder().name("club3").address("add").university("uni").description("des").category(mockCategory3).imageUrl(null).build();
        Club club4 = Club.builder().name("club4").address("add").university("uni").description("des").category(mockCategory4).imageUrl(null).build();
        Club club5 = Club.builder().name("club5").address("add").university("uni").description("des").category(mockCategory5).imageUrl(null).build();

        mockClub1 = clubService.register(club1.getName(), club1.getAddress(), club1.getUniversity(), club1.getDescription(), club1.getCategory().getName(), null);
        mockClub2 = clubService.register(club2.getName(), club2.getAddress(), club2.getUniversity(), club2.getDescription(), club2.getCategory().getName(), null);
        mockClub3 = clubService.register(club3.getName(), club3.getAddress(), club3.getUniversity(), club3.getDescription(), club3.getCategory().getName(), null);
        mockClub4 = clubService.register(club4.getName(), club4.getAddress(), club4.getUniversity(), club4.getDescription(), club4.getCategory().getName(), null);
        mockClub5 = clubService.register(club5.getName(), club5.getAddress(), club5.getUniversity(), club5.getDescription(), club5.getCategory().getName(), null);
    }

    /**
     * Start Common Region
     */
    // ========== register ==========
    @Test
    @DisplayName("User 엔티티, Club 엔티티, joinState 코드를 사용하여 ClubJoinState 엔티티를 생성할 수 있다.")
    public void Should_Return_Entity_When_User_And_Club_And_JoinState_Is_Valid() {
        //given
        User user1 = mockUser1;
        User user2 = mockUser2;
        User user3 = mockUser3;
        User user4 = mockUser4;

        Club club1 = mockClub1;
        Club club2 = mockClub2;
        Club club3 = mockClub3;
        Club club4 = mockClub4;

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        ClubJoinState registeredClubJoinState1 = clubJoinStateService.register(user1.getId(), club1.getId(), joinStateCodeMaster);
        ClubJoinState registeredClubJoinState2 = clubJoinStateService.register(user2.getId(), club2.getId(), joinStateCodeMANAGER);
        ClubJoinState registeredClubJoinState3 = clubJoinStateService.register(user3.getId(), club3.getId(), joinStateCodeMEMBER);
        ClubJoinState registeredClubJoinState4 = clubJoinStateService.register(user4.getId(), club4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(registeredClubJoinState1.getClub()).isEqualTo(club1);
        assertThat(registeredClubJoinState1.getUser()).isEqualTo(user1);
        assertThat(registeredClubJoinState1.getJoinState()).isEqualTo(JoinState.MASTER);

        assertThat(registeredClubJoinState2.getClub()).isEqualTo(club2);
        assertThat(registeredClubJoinState2.getUser()).isEqualTo(user2);
        assertThat(registeredClubJoinState2.getJoinState()).isEqualTo(JoinState.MANAGER);

        assertThat(registeredClubJoinState3.getClub()).isEqualTo(club3);
        assertThat(registeredClubJoinState3.getUser()).isEqualTo(user3);
        assertThat(registeredClubJoinState3.getJoinState()).isEqualTo(JoinState.MEMBER);

        assertThat(registeredClubJoinState4.getClub()).isEqualTo(club4);
        assertThat(registeredClubJoinState4.getUser()).isEqualTo(user4);
        assertThat(registeredClubJoinState4.getJoinState()).isEqualTo(JoinState.NOT_JOINED);
    }

    @Test
    @DisplayName("유효하지 않은 joinState 코드로는 ClubJoinState 엔티티를 생성할 수 없다.")
    public void Should_Throw_Exception_When_JoinStateCode_Is_Invalid() {
        //given
        User user = mockUser1;
        Club club = mockClub1;

        //when
        int invalidJoinStateCode = 5;

        //then
        assertThrows(IllegalArgumentException.class, () -> clubJoinStateService.register(user.getId(), club.getId(), invalidJoinStateCode));
    }

    @Test
    @DisplayName("탈퇴한 사용자가 재가입 요청을 하는 경우 isUsed 필드를 true로 변경하고 같은 엔티티 객체를 반환한다.")
    public void Should_Return_Same_Entity_When_Left_User_Request_Rejoin() {
        //given
        User user = mockUser1;
        Club club = mockClub1;

        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        ClubJoinState initialJoinState = clubJoinStateService.register(user.getId(), club.getId(), joinStateCodeMEMBER);

        //when
        clubJoinStateService.delete(user.getId(), club.getId());

        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();
        ClubJoinState rejoinState = clubJoinStateService.register(user.getId(), club.getId(), joinStateCodeNotJoined);

        //then
        assertThat(rejoinState).isSameAs(initialJoinState);
        assertThat(rejoinState.getUser()).isEqualTo(initialJoinState.getUser());
        assertThat(rejoinState.getClub()).isEqualTo(initialJoinState.getClub());
    }

    @Test
    @DisplayName("이미 존재하는 (userId, clubId) 쌍에 대해 ClubJoinState 엔티티를 추가로 생성하려고 하면 예외를 반환한다.")
    public void Should_Throw_Exception_When_CreateEntity_If_UserId_And_ClubId_Is_Duplicated() {
        //given
        User user = mockUser1;
        Club club = mockClub1;

        //when
        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();

        clubJoinStateService.register(user.getId(), club.getId(), joinStateCodeMaster);

        //then
        assertThrows(RuntimeException.class, () -> clubJoinStateService.register(user.getId(), club.getId(), joinStateCodeMANAGER));
    }
    // ========== register ==========

    // ========== getClubJoinState ==========
    @Test
    @DisplayName("pk를 가지고 clubJoinState를 조회할 수 있다.")
    public void Should_Return_ClubJoinState_When_ClubJoinStateId_Is_Valid() {
        //given
        User user = mockUser1;
        Club club = mockClub1;
        int initialJoinState = JoinState.MASTER.getCode();

        ClubJoinState registeredState = clubJoinStateService.register(user.getId(), club.getId(), initialJoinState);

        //when
        ClubJoinState clubJoinState = clubJoinStateService.getClubJoinState(registeredState.getId());

        //then
        assertThat(clubJoinState).isNotNull();
        assertThat(clubJoinState).isSameAs(registeredState);
    }

    @Test
    @DisplayName("유효하지 않은 pk를 가지고 clubJoinState를 조회하면 예외를 던진다.")
    public void Should_Throw_Exception_When_ClubJoinStateId_Is_Invalid() {
        //given
        User user = mockUser1;
        Club club = mockClub1;
        int initialJoinState = JoinState.MASTER.getCode();

        clubJoinStateService.register(user.getId(), club.getId(), initialJoinState);

        //when
        Long invalidId = 9999L;

        //then
        assertThrows(EntityNotFoundException.class, () -> clubJoinStateService.getClubJoinState(invalidId));
    }

    @Test
    @DisplayName("userId와 clubId를 가지고 clubJoinState를 조회할 수 있다.")
    public void Should_Return_ClubJoinState_When_UserId_And_ClubId_Is_Valid() {
        //given
        User user = mockUser1;
        Club club = mockClub1;
        int initialJoinState = JoinState.MASTER.getCode();

        ClubJoinState registeredState = clubJoinStateService.register(user.getId(), club.getId(), initialJoinState);

        //when
        ClubJoinState clubJoinState = clubJoinStateService.getClubJoinState(user.getId(), club.getId());

        //then
        assertThat(clubJoinState).isNotNull();
        assertThat(clubJoinState).isSameAs(registeredState);
    }

    @Test
    @DisplayName("유효하지 않은 userId나 clubId를 가지고 clubJoinState를 조회하면 예외를 던진다.")
    public void Should_Throw_Exception_When_UserId_And_ClubId_Is_Invalid() {
        //given
        User user = mockUser1;
        Club club = mockClub1;
        int initialJoinState = JoinState.MASTER.getCode();

        clubJoinStateService.register(user.getId(), club.getId(), initialJoinState);

        //when
        User invalidUser = mockUser2;
        Club invalidClub = mockClub2;

        //then
        assertThrows(EntityNotFoundException.class, () -> clubJoinStateService.getClubJoinState(invalidUser.getId(), club.getId()));
        assertThrows(EntityNotFoundException.class, () -> clubJoinStateService.getClubJoinState(user.getId(), invalidClub.getId()));
    }
    // ========== getClubJoinState ==========

    // ========== update ==========
    @Test
    @DisplayName("사용자의 클럽 가입 상태를 변경할 수 있다.")
    public void Should_Update_ClubJoinState_When_JoinState_Is_Valid() {
        //given
        User user = mockUser1;
        Club club = mockClub1;
        int initialJoinState = JoinState.MASTER.getCode();

        clubJoinStateService.register(user.getId(), club.getId(), initialJoinState);
        JoinState initialState = clubJoinStateService.getClubJoinState(user.getId(), club.getId()).getJoinState();

        //when
        int nxtJoinState = JoinState.MANAGER.getCode();
        clubJoinStateService.update(user.getId(), club.getId(), nxtJoinState);
        JoinState nxtState = clubJoinStateService.getClubJoinState(user.getId(), club.getId()).getJoinState();

        //then
        assertThat(initialState).isEqualTo(JoinState.MASTER);
        assertThat(nxtState).isEqualTo(JoinState.MANAGER);
    }

    @Test
    @DisplayName("유효하지 않은 JoinState로 가입 상태 변경 시 예외를 던진다.")
    public void Should_Throw_Exception_When_Updating_JoinState_Is_Invalid() {
        //given
        User user = mockUser1;
        Club club = mockClub1;
        int initialJoinState = JoinState.MASTER.getCode();

        clubJoinStateService.register(user.getId(), club.getId(), initialJoinState);

        //when
        int invalidJoinStateCode = 5;
        int nxtJoinState = invalidJoinStateCode;

        //then
        assertThrows(IllegalArgumentException.class, () -> clubJoinStateService.update(user.getId(), club.getId(), nxtJoinState));
    }
    // ========== update ==========

    // ========== delete ==========
    @Test
    @DisplayName("사용자의 클럽 가입 상태를 삭제한다.(엔티티를 지우지 않고 isUsed 필드만 false로 바꾼다)")
    public void Should_Changed_isUsed_Field_to_False_When_UserId_And_ClubId_Is_Valid() {
        //given
        User user = mockUser1;
        Club club = mockClub1;
        int testJoinStateCode = JoinState.MASTER.getCode();

        clubJoinStateService.register(user.getId(), club.getId(), testJoinStateCode);

        boolean isUsedBeforeDelete = clubJoinStateService.getClubJoinState(user.getId(), club.getId()).isUsed();

        //when
        clubJoinStateService.delete(user.getId(), club.getId());
        boolean isUsedAfterDelete = clubJoinStateService.getClubJoinState(user.getId(), club.getId()).isUsed();

        //then
        assertThat(isUsedBeforeDelete).isEqualTo(true);
        assertThat(isUsedAfterDelete).isEqualTo(false);
    }
    // ========== delete ==========

    // ========== isClubMaster ==========
    @Test
    @DisplayName("사용자의 joinState 권한이 MASTER인지 판단한다.")
    public void Should_Return_True_When_UserJoinState_Is_MASTER() {
        //given
        User user1 = userService.getUser(mockUser1.getId());

        Club club1 = clubService.getClub(mockClub1.getId());
        Club club2 = clubService.getClub(mockClub2.getId());
        Club club3 = clubService.getClub(mockClub3.getId());
        Club club4 = clubService.getClub(mockClub4.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user1.getId(), club2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user1.getId(), club3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(clubJoinStateService.isClubMaster(user1.getId(), club1.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isClubMaster(user1.getId(), club2.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isClubMaster(user1.getId(), club3.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isClubMaster(user1.getId(), club4.getId())).isEqualTo(false);
    }
    // ========== isClubMaster ==========

    // ========== isClubManager ==========
    @Test
    @DisplayName("사용자의 joinState 권한이 MANAGER인지 판단한다.")
    public void Should_Return_True_When_UserJoinState_Is_MANAGER() {
        //given
        User user1 = userService.getUser(mockUser1.getId());

        Club club1 = clubService.getClub(mockClub1.getId());
        Club club2 = clubService.getClub(mockClub2.getId());
        Club club3 = clubService.getClub(mockClub3.getId());
        Club club4 = clubService.getClub(mockClub4.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user1.getId(), club2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user1.getId(), club3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(clubJoinStateService.isClubManager(user1.getId(), club1.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isClubManager(user1.getId(), club2.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isClubManager(user1.getId(), club3.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isClubManager(user1.getId(), club4.getId())).isEqualTo(false);
    }
    // ========== isClubManager ==========

    // ========== isClubMember ==========
    @Test
    @DisplayName("사용자의 joinState 권한이 MEMBER인지 판단한다.")
    public void Should_Return_True_When_UserJoinState_Is_MEMBER() {
        //given
        User user1 = userService.getUser(mockUser1.getId());

        Club club1 = clubService.getClub(mockClub1.getId());
        Club club2 = clubService.getClub(mockClub2.getId());
        Club club3 = clubService.getClub(mockClub3.getId());
        Club club4 = clubService.getClub(mockClub4.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user1.getId(), club2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user1.getId(), club3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(clubJoinStateService.isClubMember(user1.getId(), club1.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isClubMember(user1.getId(), club2.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isClubMember(user1.getId(), club3.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isClubMember(user1.getId(), club4.getId())).isEqualTo(false);
    }
    // ========== isClubMember ==========

    // ========== isJoined ==========
    @Test
    @DisplayName("사용자의 동아리 가입여부를 판단한다.")
    public void Should_Return_True_When_UserJoinState_Is_MASTER_Or_MANAGER_Or_MEMBER() {
        //given
        User user1 = userService.getUser(mockUser1.getId());

        Club club1 = clubService.getClub(mockClub1.getId());
        Club club2 = clubService.getClub(mockClub2.getId());
        Club club3 = clubService.getClub(mockClub3.getId());
        Club club4 = clubService.getClub(mockClub4.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user1.getId(), club2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user1.getId(), club3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(clubJoinStateService.isJoined(user1.getId(), club1.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isJoined(user1.getId(), club2.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isJoined(user1.getId(), club3.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isJoined(user1.getId(), club4.getId())).isEqualTo(false);
    }
    // ========== isJoined ==========

    // ========== existed ==========
    @Test
    @DisplayName("사용자의 동아리 가입정보 DB row의 유효성(사용 여부)을 판단한다.")
    public void Should_Return_True_When_ClubJoinState_isUsed_Field_Is_True() {
        //given
        User user = mockUser1;
        User user2 = mockUser2;
        Club club = mockClub1;
        int testJoinStateCode = JoinState.MASTER.getCode();

        clubJoinStateService.register(user.getId(), club.getId(), testJoinStateCode);
        clubJoinStateService.register(user2.getId(), club.getId(), testJoinStateCode);

        //when
        clubJoinStateService.delete(user2.getId(), club.getId());

        //then
        assertThat(clubJoinStateService.existed(user.getId(), club.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.existed(user2.getId(), club.getId())).isEqualTo(false);
    }
    // ========== existed ==========

    // ========== isLeaveClub ==========
    @Test
    @DisplayName("사용자의 동아리 탈퇴 여부를 판단한다.")
    public void Should_Return_True_When_ClubJoinState_isUsed_Field_Is_False() {
        //given
        User user = mockUser1;
        User user2 = mockUser2;
        Club club = mockClub1;
        int testJoinStateCode = JoinState.MASTER.getCode();

        clubJoinStateService.register(user.getId(), club.getId(), testJoinStateCode);

        //when
        //then
        assertThat(clubJoinStateService.isLeaveClub(user.getId(), club.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isLeaveClub(user2.getId(), club.getId())).isEqualTo(false);

        clubJoinStateService.delete(user.getId(), club.getId());

        assertThat(clubJoinStateService.isLeaveClub(user.getId(), club.getId())).isEqualTo(true);
    }
    // ========== isLeaveClub ==========

    // ========== isRegistered ==========
    @Test
    @DisplayName("사용자의 동아리 등록 이력 여부를 판단한다.")
    public void Should_Return_True_When_User_Registered_Club_Before() {
        //given
        User user = mockUser1;
        User user2 = mockUser2;
        Club club = mockClub1;
        int testJoinStateCode = JoinState.MASTER.getCode();

        clubJoinStateService.register(user.getId(), club.getId(), testJoinStateCode);

        //when
        //then
        assertThat(clubJoinStateService.isRegistered(user.getId(), club.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isRegistered(user2.getId(), club.getId())).isEqualTo(false);

        clubJoinStateService.delete(user.getId(), club.getId());

        assertThat(clubJoinStateService.isRegistered(user.getId(), club.getId())).isEqualTo(true);
    }
    // ========== isRegistered ==========

    // ========== hasManagerRole ==========
    @Test
    @DisplayName("사용자의 가입상태가 Master 또는 Manager이면 true를 반환한다.")
    public void Should_Return_True_When_UserJoinState_Is_MASTER_Or_MANAGER() {
        //given
        User user1 = userService.getUser(mockUser1.getId());

        Club club1 = clubService.getClub(mockClub1.getId());
        Club club2 = clubService.getClub(mockClub2.getId());
        Club club3 = clubService.getClub(mockClub3.getId());
        Club club4 = clubService.getClub(mockClub4.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user1.getId(), club2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user1.getId(), club3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(clubJoinStateService.hasManagerRole(user1.getId(), club1.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.hasManagerRole(user1.getId(), club2.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.hasManagerRole(user1.getId(), club3.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.hasManagerRole(user1.getId(), club4.getId())).isEqualTo(false);
    }
    // ========== hasManagerRole ==========

    // ========== hasMemberRole ==========
    @Test
    @DisplayName("사용자의 가입상태가 Master 또는 Manager 또는 Member이면 true를 반환한다.")
    public void Should_Return_True_When_UserJoinState_Is_Not_미가입() {
        //given
        User user1 = userService.getUser(mockUser1.getId());

        Club club1 = clubService.getClub(mockClub1.getId());
        Club club2 = clubService.getClub(mockClub2.getId());
        Club club3 = clubService.getClub(mockClub3.getId());
        Club club4 = clubService.getClub(mockClub4.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user1.getId(), club2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user1.getId(), club3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(clubJoinStateService.hasMemberRole(user1.getId(), club1.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.hasMemberRole(user1.getId(), club2.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.hasMemberRole(user1.getId(), club3.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.hasMemberRole(user1.getId(), club4.getId())).isEqualTo(false);
    }
    // ========== hasMemberRole ==========
    /**
     * End Common Region
     */



    /**
     * Start Club Region (for Club API) Test
     */
    // ========== getMasters ==========
    @Test
    @DisplayName("가입상태가 Master인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_MASTER() {
        //given
        User user1 = userService.getUser(mockUser1.getId());
        User user2 = userService.getUser(mockUser2.getId());
        User user3 = userService.getUser(mockUser3.getId());
        User user4 = userService.getUser(mockUser4.getId());
        User user5 = userService.getUser(mockUser5.getId());

        Club club = clubService.getClub(mockClub1.getId());
        int joinStateMaster = JoinState.MASTER.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club.getId(), joinStateMaster);
        clubJoinStateService.register(user2.getId(), club.getId(), joinStateMaster);
        clubJoinStateService.register(user3.getId(), club.getId(), joinStateMaster);
        clubJoinStateService.register(user4.getId(), club.getId(), joinStateMaster);
        clubJoinStateService.register(user5.getId(), club.getId(), joinStateMaster);

        List<ClubJoinState> masters = clubJoinStateService.getMasters(club.getId(), testPageRequest);

        //then
        assertThat(masters.size()).isEqualTo(5);
    }
    // ========== getMasters ==========

    // ========== getManagers ==========
    @Test
    @DisplayName("가입상태가 Manager인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_MANAGER() {
        //given
        User user1 = userService.getUser(mockUser1.getId());
        User user2 = userService.getUser(mockUser2.getId());
        User user3 = userService.getUser(mockUser3.getId());
        User user4 = userService.getUser(mockUser4.getId());
        User user5 = userService.getUser(mockUser5.getId());

        Club club = clubService.getClub(mockClub1.getId());
        int joinStateManager = JoinState.MANAGER.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club.getId(), joinStateManager);
        clubJoinStateService.register(user2.getId(), club.getId(), joinStateManager);
        clubJoinStateService.register(user3.getId(), club.getId(), joinStateManager);
        clubJoinStateService.register(user4.getId(), club.getId(), joinStateManager);
        clubJoinStateService.register(user5.getId(), club.getId(), joinStateManager);

        List<ClubJoinState> managers = clubJoinStateService.getManagers(club.getId(), testPageRequest);

        //then
        assertThat(managers.size()).isEqualTo(5);
    }
    // ========== getManagers ==========

    // ========== getMembers ==========
    @Test
    @DisplayName("가입상태가 Member인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_MEMBER() {
        //given
        User user1 = userService.getUser(mockUser1.getId());
        User user2 = userService.getUser(mockUser2.getId());
        User user3 = userService.getUser(mockUser3.getId());
        User user4 = userService.getUser(mockUser4.getId());
        User user5 = userService.getUser(mockUser5.getId());

        Club club = clubService.getClub(mockClub1.getId());
        int joinStateMember = JoinState.MEMBER.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club.getId(), joinStateMember);
        clubJoinStateService.register(user2.getId(), club.getId(), joinStateMember);
        clubJoinStateService.register(user3.getId(), club.getId(), joinStateMember);
        clubJoinStateService.register(user4.getId(), club.getId(), joinStateMember);
        clubJoinStateService.register(user5.getId(), club.getId(), joinStateMember);

        List<ClubJoinState> members = clubJoinStateService.getMembers(club.getId(), testPageRequest);

        //then
        assertThat(members.size()).isEqualTo(5);
    }
    // ========== getMembers ==========

    // ========== getAllMembers ==========
    @Test
    @DisplayName("가입상태가 Master, Manager, Member인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_MASTER_Or_MANAGER_Or_MEMBER() {
        //given
        User user1 = userService.getUser(mockUser1.getId());
        User user2 = userService.getUser(mockUser2.getId());
        User user3 = userService.getUser(mockUser3.getId());
        User user4 = userService.getUser(mockUser4.getId());
        User user5 = userService.getUser(mockUser5.getId());

        Club club = clubService.getClub(mockClub1.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user2.getId(), club.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user3.getId(), club.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user4.getId(), club.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(user5.getId(), club.getId(), joinStateCodeNotJoined);

        List<ClubJoinState> members = clubJoinStateService.getAllMembers(club.getId(), testPageRequest);

        //then
        assertThat(members.size()).isEqualTo(3);
    }
    // ========== getAllMembers ==========

    // ========== getAppliedMembers ==========
    @Test
    @DisplayName("가입상태가 NOT_JOINED인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_미가입() {
        //given
        User user1 = userService.getUser(mockUser1.getId());
        User user2 = userService.getUser(mockUser2.getId());
        User user3 = userService.getUser(mockUser3.getId());
        User user4 = userService.getUser(mockUser4.getId());
        User user5 = userService.getUser(mockUser5.getId());

        Club club = clubService.getClub(mockClub1.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(user2.getId(), club.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(user3.getId(), club.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user4.getId(), club.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user5.getId(), club.getId(), joinStateCodeMEMBER);

        List<ClubJoinState> members = clubJoinStateService.getAppliedMembers(club.getId(), testPageRequest);

        //then
        assertThat(members.size()).isEqualTo(2);
    }
    // ========== getAppliedMembers ==========

    // ========== getManagerRoleMembers ==========
    @Test
    @DisplayName("가입상태가 Master인, Manager인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_MASTER_Or_MANAGER() {
        //given
        User user1 = userService.getUser(mockUser1.getId());
        User user2 = userService.getUser(mockUser2.getId());
        User user3 = userService.getUser(mockUser3.getId());
        User user4 = userService.getUser(mockUser4.getId());
        User user5 = userService.getUser(mockUser5.getId());
        User user6 = userService.getUser(mockUser6.getId());
        User user7 = userService.getUser(mockUser7.getId());
        User user8 = userService.getUser(mockUser8.getId());
        User user9 = userService.getUser(mockUser9.getId());
        User user10 = userService.getUser(mockUser10.getId());

        Club club = clubService.getClub(mockClub1.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user2.getId(), club.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user3.getId(), club.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user4.getId(), club.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user5.getId(), club.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user6.getId(), club.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user7.getId(), club.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(user8.getId(), club.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(user9.getId(), club.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(user10.getId(), club.getId(), joinStateCodeNotJoined);

        List<ClubJoinState> members = clubJoinStateService.getManagerRoleMembers(club.getId(), testPageRequest);

        //then
        assertThat(members.size()).isEqualTo(4);
    }
    // ========== getManagerRoleMembers ==========

    // ========== getMemberRoleMembers ==========
    @Test
    @DisplayName("가입상태가 Master, Manager, Member인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_MEMBER_Role() {
        //given
        User user1 = userService.getUser(mockUser1.getId());
        User user2 = userService.getUser(mockUser2.getId());
        User user3 = userService.getUser(mockUser3.getId());
        User user4 = userService.getUser(mockUser4.getId());
        User user5 = userService.getUser(mockUser5.getId());
        User user6 = userService.getUser(mockUser6.getId());
        User user7 = userService.getUser(mockUser7.getId());
        User user8 = userService.getUser(mockUser8.getId());
        User user9 = userService.getUser(mockUser9.getId());
        User user10 = userService.getUser(mockUser10.getId());

        Club club = clubService.getClub(mockClub1.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user2.getId(), club.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user3.getId(), club.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user4.getId(), club.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user5.getId(), club.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user6.getId(), club.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user7.getId(), club.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(user8.getId(), club.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(user9.getId(), club.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(user10.getId(), club.getId(), joinStateCodeNotJoined);

        List<ClubJoinState> members = clubJoinStateService.getMemberRoleMembers(club.getId(), testPageRequest);

        //then
        assertThat(members.size()).isEqualTo(6);
    }
    // ========== getMemberRoleMembers ==========
    /**
     * End Club Region (for Club API) Test
     */



    /**
     * Start User Region (for User API) Test
     */
    // ========== getClubJoinStatesByUser ==========
    @Test
    @DisplayName("사용자의 모든 동아리 가입 상태를 반환한다.")
    public void Should_Return_UserClubJoinStates() {
        //given
        User user1 = userService.getUser(mockUser1.getId());

        Club club1 = clubService.getClub(mockClub1.getId());
        Club club2 = clubService.getClub(mockClub2.getId());
        Club club3 = clubService.getClub(mockClub3.getId());
        Club club4 = clubService.getClub(mockClub4.getId());
        Club club5 = clubService.getClub(mockClub5.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user1.getId(), club2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user1.getId(), club3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club5.getId(), joinStateCodeNotJoined);

        List<ClubJoinState> userJoinStates = clubJoinStateService.getClubJoinStatesByUser(user1.getId(), testPageRequest);

        //then
        assertThat(userJoinStates.size()).isEqualTo(5);
        long user1MasterCnt = userJoinStates.stream().filter(state -> state.getJoinState().equals(JoinState.MASTER)).count();
        long user1ManagerCnt = userJoinStates.stream().filter(state -> state.getJoinState().equals(JoinState.MANAGER)).count();
        long user1MemberCnt = userJoinStates.stream().filter(state -> state.getJoinState().equals(JoinState.MEMBER)).count();
        long user1NotJoinedCnt = userJoinStates.stream().filter(state -> state.getJoinState().equals(JoinState.NOT_JOINED)).count();

        assertThat(user1MasterCnt).isEqualTo(1);
        assertThat(user1ManagerCnt).isEqualTo(1);
        assertThat(user1MemberCnt).isEqualTo(2);
        assertThat(user1NotJoinedCnt).isEqualTo(1);
    }
    // ========== getClubJoinStatesByUser ==========

    // ========== getClubJoinStatesByUser ==========
    @Test
    @DisplayName("사용자의 가입 상태가 joinState인 모든 동아리 가입 상태를 반환한다.")
    public void Should_Return_ClubJoinStates_When_UserJoinState_Is_Same() {
        //given
        User user1 = userService.getUser(mockUser1.getId());

        Club club1 = clubService.getClub(mockClub1.getId());
        Club club2 = clubService.getClub(mockClub2.getId());
        Club club3 = clubService.getClub(mockClub3.getId());
        Club club4 = clubService.getClub(mockClub4.getId());
        Club club5 = clubService.getClub(mockClub5.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user1.getId(), club2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user1.getId(), club3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club5.getId(), joinStateCodeNotJoined);

        List<ClubJoinState> joinStatesMaster = clubJoinStateService.getClubJoinStatesByUser(user1.getId(), joinStateCodeMaster, testPageRequest);
        List<ClubJoinState> joinStatesManager = clubJoinStateService.getClubJoinStatesByUser(user1.getId(), joinStateCodeMANAGER, testPageRequest);
        List<ClubJoinState> joinStatesMember = clubJoinStateService.getClubJoinStatesByUser(user1.getId(), joinStateCodeMEMBER, testPageRequest);
        List<ClubJoinState> joinStatesNotJoined = clubJoinStateService.getClubJoinStatesByUser(user1.getId(), joinStateCodeNotJoined, testPageRequest);

        //then
        assertThat(joinStatesMaster.size()).isEqualTo(1);
        assertThat(joinStatesManager.size()).isEqualTo(1);
        assertThat(joinStatesMember.size()).isEqualTo(2);
        assertThat(joinStatesNotJoined.size()).isEqualTo(1);
    }
    // ========== getClubJoinStatesByUser ==========

    // ========== getClubJoinStatesByUser ==========
    @Test
    @DisplayName("올바르지 않은 가입 상태를 조회 시 예외를 반환한다.")
    public void Should_Throw_Exception_When_getClubJoinStatesByUser_UserJoinState_Is_Invalid() {
        //given
        User user1 = userService.getUser(mockUser1.getId());

        Club club1 = clubService.getClub(mockClub1.getId());
        Club club2 = clubService.getClub(mockClub2.getId());
        Club club3 = clubService.getClub(mockClub3.getId());
        Club club4 = clubService.getClub(mockClub4.getId());
        Club club5 = clubService.getClub(mockClub5.getId());

        int invalidJoinStateCode = 5;
        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user1.getId(), club2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user1.getId(), club3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club5.getId(), joinStateCodeNotJoined);

        //then
        assertThrows(IllegalArgumentException.class,
                () -> clubJoinStateService.getClubJoinStatesByUser(user1.getId(), invalidJoinStateCode, testPageRequest));
    }
    // ========== getClubJoinStatesByUser ==========

    // ========== getClubJoinStatesByUserHasRole ==========
    @Test
    @DisplayName("사용자가 특정 권한 이상을 가진 모든 동아리 가입 상태를 반환한다.")
    public void Should_Return_ClubJoinStates_When_UserJoinState_Is_Contained() {
        //given
        User user1 = userService.getUser(mockUser1.getId());

        Club club1 = clubService.getClub(mockClub1.getId());
        Club club2 = clubService.getClub(mockClub2.getId());
        Club club3 = clubService.getClub(mockClub3.getId());
        Club club4 = clubService.getClub(mockClub4.getId());
        Club club5 = clubService.getClub(mockClub5.getId());

        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user1.getId(), club2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user1.getId(), club3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club5.getId(), joinStateCodeNotJoined);

        List<ClubJoinState> hasRolesMaster = clubJoinStateService.getClubJoinStatesByUserHasRole(user1.getId(), joinStateCodeMaster, testPageRequest);
        List<ClubJoinState> hasRolesManager = clubJoinStateService.getClubJoinStatesByUserHasRole(user1.getId(), joinStateCodeMANAGER, testPageRequest);
        List<ClubJoinState> hasRolesMember = clubJoinStateService.getClubJoinStatesByUserHasRole(user1.getId(), joinStateCodeMEMBER, testPageRequest);
        List<ClubJoinState> hasRolesNotJoined = clubJoinStateService.getClubJoinStatesByUserHasRole(user1.getId(), joinStateCodeNotJoined, testPageRequest);

        //then
        assertThat(hasRolesMaster.size()).isEqualTo(1);
        assertThat(hasRolesManager.size()).isEqualTo(2);
        assertThat(hasRolesMember.size()).isEqualTo(4);
        assertThat(hasRolesNotJoined.size()).isEqualTo(5);
    }
    // ========== getClubJoinStatesByUserHasRole ==========

    // ========== getClubJoinStatesByUserHasRole ==========
    @Test
    @DisplayName("올바르지 않은 가입 상태를 조회 시 예외를 반환한다.")
    public void Should_Throw_Exception_When_getClubJoinStatesByUserHasRole_UserJoinState_Is_Invalid() {
        //given
        User user1 = userService.getUser(mockUser1.getId());

        Club club1 = clubService.getClub(mockClub1.getId());
        Club club2 = clubService.getClub(mockClub2.getId());
        Club club3 = clubService.getClub(mockClub3.getId());
        Club club4 = clubService.getClub(mockClub4.getId());
        Club club5 = clubService.getClub(mockClub5.getId());

        int invalidJoinStateCode = 5;
        int joinStateCodeMaster = JoinState.MASTER.getCode();
        int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
        int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
        int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

        //when
        clubJoinStateService.register(user1.getId(), club1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(user1.getId(), club2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(user1.getId(), club3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(user1.getId(), club5.getId(), joinStateCodeNotJoined);

        //then
        assertThrows(IllegalArgumentException.class,
                () -> clubJoinStateService.getClubJoinStatesByUserHasRole(user1.getId(), invalidJoinStateCode, testPageRequest));
    }
    // ========== getClubJoinStatesByUserHasRole ==========
    /**
     * End User Region (for User API) Test
     */
}