package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.dto.EntryRequest;
import com.aeiouxx.semestralniprace.dto.EntryResponse;
import com.aeiouxx.semestralniprace.model.ActivityEntry;
import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.repository.ActivityRepository;
import com.aeiouxx.semestralniprace.repository.exception.NotFoundException;
import com.aeiouxx.semestralniprace.service.ActivityEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/activities/{name}/entries")
public class ActivityEntryController {
    private final ActivityRepository activityRepository;
    private final ActivityEntryService activityEntryService;

    @GetMapping
    public ResponseEntity<List<ActivityEntry>> getEntries(@PathVariable("name") String activity,
                                                          @AuthenticationPrincipal User user) {
        log.info("Getting entries for activity `{}`", activity);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PostMapping
    public ResponseEntity<EntryResponse> createEntry(@PathVariable("name") String name,
                                                     @Valid @RequestBody EntryRequest request,
                                                     @AuthenticationPrincipal User user) {
        log.info("Creating entry for activity `{}`: `{}`", name, request);
        var activity = activityRepository.findByNameAndUserId(name, user.getId())
                .orElseThrow(() -> NotFoundException.of(ActivityEntry.class));
        var entry = request.toEntity();
        entry.setActivity(activity);
        var result = activityEntryService.createActivityEntry(entry);
        return ResponseEntity.ok().body(EntryResponse.fromEntity(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntryResponse> updateEntry(@PathVariable("name") String name,
                                                     @PathVariable("id") Long id,
                                                     @Valid @RequestBody EntryRequest entry,
                                                     @AuthenticationPrincipal User user) {
        log.info("Updating entry `{}` for activity `{}`: `{}`", id, name, entry);
        var activity = activityRepository.findByNameAndUserId(name, user.getId())
                .orElseThrow(() -> NotFoundException.of(ActivityEntry.class));
        var updated = activityEntryService.updateActivityEntry(id, entry.toEntity());
        return ResponseEntity.ok().body(EntryResponse.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable("name") String activity,
                                         @PathVariable("id") Long id,
                                         @AuthenticationPrincipal User user) {
        log.info("Deleting entry `{}` for activity `{}`", id, activity);
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
