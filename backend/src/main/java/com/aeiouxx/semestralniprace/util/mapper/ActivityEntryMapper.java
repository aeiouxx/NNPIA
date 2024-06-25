package com.aeiouxx.semestralniprace.util.mapper;

import ch.qos.logback.classic.spi.EventArgUtil;
import com.aeiouxx.semestralniprace.dto.ActivityEntryRequest;
import com.aeiouxx.semestralniprace.dto.ActivityEntryResponse;
import com.aeiouxx.semestralniprace.model.Activity;
import com.aeiouxx.semestralniprace.model.ActivityEntry;
import com.aeiouxx.semestralniprace.model.User;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ActivityEntryMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    public static ActivityEntryResponse toResponse(ActivityEntry entity) {
        return new ActivityEntryResponse(
                entity.getId(),
                entity.getActivity().getName(),
                entity.getStartTime().format(FORMATTER),
                entity.getEndTime().format(FORMATTER)
        );
    }

    public static ActivityEntry fromRequest(ActivityEntryRequest request, User user, Activity activity) {
        var startTime = ZonedDateTime.parse(request.getStartTime(), FORMATTER);
        var endTime = ZonedDateTime.parse(request.getEndTime(), FORMATTER);
        log.debug("Start time: {}, end time: {}", startTime, endTime);
        return new ActivityEntry(
                null,
                activity,
                user,
                startTime,
                endTime
        );
    }

    public static boolean isEndTimeValid(ActivityEntryRequest request) {
        return LocalDateTime.parse(request.getEndTime(), FORMATTER).isAfter(LocalDateTime.parse(request.getStartTime(), FORMATTER));
    }
}
