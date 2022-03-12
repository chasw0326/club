package com.example.club_project.service.club;

import com.example.club_project.controller.club.ClubDTO;
import com.example.club_project.service.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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
    private CategoryService categoryService;

    @Autowired
    private ClubService clubService;

    private ClubDTO.Response registeredClub;

    private String testName = "테스트 동아리";
    private String testAddress = "테스트 주소";
    private String testUniversity = "테스트 대학교";
    private String testDescription = "테스트 동아리 소개";
    private String testCategoryName = "테스트 카테고리 이름";
    private String[] testCategoriesName = {"문화/예술/공연", "봉사/사회활동", "학술/교양", "창업/취업", "어학", "체육", "친목"};

    private int testPagingOffsetSize = 0;
    private int testPagingLimitSize = 50;
    private PageRequest testPageRequest = PageRequest.of(testPagingOffsetSize, testPagingLimitSize);

    @BeforeEach
    public void setup() {
        for (String categoryName : testCategoriesName) {
            categoryService.register(categoryName, testDescription);
        }

        categoryService.register(testCategoryName, "test");
    }

    @Test
    @DisplayName("{동아리명, 대학교} 정보가 중복된 동아리는 등록할 수 없다")
    public void Should_CreateEntity() {
        //given
        registeredClub = clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryName, null);

        //when
        //then
        assertThat(registeredClub.getName()).isEqualTo(testName);
        assertThat(registeredClub.getAddress()).isEqualTo(testAddress);
        assertThat(registeredClub.getUniversity()).isEqualTo(testUniversity);
        assertThat(registeredClub.getDescription()).isEqualTo(testDescription);
        assertThat(registeredClub.getCategory()).isEqualTo(testCategoryName);
    }

    @Test
    @DisplayName("동아리 정보를 수정할 수 있다")
    public void Should_UpdateEntity() {
        //given
        registeredClub = clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryName, null);

        //when
        String newName = "새 동아리 이름";
        String newAddress = "새 주소";
        String newUniversity = "새 대학교";
        String newDescription = "새 소개";
        ClubDTO.Response updatedClub = clubService.update(registeredClub.getId(), newName, newAddress, newUniversity, newDescription, null, null);

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
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryName, null);

        //when
        //then
        assertThrows(EntityExistsException.class, () -> clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryName, null));
    }

    @Test
    @DisplayName("{동아리명, 대학교} 정보는 중복될 수 없다")
    public void Should_True_When_NameAndUniversity_Duplicated() {
        //given
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryName, null);

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
        registeredClub = clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryName, null);

        //when
        ClubDTO.Response club = clubService.getClub(registeredClub.getId());

        //then
        assertThat(club).isNotNull();
        assertThat(club.getName()).isEqualTo(testName);
        assertThat(club.getUniversity()).isEqualTo(testUniversity);
    }

    @Test
    @DisplayName("특정 대학교에 속하는 하나의 동아리를 반환한다.")
    public void Should_ReturnClub_When_NameAndUniversity_Valid() {
        //given
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryName, null);

        //when
        ClubDTO.Response club = clubService.getClub(testName, testUniversity);

        //then
        assertThat(club).isNotNull();
        assertThat(club.getName()).isEqualTo(testName);
        assertThat(club.getUniversity()).isEqualTo(testUniversity);
    }

    @Test
    @DisplayName("특정 대학교에 속하는 동아리가 없다면 예외를 반환한다")
    public void Should_ThrowException_When_NameAndUniversity_InValid() {
        //given
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryName, null);

        //when
        String invalidClubName = "존재하지 않는 동아리";
        String invalidUniverity = "존재하지 않는 대학교";

        //then
        assertThrows(EntityNotFoundException.class, () -> clubService.getClub(invalidClubName, testUniversity));
        assertThrows(EntityNotFoundException.class, () -> clubService.getClub(testUniversity, invalidUniverity));
        assertThrows(EntityNotFoundException.class, () -> clubService.getClub(invalidClubName, invalidUniverity));
    }

    @Test
    @DisplayName("특정 대학교에 속하는 모든 동아리를 반환한다")
    public void Should_ReturnClubs_When_University_Valid() {
        //given
        String aUniversity = "A대학교";
        for (int i = 0; i < testCategoriesName.length; ++i) {
            String clubName = String.format("%d번 테스트 동아리", i);
            clubService.register(clubName, testAddress, aUniversity, testDescription, testCategoriesName[i], null);
        }

        String bUniversity = "B대학교";
        for (int i = 0; i < testCategoriesName.length / 2; ++i) {
            String clubName = String.format("%d번 테스트 동아리", i);
            clubService.register(clubName, testAddress, bUniversity, testDescription, testCategoriesName[i], null);
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
            clubService.register(clubName, testAddress, aUniversity, testDescription, testCategoriesName[i], null);
        }

        String bUniversity = "B대학교";
        for (int i = 0; i < testCategoriesName.length / 2; ++i) {
            String clubName = String.format("%d번 테스트 동아리", i);
            clubService.register(clubName, testAddress, bUniversity, testDescription, testCategoriesName[i], null);
        }

        //when
        List<String> categories = Arrays.stream(testCategoriesName).collect(Collectors.toList());

        //then
        for (int i = 0; i < categories.size(); ++i) {
            List<String> categorySubSet = categories.subList(i, categories.size());
            List<ClubDTO.Response> clubs = clubService.getClubs(categorySubSet, testUniversity, testPageRequest);

            assertThat(clubs.size()).isEqualTo(0);
        }

        for (int i = 0; i < categories.size(); ++i) {
            List<String> categorySubSet = categories.subList(i, categories.size());
            List<ClubDTO.Response> clubs = clubService.getClubs(categorySubSet, aUniversity, testPageRequest);

            assertThat(clubs.size()).isEqualTo(categorySubSet.size());
        }

        for (int i = 0; i < categories.size() / 2; ++i) {
            List<String> categorySubSet = categories.subList(i, categories.size() / 2);
            List<ClubDTO.Response> clubs = clubService.getClubs(categorySubSet, bUniversity, testPageRequest);

            assertThat(clubs.size()).isEqualTo(categorySubSet.size());
        }
    }

    @Test
    @DisplayName("Club id를 parameter로 주면 동아리를 삭제한다")
    public void Should_DeleteClub_When_ClubId_Valid() {
        //given
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryName, null);

        //when
        ClubDTO.Response club = clubService.getClub(testName, testUniversity);
        clubService.delete(club.getId());

        //then
        assertThrows(EntityNotFoundException.class, () -> clubService.getClub(testName, testUniversity));
    }
}