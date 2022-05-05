package com.example.club_project.service.clubjoinstate;

import com.example.club_project.controller.club.ClubDTO;
import com.example.club_project.domain.*;
import com.example.club_project.exception.custom.NotFoundException;
import com.example.club_project.repository.ClubJoinStateRepository;
import com.example.club_project.repository.ClubRepository;
import com.example.club_project.repository.UserRepository;
import com.example.club_project.service.category.CategoryService;
import com.example.club_project.service.club.ClubService;
import com.example.club_project.service.user.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ClubJoinStateServiceTest {

    @Autowired
    private ClubJoinStateService clubJoinStateService;

    /**
     * Mock Category
     */
    private static Category mockCategory1, mockCategory2, mockCategory3, mockCategory4, mockCategory5;

    /**
     * Mock User
     */
    private static User mockUser1, mockUser2, mockUser3, mockUser4, mockUser5, mockUser6, mockUser7, mockUser8, mockUser9, mockUser10;

    /**
     * Mock Club
     */
    private static Club mockClub1, mockClub2, mockClub3, mockClub4, mockClub5;

    /**
     * Data for ClubJoinState
     */
    private int joinStateCodeMaster = JoinState.MASTER.getCode();
    private int joinStateCodeMANAGER = JoinState.MANAGER.getCode();
    private int joinStateCodeMEMBER = JoinState.MEMBER.getCode();
    private int joinStateCodeNotJoined = JoinState.NOT_JOINED.getCode();

    private int testPagingOffsetSize = 0;
    private int testPagingLimitSize = 50;
    private PageRequest testPageRequest = PageRequest.of(testPagingOffsetSize, testPagingLimitSize);

    @Autowired
    private ClubJoinStateRepository clubJoinStateRepository;    // deleteAll 호출 용도
    @Autowired
    private UserRepository userRepository;  // deleteAll 호출 용도
    @Autowired
    private ClubRepository clubRepository;  // deleteAll 호출 용도


    @BeforeAll
    public void setup(@Autowired CategoryService categoryService,
                      @Autowired ClubService clubService,
                      @Autowired UserService userService) {
        //init user
        User user1 = User.builder().email("test1@gmail.com").name("test").password("Test1234!@").nickname("test").university("test").introduction("test").build();
        User user2 = User.builder().email("test2@gmail.com").name("test").password("Test1234!@").nickname("test").university("test").introduction("test").build();
        User user3 = User.builder().email("test3@gmail.com").name("test").password("Test1234!@").nickname("test").university("test").introduction("test").build();
        User user4 = User.builder().email("test4@gmail.com").name("test").password("Test1234!@").nickname("test").university("test").introduction("test").build();
        User user5 = User.builder().email("test5@gmail.com").name("test").password("Test1234!@").nickname("test").university("test").introduction("test").build();
        User user6 = User.builder().email("test6@gmail.com").name("test").password("Test1234!@").nickname("test").university("test").introduction("test").build();
        User user7 = User.builder().email("test7@gmail.com").name("test").password("Test1234!@").nickname("test").university("test").introduction("test").build();
        User user8 = User.builder().email("test8@gmail.com").name("test").password("Test1234!@").nickname("test").university("test").introduction("test").build();
        User user9 = User.builder().email("test9@gmail.com").name("test").password("Test1234!@").nickname("test").university("test").introduction("test").build();
        User user10 = User.builder().email("test10@gmail.com").name("test").password("Test1234!@").nickname("test").university("test").introduction("test").build();

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

        mockCategory1 = categoryService.register(category1.getName(), category1.getDescription());
        mockCategory2 = categoryService.register(category2.getName(), category2.getDescription());
        mockCategory3 = categoryService.register(category3.getName(), category3.getDescription());
        mockCategory4 = categoryService.register(category4.getName(), category4.getDescription());
        mockCategory5 = categoryService.register(category5.getName(), category5.getDescription());

        //init Club
        Club club1 = Club.builder().name("club1").address("test").university("test").description("test").category(mockCategory1).build();
        Club club2 = Club.builder().name("club2").address("test").university("test").description("test").category(mockCategory2).build();
        Club club3 = Club.builder().name("club3").address("test").university("test").description("test").category(mockCategory3).build();
        Club club4 = Club.builder().name("club4").address("test").university("test").description("test").category(mockCategory4).build();
        Club club5 = Club.builder().name("club5").address("test").university("test").description("test").category(mockCategory5).build();

        mockClub1 = clubService.register(club1.getName(), club1.getAddress(), club1.getUniversity(), club1.getDescription(), club1.getCategory().getId());
        mockClub2 = clubService.register(club2.getName(), club2.getAddress(), club2.getUniversity(), club2.getDescription(), club2.getCategory().getId());
        mockClub3 = clubService.register(club3.getName(), club3.getAddress(), club3.getUniversity(), club3.getDescription(), club3.getCategory().getId());
        mockClub4 = clubService.register(club4.getName(), club4.getAddress(), club4.getUniversity(), club4.getDescription(), club4.getCategory().getId());
        mockClub5 = clubService.register(club5.getName(), club5.getAddress(), club5.getUniversity(), club5.getDescription(), club5.getCategory().getId());
    }

    /**
     * Start DTO Region
     */
    @Test
    @DisplayName("ClubId가 주어지면 해당 Club 정보와 가입한 마스터, 운영진들의 정보를 ClubDTO.DetailResponse 객체로 반환한다.")
    public void Should_Return_ClubDTO_DetailResponse_When_ClubId_Provided() {
        //given
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser3.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser4.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser5.getId(), mockClub1.getId(), joinStateCodeMEMBER);

        //when
        ClubDTO.DetailResponse clubDetailDto = clubJoinStateService.getClubDetailDto(mockClub1.getId());

        //then
        assertThat(clubDetailDto.getId()).isEqualTo(mockClub1.getId());
        assertThat(clubDetailDto.getName()).isEqualTo(mockClub1.getName());
        assertThat(clubDetailDto.getAddress()).isEqualTo(mockClub1.getAddress());
        assertThat(clubDetailDto.getUniversity()).isEqualTo(mockClub1.getUniversity());
        assertThat(clubDetailDto.getDescription()).isEqualTo(mockClub1.getDescription());
        assertThat(clubDetailDto.getCategory()).isEqualTo(mockClub1.getCategory().getName());
        assertThat(clubDetailDto.getMembers().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("University 이름이 주어지면 해당 대학교에 속해있는 동아리 정보를 List<ClubDTO> 객체로 반환한다.")
    public void Should_Return_ClubDTOs_When_UniversityName_Provided() {
        //given
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser3.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser4.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser5.getId(), mockClub1.getId(), joinStateCodeMEMBER);

        clubJoinStateService.register(mockUser6.getId(), mockClub2.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser7.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser8.getId(), mockClub4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser9.getId(), mockClub5.getId(), joinStateCodeMEMBER);

        //when
        String university = "test";
        List<ClubDTO> clubDtos = clubJoinStateService.getClubDtos(university, testPageRequest);

        //then
        assertThat(clubDtos.size()).isEqualTo(5);

        for (ClubDTO clubDto : clubDtos) {
            assertThat(clubDto.getClubMembers()).isNotEqualTo(0);
        }
    }

    @Test
    @DisplayName("ClubName 검색어와 University 이름이 주어지면, " +
            "검색어를 포함하고 해당 대학교에 속해있는 동아리 정보를 List<ClubDTO> 객체로 반환한다.")
    public void Should_Return_ClubDTOs_When_ClubNameSearchKeyword_And_UniversityName_Provided() {
        //given
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser3.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser4.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser5.getId(), mockClub1.getId(), joinStateCodeMEMBER);

        clubJoinStateService.register(mockUser6.getId(), mockClub2.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser7.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser8.getId(), mockClub4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser9.getId(), mockClub5.getId(), joinStateCodeMEMBER);

        //when
        String searchKeyword = "club";
        String university = "test";
        List<ClubDTO> clubDtos = clubJoinStateService.getClubDtos(searchKeyword, university, testPageRequest);

        //then
        assertThat(clubDtos.size()).isEqualTo(5);

        for (ClubDTO clubDto : clubDtos) {
            assertThat(clubDto.getClubMembers()).isNotEqualTo(0);
        }
    }

    @Test
    @DisplayName("Category Id 리스트와 University 이름이 주어지면, " +
            "해당 카테고리 Id에 속하고 해당 대학교에 속해있는 동아리 정보를 List<ClubDTO> 객체로 반환한다.")
    public void Should_Return_ClubDTOs_When_CategoryIdList_And_UniversityName_Provided() {
        //given
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser3.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser4.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser5.getId(), mockClub1.getId(), joinStateCodeMEMBER);

        clubJoinStateService.register(mockUser6.getId(), mockClub2.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser7.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser8.getId(), mockClub4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser9.getId(), mockClub5.getId(), joinStateCodeMEMBER);

        //when
        List<Long> searchCategories = new ArrayList<>(Arrays.asList(mockCategory1.getId(), mockCategory2.getId(), mockCategory3.getId()));
        String university = "test";
        List<ClubDTO> clubDtos = clubJoinStateService.getClubDtos(searchCategories, university, testPageRequest);

        //then
        assertThat(clubDtos.size()).isEqualTo(3);

        for (ClubDTO clubDto : clubDtos) {
            assertThat(clubDto.getClubMembers()).isNotEqualTo(0);
        }
    }

    @Test
    @DisplayName("Category Id 리스트와 University 이름과 ClubName 검색어가 주어지면, " +
            "해당 카테고리 Id에 속하고 검색어를 포함하며 해당 대학교에 속해있는 동아리 정보를 List<ClubDTO> 객체로 반환한다.")
    public void Should_Return_ClubDTOs_When_CategoryIdList_And_UniversityName_And_ClubNameSearchKeyword_Provided() {
        //given
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser3.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser4.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser5.getId(), mockClub1.getId(), joinStateCodeMEMBER);

        clubJoinStateService.register(mockUser6.getId(), mockClub2.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser7.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser8.getId(), mockClub4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser9.getId(), mockClub5.getId(), joinStateCodeMEMBER);

        //when
        List<Long> searchCategories = new ArrayList<>(Arrays.asList(mockCategory1.getId(), mockCategory2.getId()));
        String searchKeyword = "club";
        String university = "test";
        List<ClubDTO> clubDtos = clubJoinStateService.getClubDtos(searchCategories, university, searchKeyword, testPageRequest);

        //then
        assertThat(clubDtos.size()).isEqualTo(2);

        for (ClubDTO clubDto : clubDtos) {
            assertThat(clubDto.getClubMembers()).isNotEqualTo(0);
        }
    }
    /**
     * End DTO Region
     */



    /**
     * Start Common Region
     */
    // ========== register ==========
    @Test
    @DisplayName("User 엔티티, Club 엔티티, joinState 코드를 사용하여 ClubJoinState 엔티티를 생성할 수 있다.")
    public void Should_Return_Entity_When_User_And_Club_And_JoinState_Is_Valid() {
        //given
        //when
        ClubJoinState registeredClubJoinState1 = clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        ClubJoinState registeredClubJoinState2 = clubJoinStateService.register(mockUser2.getId(), mockClub2.getId(), joinStateCodeMANAGER);
        ClubJoinState registeredClubJoinState3 = clubJoinStateService.register(mockUser3.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        ClubJoinState registeredClubJoinState4 = clubJoinStateService.register(mockUser4.getId(), mockClub4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(registeredClubJoinState1.getClub()).isNotNull();
        assertThat(registeredClubJoinState1.getUser()).isNotNull();
        assertThat(registeredClubJoinState1.getJoinState()).isEqualTo(JoinState.MASTER);

        assertThat(registeredClubJoinState2.getClub()).isNotNull();
        assertThat(registeredClubJoinState2.getUser()).isNotNull();
        assertThat(registeredClubJoinState2.getJoinState()).isEqualTo(JoinState.MANAGER);

        assertThat(registeredClubJoinState3.getClub()).isNotNull();
        assertThat(registeredClubJoinState3.getUser()).isNotNull();
        assertThat(registeredClubJoinState3.getJoinState()).isEqualTo(JoinState.MEMBER);

        assertThat(registeredClubJoinState4.getClub()).isNotNull();
        assertThat(registeredClubJoinState4.getUser()).isNotNull();
        assertThat(registeredClubJoinState4.getJoinState()).isEqualTo(JoinState.NOT_JOINED);
    }

    @Test
    @DisplayName("유효하지 않은 joinState 코드로는 ClubJoinState 엔티티를 생성할 수 없다.")
    public void Should_Throw_Exception_When_JoinStateCode_Is_Invalid() {
        //when
        int invalidJoinStateCode = 5;

        //then
        assertThrows(IllegalArgumentException.class, () -> clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), invalidJoinStateCode));
    }

    @Test
    @DisplayName("탈퇴한 사용자가 재가입 요청을 하는 경우 isUsed 필드를 true로 변경하고 같은 엔티티 객체를 반환한다.")
    public void Should_Return_Same_Entity_When_Left_User_Request_Rejoin() {
        //given
        ClubJoinState initialJoinState = clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMEMBER);

        //when
        clubJoinStateService.delete(mockUser1.getId(), mockClub1.getId());

        ClubJoinState rejoinState = clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeNotJoined);

        //then
        assertThat(rejoinState).isSameAs(initialJoinState);
        assertThat(rejoinState.getUser()).isEqualTo(initialJoinState.getUser());
        assertThat(rejoinState.getClub()).isEqualTo(initialJoinState.getClub());
    }

    @Test
    @DisplayName("이미 존재하는 (userId, clubId) 쌍에 대해 ClubJoinState 엔티티를 추가로 생성하려고 하면 예외를 반환한다.")
    public void Should_Throw_Exception_When_CreateEntity_If_UserId_And_ClubId_Is_Duplicated() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);

        //then
        assertThrows(RuntimeException.class, () -> clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMANAGER));
    }
    // ========== register ==========

    // ========== getClubJoinState ==========
    @Test
    @DisplayName("pk를 가지고 clubJoinState를 조회할 수 있다.")
    public void Should_Return_ClubJoinState_When_ClubJoinStateId_Is_Valid() {
        //given
        ClubJoinState registeredState = clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);

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
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);

        //when
        Long invalidId = 9999L;

        //then
        assertThrows(NotFoundException.class, () -> clubJoinStateService.getClubJoinState(invalidId));
    }

    @Test
    @DisplayName("userId와 clubId를 가지고 clubJoinState를 조회할 수 있다.")
    public void Should_Return_ClubJoinState_When_UserId_And_ClubId_Is_Valid() {
        //given
        ClubJoinState registeredState = clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);

        //when
        ClubJoinState clubJoinState = clubJoinStateService.getClubJoinState(mockUser1.getId(), mockClub1.getId());

        //then
        assertThat(clubJoinState).isNotNull();
        assertThat(clubJoinState).isSameAs(registeredState);
    }

    @Test
    @DisplayName("유효하지 않은 userId나 clubId를 가지고 clubJoinState를 조회하면 예외를 던진다.")
    public void Should_Throw_Exception_When_UserId_And_ClubId_Is_Invalid() {
        //given
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);

        //when
        User invalidUser = mockUser2;
        Club invalidClub = mockClub2;

        //then
        assertThrows(NotFoundException.class, () -> clubJoinStateService.getClubJoinState(invalidUser.getId(), mockClub1.getId()));
        assertThrows(NotFoundException.class, () -> clubJoinStateService.getClubJoinState(mockUser1.getId(), invalidClub.getId()));
    }
    // ========== getClubJoinState ==========

    // ========== update ==========
    @Test
    @DisplayName("사용자의 클럽 가입 상태를 변경할 수 있다.")
    public void Should_Update_ClubJoinState_When_JoinState_Is_Valid() {
        //given
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        JoinState initialState = clubJoinStateService.getClubJoinState(mockUser1.getId(), mockClub1.getId()).getJoinState();

        //when
        int nxtJoinState = JoinState.MANAGER.getCode();
        clubJoinStateService.update(mockUser1.getId(), mockClub1.getId(), nxtJoinState);
        JoinState nxtState = clubJoinStateService.getClubJoinState(mockUser1.getId(), mockClub1.getId()).getJoinState();

        //then
        assertThat(initialState).isEqualTo(JoinState.MASTER);
        assertThat(nxtState).isEqualTo(JoinState.MANAGER);
    }

    @Test
    @DisplayName("유효하지 않은 JoinState로 가입 상태 변경 시 예외를 던진다.")
    public void Should_Throw_Exception_When_Updating_JoinState_Is_Invalid() {
        //given
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);

        //when
        int invalidJoinStateCode = 5;
        int nxtJoinState = invalidJoinStateCode;

        //then
        assertThrows(IllegalArgumentException.class, () -> clubJoinStateService.update(mockUser1.getId(), mockClub1.getId(), nxtJoinState));
    }
    // ========== update ==========

    // ========== delete ==========
    @Test
    @DisplayName("사용자의 클럽 가입 상태를 삭제한다.(엔티티를 지우지 않고 isUsed 필드만 false로 바꾼다)")
    public void Should_Changed_isUsed_Field_to_False_When_UserId_And_ClubId_Is_Valid() {
        //given
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);

        boolean isUsedBeforeDelete = clubJoinStateService.getClubJoinState(mockUser1.getId(), mockClub1.getId()).isUsed();

        //when
        clubJoinStateService.delete(mockUser1.getId(), mockClub1.getId());
        boolean isUsedAfterDelete = clubJoinStateService.getClubJoinState(mockUser1.getId(), mockClub1.getId()).isUsed();

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
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser1.getId(), mockClub2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser1.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(clubJoinStateService.isClubMaster(mockUser1.getId(), mockClub1.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isClubMaster(mockUser1.getId(), mockClub2.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isClubMaster(mockUser1.getId(), mockClub3.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isClubMaster(mockUser1.getId(), mockClub4.getId())).isEqualTo(false);
    }
    // ========== isClubMaster ==========

    // ========== isClubManager ==========
    @Test
    @DisplayName("사용자의 joinState 권한이 MANAGER인지 판단한다.")
    public void Should_Return_True_When_UserJoinState_Is_MANAGER() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser1.getId(), mockClub2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser1.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(clubJoinStateService.isClubManager(mockUser1.getId(), mockClub1.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isClubManager(mockUser1.getId(), mockClub2.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isClubManager(mockUser1.getId(), mockClub3.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isClubManager(mockUser1.getId(), mockClub4.getId())).isEqualTo(false);
    }
    // ========== isClubManager ==========

    // ========== isClubMember ==========
    @Test
    @DisplayName("사용자의 joinState 권한이 MEMBER인지 판단한다.")
    public void Should_Return_True_When_UserJoinState_Is_MEMBER() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser1.getId(), mockClub2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser1.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(clubJoinStateService.isClubMember(mockUser1.getId(), mockClub1.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isClubMember(mockUser1.getId(), mockClub2.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isClubMember(mockUser1.getId(), mockClub3.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isClubMember(mockUser1.getId(), mockClub4.getId())).isEqualTo(false);
    }
    // ========== isClubMember ==========

    // ========== isJoined ==========
    @Test
    @DisplayName("사용자의 동아리 가입여부를 판단한다.")
    public void Should_Return_True_When_UserJoinState_Is_MASTER_Or_MANAGER_Or_MEMBER() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser1.getId(), mockClub2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser1.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(clubJoinStateService.isJoined(mockUser1.getId(), mockClub1.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isJoined(mockUser1.getId(), mockClub2.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isJoined(mockUser1.getId(), mockClub3.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isJoined(mockUser1.getId(), mockClub4.getId())).isEqualTo(false);
    }
    // ========== isJoined ==========

    // ========== existed ==========
    @Test
    @DisplayName("사용자의 동아리 가입정보 DB row의 유효성(사용 여부)을 판단한다.")
    public void Should_Return_True_When_ClubJoinState_isUsed_Field_Is_True() {
        //given
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeMaster);

        //when
        clubJoinStateService.delete(mockUser2.getId(), mockClub1.getId());

        //then
        assertThat(clubJoinStateService.existed(mockUser1.getId(), mockClub1.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.existed(mockUser2.getId(), mockClub1.getId())).isEqualTo(false);
    }
    // ========== existed ==========

    // ========== isLeaveClub ==========
    @Test
    @DisplayName("사용자의 동아리 탈퇴 여부를 판단한다.")
    public void Should_Return_True_When_ClubJoinState_isUsed_Field_Is_False() {
        //given
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);

        //when
        //then
        assertThat(clubJoinStateService.isLeaveClub(mockUser1.getId(), mockClub1.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.isLeaveClub(mockUser2.getId(), mockClub1.getId())).isEqualTo(false);

        clubJoinStateService.delete(mockUser1.getId(), mockClub1.getId());

        assertThat(clubJoinStateService.isLeaveClub(mockUser1.getId(), mockClub1.getId())).isEqualTo(true);
    }
    // ========== isLeaveClub ==========

    // ========== isRegistered ==========
    @Test
    @DisplayName("사용자의 동아리 등록 이력 여부를 판단한다.")
    public void Should_Return_True_When_User_Registered_Club_Before() {
        //given
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);

        //when
        //then
        assertThat(clubJoinStateService.isRegistered(mockUser1.getId(), mockClub1.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.isRegistered(mockUser2.getId(), mockClub1.getId())).isEqualTo(false);

        clubJoinStateService.delete(mockUser1.getId(), mockClub1.getId());

        assertThat(clubJoinStateService.isRegistered(mockUser1.getId(), mockClub1.getId())).isEqualTo(true);
    }
    // ========== isRegistered ==========

    // ========== hasManagerRole ==========
    @Test
    @DisplayName("사용자의 가입상태가 Master 또는 Manager이면 true를 반환한다.")
    public void Should_Return_True_When_UserJoinState_Is_MASTER_Or_MANAGER() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser1.getId(), mockClub2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser1.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(clubJoinStateService.hasManagerRole(mockUser1.getId(), mockClub1.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.hasManagerRole(mockUser1.getId(), mockClub2.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.hasManagerRole(mockUser1.getId(), mockClub3.getId())).isEqualTo(false);
        assertThat(clubJoinStateService.hasManagerRole(mockUser1.getId(), mockClub4.getId())).isEqualTo(false);
    }
    // ========== hasManagerRole ==========

    // ========== hasMemberRole ==========
    @Test
    @DisplayName("사용자의 가입상태가 Master 또는 Manager 또는 Member이면 true를 반환한다.")
    public void Should_Return_True_When_UserJoinState_Is_Not_미가입() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser1.getId(), mockClub2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser1.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub4.getId(), joinStateCodeNotJoined);

        //then
        assertThat(clubJoinStateService.hasMemberRole(mockUser1.getId(), mockClub1.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.hasMemberRole(mockUser1.getId(), mockClub2.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.hasMemberRole(mockUser1.getId(), mockClub3.getId())).isEqualTo(true);
        assertThat(clubJoinStateService.hasMemberRole(mockUser1.getId(), mockClub4.getId())).isEqualTo(false);
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
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser3.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser4.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser5.getId(), mockClub1.getId(), joinStateCodeMaster);

        ClubJoinState master = clubJoinStateService.getMaster(mockClub1.getId(), testPageRequest);

        //then
        assertThat(master).isNotNull();
    }
    // ========== getMasters ==========

    // ========== getManagers ==========
    @Test
    @DisplayName("가입상태가 Manager인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_MANAGER() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser3.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser4.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser5.getId(), mockClub1.getId(), joinStateCodeMANAGER);

        List<ClubJoinState> managers = clubJoinStateService.getManagers(mockClub1.getId(), testPageRequest);

        //then
        assertThat(managers.size()).isEqualTo(5);
    }
    // ========== getManagers ==========

    // ========== getMembers ==========
    @Test
    @DisplayName("가입상태가 Member인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_MEMBER() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser3.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser4.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser5.getId(), mockClub1.getId(), joinStateCodeMEMBER);

        List<ClubJoinState> members = clubJoinStateService.getMembers(mockClub1.getId(), testPageRequest);

        //then
        assertThat(members.size()).isEqualTo(5);
    }
    // ========== getMembers ==========

    // ========== getAllMembers ==========
    @Test
    @DisplayName("가입상태가 Master, Manager, Member인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_MASTER_Or_MANAGER_Or_MEMBER() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser3.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser4.getId(), mockClub1.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(mockUser5.getId(), mockClub1.getId(), joinStateCodeNotJoined);

        List<ClubJoinState> members = clubJoinStateService.getAllMembers(mockClub1.getId(), testPageRequest);

        //then
        assertThat(members.size()).isEqualTo(3);
    }
    // ========== getAllMembers ==========

    // ========== getAppliedMembers ==========
    @Test
    @DisplayName("가입상태가 NOT_JOINED인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_미가입() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(mockUser3.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser4.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser5.getId(), mockClub1.getId(), joinStateCodeMEMBER);

        List<ClubJoinState> members = clubJoinStateService.getAppliedMembers(mockClub1.getId(), testPageRequest);

        //then
        assertThat(members.size()).isEqualTo(2);
    }
    // ========== getAppliedMembers ==========

    // ========== getManagerRoleMembers ==========
    @Test
    @DisplayName("가입상태가 Master인, Manager인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_MASTER_Or_MANAGER() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser3.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser4.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser5.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser6.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser7.getId(), mockClub1.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(mockUser8.getId(), mockClub1.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(mockUser9.getId(), mockClub1.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(mockUser10.getId(), mockClub1.getId(), joinStateCodeNotJoined);

        List<ClubJoinState> members = clubJoinStateService.getManagerRoleMembers(mockClub1.getId(), testPageRequest);

        //then
        assertThat(members.size()).isEqualTo(4);
    }
    // ========== getManagerRoleMembers ==========

    // ========== getMemberRoleMembers ==========
    @Test
    @DisplayName("가입상태가 Master, Manager, Member인 유저 목록을 반환한다.")
    public void Should_Return_Users_When_UserJoinState_Is_MEMBER_Role() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser2.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser3.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser4.getId(), mockClub1.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser5.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser6.getId(), mockClub1.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser7.getId(), mockClub1.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(mockUser8.getId(), mockClub1.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(mockUser9.getId(), mockClub1.getId(), joinStateCodeNotJoined);
        clubJoinStateService.register(mockUser10.getId(), mockClub1.getId(), joinStateCodeNotJoined);

        List<ClubJoinState> members = clubJoinStateService.getMemberRoleMembers(mockClub1.getId(), testPageRequest);

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
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser1.getId(), mockClub2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser1.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub5.getId(), joinStateCodeNotJoined);

        List<ClubJoinState> userJoinStates = clubJoinStateService.getClubJoinStatesByUser(mockUser1.getId(), testPageRequest);

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
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser1.getId(), mockClub2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser1.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub5.getId(), joinStateCodeNotJoined);

        List<ClubJoinState> joinStatesMaster = clubJoinStateService.getClubJoinStatesByUser(mockUser1.getId(), joinStateCodeMaster, testPageRequest);
        List<ClubJoinState> joinStatesManager = clubJoinStateService.getClubJoinStatesByUser(mockUser1.getId(), joinStateCodeMANAGER, testPageRequest);
        List<ClubJoinState> joinStatesMember = clubJoinStateService.getClubJoinStatesByUser(mockUser1.getId(), joinStateCodeMEMBER, testPageRequest);
        List<ClubJoinState> joinStatesNotJoined = clubJoinStateService.getClubJoinStatesByUser(mockUser1.getId(), joinStateCodeNotJoined, testPageRequest);

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
        int invalidJoinStateCode = 5;

        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser1.getId(), mockClub2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser1.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub5.getId(), joinStateCodeNotJoined);

        //then
        assertThrows(IllegalArgumentException.class,
                () -> clubJoinStateService.getClubJoinStatesByUser(mockUser1.getId(), invalidJoinStateCode, testPageRequest));
    }
    // ========== getClubJoinStatesByUser ==========

    // ========== getClubJoinStatesByUserHasRole ==========
    @Test
    @DisplayName("사용자가 특정 권한 이상을 가진 모든 동아리 가입 상태를 반환한다.")
    public void Should_Return_ClubJoinStates_When_UserJoinState_Is_Contained() {
        //given
        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser1.getId(), mockClub2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser1.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub5.getId(), joinStateCodeNotJoined);

        List<ClubJoinState> hasRolesMaster = clubJoinStateService.getClubJoinStatesByUserHasRole(mockUser1.getId(), joinStateCodeMaster, testPageRequest);
        List<ClubJoinState> hasRolesManager = clubJoinStateService.getClubJoinStatesByUserHasRole(mockUser1.getId(), joinStateCodeMANAGER, testPageRequest);
        List<ClubJoinState> hasRolesMember = clubJoinStateService.getClubJoinStatesByUserHasRole(mockUser1.getId(), joinStateCodeMEMBER, testPageRequest);
        List<ClubJoinState> hasRolesNotJoined = clubJoinStateService.getClubJoinStatesByUserHasRole(mockUser1.getId(), joinStateCodeNotJoined, testPageRequest);

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
        int invalidJoinStateCode = 5;

        //when
        clubJoinStateService.register(mockUser1.getId(), mockClub1.getId(), joinStateCodeMaster);
        clubJoinStateService.register(mockUser1.getId(), mockClub2.getId(), joinStateCodeMANAGER);
        clubJoinStateService.register(mockUser1.getId(), mockClub3.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub4.getId(), joinStateCodeMEMBER);
        clubJoinStateService.register(mockUser1.getId(), mockClub5.getId(), joinStateCodeNotJoined);

        //then
        assertThrows(IllegalArgumentException.class,
                () -> clubJoinStateService.getClubJoinStatesByUserHasRole(mockUser1.getId(), invalidJoinStateCode, testPageRequest));
    }
    // ========== getClubJoinStatesByUserHasRole ==========
    /**
     * End User Region (for User API) Test
     */

    @AfterAll
    public void destrory() {
        clubJoinStateRepository.deleteAll();
        clubRepository.deleteAll();
        userRepository.deleteAll();
    }
}