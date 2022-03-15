package com.example.club_project.service.category;

import com.example.club_project.controller.category.CategoryDTO;
import com.example.club_project.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    String categoryName, categoryDescription;

    @BeforeEach
    public void setup() {
        categoryName = "test";
        categoryDescription = "description";
    }

    @Test
    @DisplayName("카테고리 Entity를 DTO로 변환할 수 있다")
    public void Should_TranslateEntity() {
        //given
        Category registeredCategory = categoryService.register(categoryName, categoryDescription);

        //when
        CategoryDTO.Response registeredCategoryDTO = CategoryDTO.Response.from(registeredCategory);

        //then
        assertThat(registeredCategoryDTO.getId()).isEqualTo(registeredCategory.getId());
        assertThat(registeredCategoryDTO.getName()).isEqualTo(registeredCategory.getName());
        assertThat(registeredCategoryDTO.getDescription()).isEqualTo(registeredCategory.getDescription());
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
    @DisplayName("카테고리 id(PK)가 존재하면 해당하는 Category를 반환한다")
    public void Should_ReturnCategory_When_CategoryId_Exist() {
        //given
        Category savedCategory = categoryService.register(categoryName, categoryDescription);

        //when
        Category findCategory = categoryService.getCategory(savedCategory.getId());

        //then
        assertThat(findCategory).isSameAs(savedCategory);
        assertThat(findCategory.getId()).isEqualTo(savedCategory.getId());
        assertThat(findCategory.getName()).isEqualTo(savedCategory.getName());
        assertThat(findCategory.getDescription()).isEqualTo(savedCategory.getDescription());
    }

    @Test
    @DisplayName("없는 카테고리 id(PK)로 조회시 예외를 반환한다")
    public void Should_ThrowException_When_CategoryId_NotExisted() {
        //given
        categoryService.register(categoryName, categoryDescription);

        //when
        Long invalidId = 999L;

        //then
        assertThrows(EntityNotFoundException.class, () -> categoryService.getCategory(invalidId));
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
        assertThrows(EntityNotFoundException.class, () -> categoryService.getCategory(nonExistCategoryName));
    }

    @Test
    @DisplayName("getCategoriesById 메서드 실행 시 id에 해당하는 모든 카테고리 엔티티를 반환한다")
    public void Should_Return_All_Categories_By_Id() {
        //given
        List<Long> categories = new ArrayList<>();

        int beforeCategorySize = categoryService.getCategories().size();
        int newCategorySize = 10;
        for (int i = 0; i < newCategorySize; ++i) {
            String categoryName = String.format("%d번째 카테고리", i);
            String categoryDescription = String.format("%d번째 테스트 카테고리 설명입니다", i);
            Category registeredCategory = categoryService.register(categoryName, categoryDescription);
            categories.add(registeredCategory.getId());
        }

        //when
        List<Category> result = categoryService.getCategoriesById(categories);

        //then
        assertThat(result.size()).isEqualTo(beforeCategorySize + newCategorySize);
    }

    @Test
    @DisplayName("getCategoriesById 메서드 실행 시 유효하지 않는 id에 대한 엔티티 결과는 반환하지 않는다")
    public void Should_Not_ReturnCategories_When_CategoryId_NotExisted() {
        //given
        List<Long> categories = new ArrayList<>();
        Long someInvalidId = 999L;

        int beforeCategorySize = categoryService.getCategories().size();
        int newCategorySize = 10;
        for (int i = 0; i < newCategorySize; ++i) {
            String categoryName = String.format("%d번째 카테고리", i);
            String categoryDescription = String.format("%d번째 테스트 카테고리 설명입니다", i);
            categoryService.register(categoryName, categoryDescription);
        }

        categories.add(someInvalidId++);
        categories.add(someInvalidId++);
        categories.add(someInvalidId++);
        categories.add(someInvalidId++);
        categories.add(someInvalidId++);

        //when
        List<Category> originSize = categoryService.getCategories();
        List<Category> result = categoryService.getCategoriesById(categories);

        //then
        assertThat(originSize.size()).isEqualTo(beforeCategorySize + newCategorySize);
        assertThat(result.size()).isEqualTo(0);
    }

    // List<Category> getCategoriesByName(List<String> categoryNames);
    @Test
    @DisplayName("getCategoriesByName 메서드 실행 시 Name이 일치하는 모든 카테고리 엔티티를 반환한다")
    public void Should_Return_All_Categories_By_Names() {
        //given
        List<String> categories = new ArrayList<>();

        int beforeCategorySize = categoryService.getCategories().size();
        int newCategorySize = 10;
        for (int i = 0; i < newCategorySize; ++i) {
            String categoryName = String.format("%d번째 카테고리", i);
            String categoryDescription = String.format("%d번째 테스트 카테고리 설명입니다", i);
            Category registeredCategory = categoryService.register(categoryName, categoryDescription);
            categories.add(registeredCategory.getName());
        }

        //when
        List<Category> result = categoryService.getCategoriesByName(categories);

        //then
        assertThat(result.size()).isEqualTo(beforeCategorySize + newCategorySize);
    }

    @Test
    @DisplayName("getCategoriesByName 메서드 실행 시 Name이 일치하는 모든 카테고리 엔티티를 반환한다")
    public void Should_Not_ReturnCategories_When_CategoryNames_NotExisted() {
        //given
        List<String> categories = new ArrayList<>();

        int beforeCategorySize = categoryService.getCategories().size();
        int newCategorySize = 10;
        for (int i = 0; i < newCategorySize; ++i) {
            String categoryName = String.format("%d번째 카테고리", i);
            String categoryDescription = String.format("%d번째 테스트 카테고리 설명입니다", i);
            categoryService.register(categoryName, categoryDescription);
        }

        categories.add("Invalid_CategoryName1");
        categories.add("Invalid_CategoryName2");
        categories.add("Invalid_CategoryName3");
        categories.add("Invalid_CategoryName4");
        categories.add("Invalid_CategoryName5");

        //when
        List<Category> originSize = categoryService.getCategories();
        List<Category> result = categoryService.getCategoriesByName(categories);

        //then
        assertThat(originSize.size()).isEqualTo(beforeCategorySize + newCategorySize);
        assertThat(result.size()).isEqualTo(0);
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
    @DisplayName("Entity 삭제 시 id를 가지고 엔티티를 삭제한다")
    public void Should_Delete_Entity_When_Removed_Id_isValid() {
        //given
        Category registeredCategory = categoryService.register(categoryName, categoryDescription);

        //when
        categoryService.delete(registeredCategory.getId());

        //then
        assertThrows(EntityNotFoundException.class, () -> categoryService.getCategory(registeredCategory.getId()));
    }
}