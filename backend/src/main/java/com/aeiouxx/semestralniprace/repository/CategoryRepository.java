package com.aeiouxx.semestralniprace.repository;

import com.aeiouxx.semestralniprace.dto.CategorySummaryResponse;
import com.aeiouxx.semestralniprace.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findByUserId(Pageable pageable, Long userId);
    Optional<Category> findByUserIdAndName(Long userId, String name);
    void deleteByNameAndUserId(String name, Long userId);
}
