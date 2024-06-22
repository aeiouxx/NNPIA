package com.aeiouxx.semestralniprace.dto;

import com.aeiouxx.semestralniprace.model.ActivityEntry;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class EntryRequest {

    @NotBlank(message = "Activity name must be specified.")
    private String activityName;
    @NotBlank(message = "Start time must be specified.")
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @AssertTrue(message = "End time must be after start time if specified.")
    private boolean isEndTimeValid() {
        return endTime == null || endTime.isAfter(startTime);
    }
    public ActivityEntry toEntity() {
        var entry = new ActivityEntry();
        entry.setStartTime(startTime);
        entry.setEndTime(endTime);
        return entry;
    }
}
