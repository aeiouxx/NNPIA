package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.dto.CategoryRequest;
import com.aeiouxx.semestralniprace.dto.CategoryResponse;
import com.aeiouxx.semestralniprace.model.Category;
import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.service.CategoryService;
import com.aeiouxx.semestralniprace.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryDTO,
                                                           @AuthenticationPrincipal User user) {
        log.info("Creating category for user: {}", user);
        var category = categoryDTO.toEntity();
        var result = categoryService.createCategory(category);
        var response = CategoryResponse.fromEntity(result);
        return new ResponseEntity<>(response,
                HttpStatus.CREATED);
    }

    @PutMapping("/{name}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable String name,
                                                           @Valid @RequestBody CategoryRequest categoryDTO,
                                                           @AuthenticationPrincipal User user) {
        log.info("Updating category for user: {}", user);
        var category = categoryDTO.toEntity();
        var result = categoryService.updateCategory(name, category);
        return new ResponseEntity<>(CategoryResponse.fromEntity(result),
                HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteCategory(@PathVariable String name,
                                            @AuthenticationPrincipal User user) {
        log.info("Deleting category for user: {}", user);
        categoryService.deleteCategoryByName(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
