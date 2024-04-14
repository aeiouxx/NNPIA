package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.dto.CategoryCreationRequest;
import com.aeiouxx.semestralniprace.dto.CategoryCreationResponse;
import com.aeiouxx.semestralniprace.model.Category;
import com.aeiouxx.semestralniprace.service.CategoryService;
import com.aeiouxx.semestralniprace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;
    @GetMapping
    public Iterable<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping
    public CategoryCreationResponse createCategory(@RequestBody CategoryCreationRequest categoryDTO) {
        var category = categoryDTO.toEntity();
        var result = categoryService.createCategory(category);
        var response = CategoryCreationResponse.fromEntity(result);
        return response;
    }
}
