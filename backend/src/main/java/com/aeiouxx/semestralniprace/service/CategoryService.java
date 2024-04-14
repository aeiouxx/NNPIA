package com.aeiouxx.semestralniprace.service;

import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.repository.CategoryRepository;
import com.aeiouxx.semestralniprace.repository.UserRepository;
import com.aeiouxx.semestralniprace.repository.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.aeiouxx.semestralniprace.model.Category;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<Category> getCategories() {
        return categoryRepository.findByUserId(getUserId());
    }
    public Optional<Category> findByNameForCurrentUser(String name) {
        return categoryRepository.findByUserIdAndName(getUserId(), name);
    }

    @Transactional
    public Category createCategory(Category category) {
        category.setUser(userRepository.findById(getUserId())
                .orElseThrow(() -> new NotFoundException(User.class)));
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(String oldName, Category category) {
        var existingCategory = categoryRepository.findByUserIdAndName(getUserId(), oldName)
                .orElseThrow(() -> new NotFoundException(Category.class));
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public void deleteCategoryByName(String categoryName)
    {
        categoryRepository.deleteByUserIdAndName(getUserId(), categoryName)
                 .orElseThrow(() -> new NotFoundException(Category.class));
    }

    private Long getUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(User.class))
                .getId();
    }
}
