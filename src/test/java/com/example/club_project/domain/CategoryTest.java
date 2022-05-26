package com.example.club_project.domain;

import com.example.club_project.repository.CategoryRepository;
import com.example.club_project.util.ValidateUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CategoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ValidateUtil validateUtil;

    @Test
    @DisplayName("Category Entity Validation 테스트")
    public void Category_Entity_Validation_test() {
        //given
        String validCategoryName = "정확히스무글자가되게맞춘카테고리명입니다";
        String invalidCategoryName = "스무글자가 훨씬 넘는 잘못된 사이즈의 카테고리명입니다.";
        String description = "description";

        //when
        Category category = Category.builder()
                .name(validCategoryName)
                .description(description)
                .build();

        //then
        validateUtil.validate(category);
        categoryRepository.save(category);

        //when
        Category invalidCategory = Category.builder()
                .name(invalidCategoryName)
                .description(description)
                .build();

        //then
        assertThrows(ValidationException.class, () -> validateUtil.validate(invalidCategory));
    }
}