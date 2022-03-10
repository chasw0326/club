package com.example.club_project.service.category;

import com.example.club_project.domain.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    String categoryName, categoryDescription;

    @BeforeEach
    public void init() {
        categoryName = "test";
        categoryDescription = "description";
    }

    @Test
    @DisplayName("register 메서드로 name,description 파라미터를 넘겨주면 Category를 반환한다")
    public void Should_ReturnCategory_When_Register_CategoryNameAndDescription() {
        //given
        int beforeCategorySize = categoryService.getCategories().size();

        //when
        Category registeredCategory = categoryService.register(categoryName, categoryDescription);

        //then
        assertThat(categoryService.getCategories().size()).isEqualTo(beforeCategorySize + 1);
        assertThat(registeredCategory.getName()).isEqualTo(categoryName);
        assertThat(registeredCategory.getDescription()).isEqualTo(categoryDescription);
    }

    @Test
    @DisplayName("카테고리명이 존재하면 해당 Category를 반환한다")
    public void Should_ReturnCategory_When_CategoryName_Exist() {
        //given
        Category savedCategory = categoryService.register(categoryName, categoryDescription);

        //when
        Category findCategory = categoryService.getCategory(categoryName);

        //then
        assertThat(findCategory.getId()).isEqualTo(savedCategory.getId());
        assertThat(findCategory.getName()).isEqualTo(savedCategory.getName());
        assertThat(findCategory.getDescription()).isEqualTo(savedCategory.getDescription());
    }

    @Test
    @DisplayName("카테고리명이 존재하지 않으면 예외를 반환한다")
    public void Should_ThrowException_When_CategoryName_NotExisted() {
        //given
        categoryService.register(categoryName, categoryDescription);

        //when
        String nonExistCategoryName = "존재하지 않는 카테고리명";

        //then
        Assertions.assertThrows(EntityNotFoundException.class, () -> categoryService.getCategory(nonExistCategoryName));
    }

    @Test
    @DisplayName("getCategories 메서드 실행 시 모든 카테고리 엔티티를 반환한다")
    public void Should_Return_All_Categories() {
        //given
        int beforeCategorySize = categoryService.getCategories().size();
        int newCategorySize = 10;
        for (int i = 0; i < newCategorySize; ++i) {
            String categoryName = String.format("%d번째 카테고리", i);
            String categoryDescription = String.format("%d번째 테스트 카테고리 설명입니다", i);
            categoryService.register(categoryName, categoryDescription);
        }

        //when
        List<Category> categories = categoryService.getCategories();

        //then
        assertThat(categories.size()).isEqualTo(beforeCategorySize + newCategorySize);
    }

    @Test
    @DisplayName("Entity 삭제 시 삭제된 엔티티 갯수를 리턴한다")
    public void Should_Return_1L_When_Remove_OneEntity() {
        //given
        int beforeCategorySize = categoryService.getCategories().size();
        categoryService.register(categoryName, categoryDescription);

        //when
        long deleteEntitySize = categoryService.delete(categoryName);

        //then
        assertThat(categoryService.getCategories().size()).isEqualTo(beforeCategorySize);
        assertThat(deleteEntitySize).isEqualTo(1);
    }
}