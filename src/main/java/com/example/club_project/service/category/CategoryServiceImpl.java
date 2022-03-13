package com.example.club_project.service.category;

import com.example.club_project.domain.Category;
import com.example.club_project.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category register(String name, String description) {
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
        //TODO: 사용자 정의 Exception으로 수정
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategory(String name) {
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
        return categoryRepository.findAllById(categories);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategoriesByName(List<String> categoryNames) {
        return categoryRepository.findAllNames(categoryNames);
    }

    @Override
    @Transactional
    public long delete(String name) {
        return categoryRepository.deleteByName(name);
    }
}
