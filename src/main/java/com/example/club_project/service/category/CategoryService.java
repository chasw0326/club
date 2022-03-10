package com.example.club_project.service.category;

import com.example.club_project.domain.Category;

import java.util.List;

public interface CategoryService {

    Category register(String name, String description);

    Category getCategory(String name);

    List<Category> getCategories();

    long delete(String name);
}
