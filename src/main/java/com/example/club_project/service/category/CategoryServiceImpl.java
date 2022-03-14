package com.example.club_project.service.category;

import com.example.club_project.domain.Category;
import com.example.club_project.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category register(String name, String description) {
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");
        Objects.requireNonNull(description, "description 입력값은 필수입니다.");

        return categoryRepository.save(createCategory(name, description));
    }

    private Category createCategory(String name, String description) {
        return Category.builder()
                .name(name)
                .description(description)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategory(Long id) {
        Objects.requireNonNull(id, "id 입력값은 필수입니다.");

        //TODO: 사용자 정의 Exception으로 수정
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategory(String name) {
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");

        //TODO: 사용자 정의 Exception으로 수정
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategoriesById(List<Long> categories) {
        Objects.requireNonNull(categories, "categories 입력값은 필수입니다.");
        return categoryRepository.findAllById(categories);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategoriesByName(List<String> categoryNames) {
        Objects.requireNonNull(categoryNames, "categoryNames 입력값은 필수입니다.");
        return categoryRepository.findAllNames(categoryNames);
    }

    @Override
    @Transactional
    public long delete(String name) {
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");
        return categoryRepository.deleteByName(name);
    }
}
