package com.example.club_project.controller.category;

import com.example.club_project.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/category")
@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    /**
     * id에 해당하는 동아리 카테고리를 반환한다.
     *
     * GET /api/category/:id
     */
    @GetMapping("/{id}")
    public CategoryDTO.Response getCategories(@PathVariable Long id) {
        return categoryService.getCategoryDto(id);
    }

    /**
     * 동아리 카테고리 목록을 반환한다.
     *
     * GET /api/category
     */
    @GetMapping
    public List<CategoryDTO.Response> getCategories() {
        return categoryService.getCategoryDtos();
    }

    /**
     * 동아리 카테고리 생성할 수 있다.
     *
     * POST /api/category
     */
    @PostMapping
    public CategoryDTO.Response registerCategory(@RequestBody CategoryDTO.RegisterRequest request) {
        return categoryService.registerCategory(request.getName(), request.getDescription());
    }

    /**
     * 동아리 카테고리 정보를 업데이트 할 수 있다.
     *
     * PUT /api/category/:id
     */
    @PutMapping("/{id}")
    public CategoryDTO.Response updateCategory(@PathVariable Long id, @RequestBody CategoryDTO.UpdateRequest request) {
        return categoryService.updateCategory(id, request.getName(), request.getDescription());
    }

    /**
     * 동아리 카테고리를 지울 수 있다.
     *
     * DELETE /api/category/:id
     */
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
