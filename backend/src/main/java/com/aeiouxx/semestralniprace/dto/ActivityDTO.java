package com.aeiouxx.semestralniprace.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ActivityDTO {
    @NotBlank(message = "Name must not be empty")
    private String name;
    @Size(max = 255, message = "Description must be at most 255 characters long")
    private String description;
    private String categoryName;
}
