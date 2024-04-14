package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.repository.ActivityEntryRepository;
import com.aeiouxx.semestralniprace.service.ActivityEntryService;
import com.aeiouxx.semestralniprace.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/activities/{slug}/entries")
public class ActivityEntryController {
    private final ActivityService activityService;
    private final ActivityEntryService activityEntryService;


    @PostMapping
    public ResponseEntity<?> createEntry(
            @PathVariable("slug") String activity,
            @RequestBody String entry,
            @AuthenticationPrincipal User user)
    {
        log.info("Creating entry for activity `{}`: `{}`", activity, entry);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntry(
            @PathVariable("slug") String activity,
            @PathVariable("id") Long id,
            @RequestBody String entry,
            @AuthenticationPrincipal User user)
    {
        log.info("Updating entry `{}` for activity `{}`: `{}`", id, activity, entry);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntry(
            @PathVariable("slug") String activity,
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user)
    {
        log.info("Deleting entry `{}` for activity `{}`", id, activity);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping
    public ResponseEntity<?> getEntries(
            @PathVariable("slug") String activity,
            @AuthenticationPrincipal User user)
    {
        log.info("Getting entries for activity `{}`", activity);
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
