package com.aeiouxx.semestralniprace.dto;

import com.aeiouxx.semestralniprace.model.ActivityEntry;
import com.aeiouxx.semestralniprace.util.mapper.ActivityEntryMapper;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ActivityEntryRequest {
    @NotBlank(message = "Activity name must be specified.")
    private String activity;
    @NotNull(message = "Start time must be specified.")
    private String startTime;
    @NotNull(message = "End time must be specified.")
    private String endTime;

    @AssertTrue(message = "End time must be after start time.")
    private boolean isEndTimeValid() {
        return ActivityEntryMapper.isEndTimeValid(this);
    }
}
