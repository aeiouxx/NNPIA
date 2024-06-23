package com.aeiouxx.semestralniprace.dto;

import com.aeiouxx.semestralniprace.model.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivityResponse {
    private String name;
    private String description;
    private String category;

    public static ActivityResponse fromEntity(Activity activity) {
        return new ActivityResponse(
                activity.getName(),
                activity.getDescription(),
                activity.getCategory().getName()
        );
    }
}
