package com.example.club_project.service.category;

import com.example.club_project.controller.category.CategoryDTO;
import com.example.club_project.domain.Category;
import com.example.club_project.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * DTO region
     * for Controller
     */
    @Override
    @Transactional
    public CategoryDTO.Response registerCategory(String name, String description) {
        return convertToDTO(register(name, description));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO.Response getCategoryDto(Long id) {
        return convertToDTO(getCategory(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO.Response> getCategoryDtos() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDTO.Response updateCategory(Long id, String name, String description) {
        Category updatedCategory = update(id, name, description);
        return convertToDTO(updatedCategory);
    }

    private CategoryDTO.Response convertToDTO(Category category) {
        return CategoryDTO.Response.from(category);
    }

    /**
     * Service Region
     * for other Services
     */
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
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 id 입니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategory(String name) {
        Objects.requireNonNull(name, "name 입력값은 필수입니다.");

        //TODO: 사용자 정의 Exception으로 수정
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 카테고리이름입니다."));
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
    public Category update(Long id, String name, String description) {
        Objects.requireNonNull(id, "id 입력값은 필수입니다.");

        Category updatedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("수정하려는 id값이 존재하지 않습니다."));

        updatedCategory.update(name, description);

        return updatedCategory;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id, "id 입력값은 필수입니다.");
        Category deleteCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("삭제하려는 id값이 존재하지 않습니다."));

        categoryRepository.delete(deleteCategory);
    }
}
