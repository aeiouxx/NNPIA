package com.aeiouxx.semestralniprace.dto;

import com.aeiouxx.semestralniprace.model.Category;
import lombok.Data;

@Data
public class CategoryResponse {
    public static CategoryResponse fromEntity(Category category) {
        CategoryResponse categoryDTO = new CategoryResponse();
        categoryDTO.setName(category.getName());
        categoryDTO.setUser(category.getUser().getUsername());
        return categoryDTO;
    }

    private String name;
    private String user;
}
