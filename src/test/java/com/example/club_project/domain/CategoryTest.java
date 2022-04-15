package com.example.club_project.domain;

import com.example.club_project.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CategoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Category Entity Validation 테스트")
    public void Category_Entity_Validation_test() {
        //given
        String validCategoryName = "정확히스무글자가되게맞춘카테고리명입니다";
        String invalidCategoryName = "스무글자가 훨씬 넘는 잘못된 사이즈의 카테고리명입니다.";
        String description = "description";

        //then
        Category category = Category.builder()
                .name(validCategoryName)
                .description(description)
                .build();

        categoryRepository.save(category);

        assertThrows(IllegalArgumentException.class, () -> Category.builder()
                                                                    .name(invalidCategoryName)
                                                                    .description(description)
                                                                    .build());
    }
}