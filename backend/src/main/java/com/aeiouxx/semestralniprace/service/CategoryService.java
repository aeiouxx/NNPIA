package com.aeiouxx.semestralniprace.service;

import com.aeiouxx.semestralniprace.dto.CategorySummaryResponse;
import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.repository.CategoryRepository;
import com.aeiouxx.semestralniprace.repository.UserRepository;
import com.aeiouxx.semestralniprace.repository.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.aeiouxx.semestralniprace.model.Category;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    // Advanced operations
    public Page<Category> getForUser(Pageable pageable, User user) {
        return categoryRepository.findByUserId(pageable, user.getId());
    }
    public Optional<Category> findByNameForCurrentUser(String name) {
        return categoryRepository.findByUserIdAndName(getUserId(), name);
    }
    // < Advanced operations
    // > Basic CRUD operations
    @Transactional
    public Category create(Category category, User user) {
        category.setUser(user);
        return categoryRepository.save(category);
    }
    @Transactional
    public Category update(String oldName, Category category) {
        var existingCategory = categoryRepository.findByUserIdAndName(getUserId(), oldName)
                .orElseThrow(() -> new NotFoundException(Category.class));
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }
    @Transactional
    public void deleteByNameAndUser(String name, User user) {
        categoryRepository.deleteByNameAndUserId(name, user.getId());
    }
    // < Basic CRUD operations
    // todo: remove not needed
    private Long getUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(User.class))
                .getId();
    }
}
