package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.dto.CategoryRequest;
import com.aeiouxx.semestralniprace.dto.CategoryResponse;
import com.aeiouxx.semestralniprace.dto.CategorySummary;
import com.aeiouxx.semestralniprace.model.Category;
import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.service.CategoryService;
import com.aeiouxx.semestralniprace.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @GetMapping("/summary")
    public Page<CategorySummary> getCategorySummaries(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(required = false) String filter,
                                                      @AuthenticationPrincipal User user) {
        log.debug("Getting category summaries for user: {}", user);
        var pageable = PageRequest.of(page, size);
        return categoryService.getSummariesForUser(pageable, user, filter);
    }



    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryDTO,
                                                           @AuthenticationPrincipal User user) {
        log.debug("Creating category for user: {}", user);
        var category = categoryDTO.toEntity();
        var result = categoryService.create(category);
        var response = CategoryResponse.fromEntity(result);
        return new ResponseEntity<>(response,
                HttpStatus.CREATED);
    }

    @PutMapping("/{name}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable String name,
                                                           @Valid @RequestBody CategoryRequest categoryDTO,
                                                           @AuthenticationPrincipal User user) {
        log.debug("Updating category for user: {}", user);
        var category = categoryDTO.toEntity();
        var result = categoryService.update(name, category);
        return new ResponseEntity<>(CategoryResponse.fromEntity(result),
                HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteCategory(@PathVariable String name,
                                            @AuthenticationPrincipal User user) {
        log.debug("Deleting category for user: {}", user);
        categoryService.deleteByNameAndUser(name, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
