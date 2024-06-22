package com.aeiouxx.semestralniprace.dto;

import com.aeiouxx.semestralniprace.model.Activity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivityRequest {
    @NotBlank(message = "Activity name cannot be empty")
    private String name;
    private String description;
    @NotBlank(message = "Activity must be assigned a category")
    private String categoryName;
    public Activity toEntity() {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        return activity;
    }
}
