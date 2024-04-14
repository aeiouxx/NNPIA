package com.aeiouxx.semestralniprace.dto;

import com.aeiouxx.semestralniprace.model.Activity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivityRequest {
    private String name;
    private String description;
    private String categoryName;
    public Activity toEntity() {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        return activity;
    }
}
