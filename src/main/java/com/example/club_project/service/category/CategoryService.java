package com.example.club_project.service.category;

import com.example.club_project.controller.category.CategoryDTO;
import com.example.club_project.domain.Category;

import java.util.List;

public interface CategoryService {

    /**
     * DTO Region (for other controllers)
     */
    CategoryDTO.Response registerCategory(String name, String description);

    CategoryDTO.Response getCategoryDto(Long id);

    List<CategoryDTO.Response> getCategoryDtos();

    CategoryDTO.Response updateCategory(Long id, String name, String description);

    /**
     * Entity Region (for other services)
     */
    Category register(String name, String description);

    Category getCategory(Long id);

    Category getCategory(String name);

    List<Category> getCategories();

    List<Category> getCategoriesById(List<Long> categories);

    List<Category> getCategoriesByName(List<String> categoryNames);

    Category update(Long id, String name, String description);

    void delete(Long id);
}
