package com.aeiouxx.semestralniprace.dto;


import com.aeiouxx.semestralniprace.model.ActivityEntry;
import lombok.Data;

@Data
public class EntryResponse {
    public String activityName;
    public String startTime;
    public String endTime;

    public static EntryResponse fromEntity(ActivityEntry entry) {
        var response = new EntryResponse();
        response.activityName = entry.getActivity().getName();
        response.startTime = entry.getStartTime().toString();
        response.endTime = entry.getEndTime() == null ? null : entry.getEndTime().toString();
        return response;
    }
}
