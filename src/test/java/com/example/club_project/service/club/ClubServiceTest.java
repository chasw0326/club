package com.example.club_project.service.club;

import com.example.club_project.domain.Club;
import com.example.club_project.service.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    private String testName = "테스트 동아리";
    private String testAddress = "테스트 주소";
    private String testUniversity = "테스트 대학교";
    private String testDescription = "테스트 동아리 소개";
    private String testCategoryName = "테스트 카테고리 이름";
    private String[] testCategoriesName = {"문화/예술/공연","봉사/사회활동","학술/교양","창업/취업","어학","체육","친목"};

    @BeforeEach
    public void initCategories() {
        for (String categoryName : testCategoriesName) {
            categoryService.register(categoryName, testDescription);
        }

        categoryService.register(testCategoryName, "test");
    }

    @Test
    @DisplayName("{동아리명, 대학교} 정보가 중복된 동아리는 등록할 수 없다")
    public void Should_CreateEntity() {
        //given
        Club savedClub = clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryName, null);

        //when
        //then
        assertThat(savedClub.getName()).isEqualTo(testName);
        assertThat(savedClub.getAddress()).isEqualTo(testAddress);
        assertThat(savedClub.getUniversity()).isEqualTo(testUniversity);
        assertThat(savedClub.getDescription()).isEqualTo(testDescription);
        assertThat(savedClub.getCategory().getName()).isEqualTo(testCategoryName);
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
    @DisplayName("특정 대학교에 속하는 하나의 동아리를 반환한다.")
    public void Should_ReturnClub_When_NameAndUniversity_Valid() {
        //given
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryName, null);

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

        int aUniversityClubsSize = testCategoriesName.length;
        int bUniversityClubsSize = testCategoriesName.length / 2;

        //when
        //then
        assertThat(clubService.getClubs(aUniversity).size()).isEqualTo(aUniversityClubsSize);
        assertThat(clubService.getClubs(bUniversity).size()).isEqualTo(bUniversityClubsSize);
        assertThat(clubService.getClubs(testUniversity).size()).isEqualTo(0);
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
            List<Club> clubs = clubService.getClubs(categorySubSet, testUniversity);

            assertThat(clubs.size()).isEqualTo(0);
        }

        for (int i = 0; i < categories.size(); ++i) {
            List<String> categorySubSet = categories.subList(i, categories.size());
            List<Club> clubs = clubService.getClubs(categorySubSet, aUniversity);

            assertThat(clubs.size()).isEqualTo(categorySubSet.size());
        }

        for (int i = 0; i < categories.size() / 2; ++i) {
            List<String> categorySubSet = categories.subList(i, categories.size() / 2);
            List<Club> clubs = clubService.getClubs(categorySubSet, bUniversity);

            assertThat(clubs.size()).isEqualTo(categorySubSet.size());
        }
    }

    @Test
    @DisplayName("특정 대학교에 있는 하나의 동아리를 삭제한다")
    public void Should_DeleteClub_When_ClubnameAndUniversity_Valid() {
        //given
        clubService.register(testName, testAddress, testUniversity, testDescription, testCategoryName, null);

        //when
        String invalidClubName = "존재하지 않는 동아리";
        String invalidUniverity = "존재하지 않는 대학교";

        long inValidDeleteQueryResult = clubService.delete(invalidClubName, invalidUniverity);
        long validDeleteQueryResult = clubService.delete(testName, testUniversity);

        //then
        assertThat(inValidDeleteQueryResult).isEqualTo(0L);
        assertThat(validDeleteQueryResult).isEqualTo(1L);
    }
}