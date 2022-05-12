package com.example.club_project.service.category;

import com.example.club_project.controller.category.CategoryDTO;
import com.example.club_project.domain.Category;
import com.example.club_project.exception.custom.NotFoundException;
import com.example.club_project.repository.CategoryRepository;
import com.example.club_project.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ValidateUtil validateUtil;

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
    public CategoryDTO.Response getCategoryDto(Long id) {
        return convertToDTO(getCategory(id));
    }

    @Override
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
        checkArgument(StringUtils.isNotEmpty(name), "name 입력값은 필수입니다.");
        checkArgument(StringUtils.isNotEmpty(description), "description 입력값은 필수입니다.");

        Category category = createCategory(name, description);
        validateUtil.validate(category);

        return categoryRepository.save(category);
    }

    private Category createCategory(String name, String description) {
        return Category.builder()
                .name(name)
                .description(description)
                .build();
    }

    @Override
    public Category getCategory(Long id) {
        checkArgument(ObjectUtils.isNotEmpty(id), "id 입력값은 필수입니다.");

        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 id 입니다."));
    }

    @Override
    public Category getCategory(String name) {
        checkArgument(StringUtils.isNotEmpty(name), "name 입력값은 필수입니다.");

        return categoryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 카테고리이름입니다."));
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getCategoriesById(List<Long> categories) {
        checkArgument(ObjectUtils.isNotEmpty(categories), "categories 입력값은 필수입니다.");

        return categoryRepository.findAllById(categories);
    }

    @Override
    public List<Category> getCategoriesByName(List<String> categoryNames) {
        checkArgument(ObjectUtils.isNotEmpty(categoryNames), "categoryNames 입력값은 필수입니다.");

        return categoryRepository.findAllNames(categoryNames);
    }

    @Override
    @Transactional
    public Category update(Long id, String name, String description) {
        checkArgument(ObjectUtils.isNotEmpty(id), "id 입력값은 필수입니다.");
        checkArgument(StringUtils.isNotEmpty(name), "name 입력값은 필수입니다.");
        checkArgument(StringUtils.isNotEmpty(description), "description 입력값은 필수입니다.");

        Category updatedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("수정하려는 id값이 존재하지 않습니다."));

        updatedCategory.update(name, description);

        validateUtil.validate(updatedCategory);

        return updatedCategory;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        checkArgument(ObjectUtils.isNotEmpty(id), "id 입력값은 필수입니다.");

        Category deleteCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("삭제하려는 id값이 존재하지 않습니다."));

        categoryRepository.delete(deleteCategory);
    }
}
