package com.aeiouxx.semestralniprace.dto;

import com.aeiouxx.semestralniprace.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryResponse {
    public static CategoryResponse fromEntity(Category category) {
        return new CategoryResponse(category.getName());
    }
    public static List<CategoryResponse> fromEntities(List<Category> categories) {
        return categories.stream()
                .map(CategoryResponse::fromEntity)
                .toList();
    }
    private String name;
}
