package com.aeiouxx.semestralniprace.repository;

import com.aeiouxx.semestralniprace.model.CategorySummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategorySummaryRepository extends JpaRepository<CategorySummary, String>
{
    @Query("SELECT cs FROM CategorySummary cs " +
            "WHERE cs.userId = :userId " +
            "AND (:filter IS NULL OR cs.name LIKE %:filter%)")
    Page<CategorySummary> findCategorySummariesByUserId(Pageable pageable,
                                                        @Param("userId") Long userId,
                                                        @Param("filter") String filter);
}
