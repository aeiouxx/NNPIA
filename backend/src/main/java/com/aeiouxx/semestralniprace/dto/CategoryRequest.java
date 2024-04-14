package com.aeiouxx.semestralniprace.dto;

import com.aeiouxx.semestralniprace.model.Category;
import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    public Category toEntity() {
        Category category = new Category();
        category.setName(name);
        return category;
    }
}
