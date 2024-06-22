package com.aeiouxx.semestralniprace.service;

import com.aeiouxx.semestralniprace.dto.CategorySummaryResponse;
import com.aeiouxx.semestralniprace.model.CategorySummary;
import com.aeiouxx.semestralniprace.repository.CategorySummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategorySummaryService {
    private final CategorySummaryRepository categorySummaryRepository;
    public Page<CategorySummaryResponse> getForUser(Pageable pageable, Long userId, String filter) {
        return categorySummaryRepository.findCategorySummariesByUserId(pageable, userId, filter)
                .map(cs -> CategorySummaryResponse.builder()
                        .name(cs.getName())
                        .activityCount(cs.getActivityCount())
                        .entryCount(cs.getEntryCount())
                        .build());
    }
}
