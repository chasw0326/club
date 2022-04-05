package com.example.club_project.service.club;

import com.example.club_project.controller.club.ClubDTO;
import com.example.club_project.domain.Category;
import com.example.club_project.domain.Club;
import com.example.club_project.exception.custom.AlreadyExistsException;
import com.example.club_project.exception.custom.NotFoundException;
import com.example.club_project.service.category.CategoryService;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ClubServiceTest {

    @Autowired
    private ClubService clubService;

    private static final String testName = "테스트 동아리";
    private static final String testAddress = "테스트 주소";
    private static final String testUniversity = "테스트 대학교";
    private static final String testDescription = "테스트 동아리 소개";
    private static final String testCategoryName = "테스트 카테고리 이름";
    private static final String[] testCategoriesName = {"문화/예술/공연", "봉사/사회활동", "학술/교양", "창업/취업", "어학", "체육", "친목"};
    private static Long testCategoryId;
    private static Long[] testCategoryIds;
    private static Category testCategory;


    private final int testPagingOffsetSize = 0;
    private final int testPagingLimitSize = 50;
    private final PageRequest testPageRequest = PageRequest.of(testPagingOffsetSize, testPagingLimitSize);

    @BeforeAll
    public static void setup(@Autowired CategoryService categoryService) {

        List<Long> testCategoryLists = new ArrayList<>();

        for (String categoryName : testCategoriesName) {
            Category registeredCategory = categoryService.register(categoryName, testDescription);
            testCategoryLists.add(registeredCategory.getId());
        }

        testCategory = categoryService.register(testCategoryName, "test");
        testCategoryId = testCategory.getId();
        testCategoryIds = testCategoryLists.toArray(new Long[testCategoryLists.size()]);
    }

    @Test
    @DisplayName("동아리 Entity를 DTO로 변환할 수 있다")
    public void Should_TranslateEntity() {
        //given
        Club registeredClub = clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryId, null);

        //when
        ClubDTO.Response registeredClubDTO = ClubDTO.Response.from(registeredClub);

        //then
        assertThat(registeredClubDTO.getId()).isEqualTo(registeredClub.getId());
        assertThat(registeredClubDTO.getName()).isEqualTo(registeredClub.getName());
        assertThat(registeredClubDTO.getAddress()).isEqualTo(registeredClub.getAddress());
        assertThat(registeredClubDTO.getUniversity()).isEqualTo(registeredClub.getUniversity());
        assertThat(registeredClubDTO.getDescription()).isEqualTo(registeredClub.getDescription());
        assertThat(registeredClubDTO.getImageUrl()).isEqualTo("");
        assertThat(registeredClubDTO.getCategory()).isEqualTo(registeredClub.getCategory().getId());

    }

    @Test
    @DisplayName("동아리를 등록할 수 있다")
    public void Should_CreateEntity() {
        //given
        Club registeredClub = clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryId, null);

        //when
        //then
        assertThat(registeredClub.getName()).isEqualTo(testName);
        assertThat(registeredClub.getAddress()).isEqualTo(testAddress);
        assertThat(registeredClub.getUniversity()).isEqualTo(testUniversity);
        assertThat(registeredClub.getDescription()).isEqualTo(testDescription);
        assertThat(registeredClub.getCategory().getName()).isEqualTo(testCategory.getName());
        assertThat(registeredClub.getCategory().getDescription()).isEqualTo(testCategory.getDescription());
    }

    @Test
    @DisplayName("동아리 정보를 수정할 수 있다")
    public void Should_UpdateEntity() {
        //given
        Club registeredClub = clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryId, null);

        //when
        String newName = "새 동아리 이름";
        String newAddress = "새 주소";
        String newUniversity = "새 대학교";
        String newDescription = "새 소개";
        Club updatedClub = clubService.update(registeredClub.getId(), newName, newAddress, newUniversity, newDescription, null, null);

        //then
        assertThat(updatedClub.getId()).isEqualTo(registeredClub.getId());
        assertThat(updatedClub.getName()).isEqualTo(newName);
        assertThat(updatedClub.getAddress()).isEqualTo(newAddress);
        assertThat(updatedClub.getUniversity()).isEqualTo(newUniversity);
        assertThat(updatedClub.getDescription()).isEqualTo(newDescription);
    }

    @Test
    @DisplayName("{동아리명, 대학교} 정보가 중복된 동아리는 등록할 수 없다")
    public void Should_ThrowException_When_Registerd_Club_NameAndUniversity_Duplicated() {
        //given
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryId, null);

        //when
        //then
        assertThrows(AlreadyExistsException.class, () -> clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryId, null));
    }

    @Test
    @DisplayName("{동아리명, 대학교} 정보는 중복될 수 없다")
    public void Should_True_When_NameAndUniversity_Duplicated() {
        //given
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryId, null);

        //when
        String invalidClubName = "존재하지 않는 동아리";
        String invalidUniverity = "존재하지 않는 대학교";

        //then
        assertThat(clubService.existed(testName, testUniversity)).isEqualTo(true);
        assertThat(clubService.existed(invalidClubName, testUniversity)).isEqualTo(false);
        assertThat(clubService.existed(testName, invalidUniverity)).isEqualTo(false);
    }

    @Test
    @DisplayName("Id에 속하는 하나의 동아리를 반환한다.")
    public void Should_ReturnClub_When_ClubId_Valid() {
        //given
        Club registeredClub = clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryId, null);

        //when
        Club club = clubService.getClub(registeredClub.getId());

        //then
        assertThat(club).isNotNull();
        assertThat(club.getName()).isEqualTo(testName);
        assertThat(club.getUniversity()).isEqualTo(testUniversity);
    }

    @Test
    @DisplayName("특정 대학교에 속하는 하나의 동아리를 반환한다.")
    public void Should_ReturnClub_When_NameAndUniversity_Valid() {
        //given
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryId, null);

        //when
        Club club = clubService.getClub(testName, testUniversity);

        //then
        assertThat(club).isNotNull();
        assertThat(club.getName()).isEqualTo(testName);
        assertThat(club.getUniversity()).isEqualTo(testUniversity);
    }

    @Test
    @DisplayName("특정 대학교에 속하는 동아리가 없다면 예외를 반환한다")
    public void Should_ThrowException_When_NameAndUniversity_InValid() {
        //given
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryId, null);

        //when
        String invalidClubName = "존재하지 않는 동아리";
        String invalidUniverity = "존재하지 않는 대학교";

        //then
        assertThrows(NotFoundException.class, () -> clubService.getClub(invalidClubName, testUniversity));
        assertThrows(NotFoundException.class, () -> clubService.getClub(testUniversity, invalidUniverity));
        assertThrows(NotFoundException.class, () -> clubService.getClub(invalidClubName, invalidUniverity));
    }

    @Test
    @DisplayName("특정 대학교에 속하는 동아리 중에 검색어를 포함하는 이름을 가진 동아리는 모두 반환한다")
    public void Should_ReturnClubs_When_Name_Is_Containing_KeyWord() {
        //given
        String clubName1 = "ABC";
        String clubName2 = "ABCD";
        clubService.register(clubName1, testAddress, testUniversity, testDescription, testCategoryId, null);
        clubService.register(clubName2, testAddress, testUniversity, testDescription, testCategoryId, null);
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryId, null);

        //when
        String searchKeyword = "AB";
        List<Club> club = clubService.getClubs(searchKeyword, testUniversity, testPageRequest);

        //then
        assertThat(club.size()).isEqualTo(2);
        assertThat(club.get(0).getUniversity()).isEqualTo(testUniversity);
        assertThat(club.get(1).getUniversity()).isEqualTo(testUniversity);
    }

    @Test
    @DisplayName("특정 대학교에 속하는 모든 동아리를 반환한다")
    public void Should_ReturnClubs_When_University_Valid() {
        //given
        String aUniversity = "A대학교";
        for (int i = 0; i < testCategoriesName.length; ++i) {
            String clubName = String.format("%d번 테스트 동아리", i);
            clubService.register(clubName, testAddress, aUniversity, testDescription, testCategoryIds[i], null);
        }

        String bUniversity = "B대학교";
        for (int i = 0; i < testCategoriesName.length / 2; ++i) {
            String clubName = String.format("%d번 테스트 동아리", i);
            clubService.register(clubName, testAddress, bUniversity, testDescription, testCategoryIds[i], null);
        }

        //when
        int aUniversityClubsSize = testCategoriesName.length;
        int bUniversityClubsSize = testCategoriesName.length / 2;

        //then
        assertThat(clubService.getClubs(aUniversity, testPageRequest).size()).isEqualTo(aUniversityClubsSize);
        assertThat(clubService.getClubs(bUniversity, testPageRequest).size()).isEqualTo(bUniversityClubsSize);
        assertThat(clubService.getClubs(testUniversity, testPageRequest).size()).isEqualTo(0);
    }


    @Test
    @DisplayName("특정 대학교에 속하고 해당 카테고리에 속하는 모든 동아리를 반환한다")
    public void Should_ReturnClubs_When_UniversityAndCategoryNames_Valid() {
        //given
        String aUniversity = "A대학교";
        for (int i = 0; i < testCategoriesName.length; ++i) {
            String clubName = String.format("%d번 테스트 동아리", i);
            clubService.register(clubName, testAddress, aUniversity, testDescription, testCategoryIds[i], null);
        }

        String bUniversity = "B대학교";
        for (int i = 0; i < testCategoriesName.length / 2; ++i) {
            String clubName = String.format("%d번 테스트 동아리", i);
            clubService.register(clubName, testAddress, bUniversity, testDescription, testCategoryIds[i], null);
        }

        //when
        List<Long> categories = Arrays.stream(testCategoryIds).collect(Collectors.toList());

        //then
        for (int i = 0; i < categories.size(); ++i) {
            List<Long> categorySubSet = categories.subList(i, categories.size());
            List<Club> clubs = clubService.getClubs(categorySubSet, testUniversity, testPageRequest);

            assertThat(clubs.size()).isEqualTo(0);
        }

        for (int i = 0; i < categories.size(); ++i) {
            List<Long> categorySubSet = categories.subList(i, categories.size());
            List<Club> clubs = clubService.getClubs(categorySubSet, aUniversity, testPageRequest);

            assertThat(clubs.size()).isEqualTo(categorySubSet.size());
        }

        for (int i = 0; i < categories.size() / 2; ++i) {
            List<Long> categorySubSet = categories.subList(i, categories.size() / 2);
            List<Club> clubs = clubService.getClubs(categorySubSet, bUniversity, testPageRequest);

            assertThat(clubs.size()).isEqualTo(categorySubSet.size());
        }
    }

    @Test
    @DisplayName("특정 대학교에 속하고 해당 카테고리에 속하고 검색어를 포함하는 이름을 가진 모든 동아리를 반환한다")
    public void Should_ReturnClubs_When_UniversityAndCategoryNamesAndClubName_Valid() {
        //given
        String aUniversity = "A대학교";
        for (int i = 0; i < testCategoriesName.length; ++i) {
            String clubName = String.format("%d번 테스트 동아리", i);
            clubService.register(clubName, testAddress, aUniversity, testDescription, testCategoryIds[i], null);
        }

        String bUniversity = "B대학교";
        for (int i = 0; i < testCategoriesName.length / 2; ++i) {
            String clubName = String.format("%d번 테스트 동아리", i);
            clubService.register(clubName, testAddress, bUniversity, testDescription, testCategoryIds[i], null);
        }

        //when
        String searchKeyword = "번 테스트";
        List<Long> categories = Arrays.stream(testCategoryIds).collect(Collectors.toList());

        //then
        for (int i = 0; i < categories.size(); ++i) {
            List<Long> categorySubSet = categories.subList(i, categories.size());
            List<Club> clubs = clubService.getClubs(categorySubSet, testUniversity, searchKeyword, testPageRequest);

            assertThat(clubs.size()).isEqualTo(0);
        }

        for (int i = 0; i < categories.size(); ++i) {
            List<Long> categorySubSet = categories.subList(i, categories.size());
            List<Club> clubs = clubService.getClubs(categorySubSet, aUniversity, searchKeyword, testPageRequest);

            assertThat(clubs.size()).isEqualTo(categorySubSet.size());
        }

        for (int i = 0; i < categories.size() / 2; ++i) {
            List<Long> categorySubSet = categories.subList(i, categories.size() / 2);
            List<Club> clubs = clubService.getClubs(categorySubSet, bUniversity, searchKeyword, testPageRequest);

            assertThat(clubs.size()).isEqualTo(categorySubSet.size());
        }
    }

    @Test
    @DisplayName("Club id를 parameter로 주면 동아리를 삭제한다")
    public void Should_DeleteClub_When_ClubId_Valid() {
        //given
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryId, null);

        //when
        Club club = clubService.getClub(testName, testUniversity);
        clubService.delete(club.getId());

        //then
        assertThrows(NotFoundException.class, () -> clubService.getClub(testName, testUniversity));
    }
}