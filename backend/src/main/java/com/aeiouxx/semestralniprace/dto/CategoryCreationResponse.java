package com.aeiouxx.semestralniprace.dto;

import com.aeiouxx.semestralniprace.model.Category;
import lombok.Data;

@Data
public class CategoryCreationResponse {
    public static CategoryCreationResponse fromEntity(Category category) {
        CategoryCreationResponse categoryDTO = new CategoryCreationResponse();
        categoryDTO.setName(category.getName());
        categoryDTO.setUser(category.getUser().getUsername());
        return categoryDTO;
    }

    private String name;
    private String user;
}
