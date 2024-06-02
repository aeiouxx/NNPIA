package com.aeiouxx.semestralniprace.repository;

import com.aeiouxx.semestralniprace.dto.CategorySummary;
import com.aeiouxx.semestralniprace.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT new com.aeiouxx.semestralniprace.dto.CategorySummary(c.name, COUNT(a) ,COUNT(ae)) " +
            "FROM Category c LEFT JOIN Activity a ON c.id = a.category.id " +
            "LEFT JOIN ActivityEntry ae ON a.id = ae.activity.id " +
            "WHERE c.user.id = :userId " +
            "AND (:filter IS NULL OR c.name LIKE %:filter%) " +
            "GROUP BY c.name")
    Page<CategorySummary> findCategorySummariesByUserId(Pageable pageable,
                                                        @Param("userId") Long userId,
                                                        @Param("filter") String filter);

    Page<Category> findByUserId(Pageable pageable, Long userId);
    Optional<Category> findByUserIdAndName(Long userId, String name);

    void deleteByNameAndUserId(String name, Long userId);
}
