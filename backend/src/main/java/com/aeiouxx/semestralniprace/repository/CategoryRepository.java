package com.aeiouxx.semestralniprace.repository;

import com.aeiouxx.semestralniprace.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserId(Long userId);
    Optional<Category> findByUserIdAndId(Long userId, Long categoryId);
    Optional<Category> findByUserIdAndName(Long userId, String name);
    Optional<Category> deleteByUserIdAndName(Long userId, String name);
}
