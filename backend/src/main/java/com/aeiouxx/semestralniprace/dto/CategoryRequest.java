package com.aeiouxx.semestralniprace.dto;

import com.aeiouxx.semestralniprace.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "Category name cannot be empty.")
    private String name;
    public Category toEntity() {
        Category category = new Category();
        category.setName(name);
        return category;
    }
}
