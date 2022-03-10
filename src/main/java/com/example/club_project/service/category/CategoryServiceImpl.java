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

    @Transactional
    public Category register(String name, String description) {
        Category category = Category.builder()
                                    .name(name)
                                    .description(description)
                                    .build();

        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category getCategory(String name) {
        //TODO: 사용자 정의 Exception으로 수정
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("throw notFoundException"));
    }

    @Transactional
    public long delete(String name) {
        return categoryRepository.deleteByName(name);
    }
}
