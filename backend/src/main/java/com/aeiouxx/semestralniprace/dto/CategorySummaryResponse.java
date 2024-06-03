package com.aeiouxx.semestralniprace.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategorySummaryResponse {
    private String name;
    private long totalActivities;
    private long totalEntries;
}