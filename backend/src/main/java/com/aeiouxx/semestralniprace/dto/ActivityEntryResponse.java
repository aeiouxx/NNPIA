package com.aeiouxx.semestralniprace.dto;


import com.aeiouxx.semestralniprace.model.ActivityEntry;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivityEntryResponse {
    public Long id;
    public String activity;
    public String startTime;
    public String endTime;
}
