package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.dto.ActivityEntryRequest;
import com.aeiouxx.semestralniprace.dto.ActivityEntryResponse;
import com.aeiouxx.semestralniprace.model.ActivityEntry;
import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.repository.ActivityRepository;
import com.aeiouxx.semestralniprace.repository.exception.NotFoundException;
import com.aeiouxx.semestralniprace.service.ActivityEntryService;
import com.aeiouxx.semestralniprace.util.mapper.ActivityEntryMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aeiouxx.semestralniprace.controller.utils.PageUtils.createPageRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/activity-entries")
public class ActivityEntryController {
    private final ActivityEntryService activityEntryService;

    @GetMapping
    public Page<ActivityEntryResponse> getActivityEntries(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(required = false) String filter,
                                                          @RequestParam(required = false) String sortField,
                                                          @RequestParam(required = false) String sortOrder,
                                                          @AuthenticationPrincipal User user) {
        log.debug("Getting activity entries for user: {}", user);
        PageRequest pageable = createPageRequest(page, size, sortField, sortOrder);
        Page<ActivityEntryResponse> result = activityEntryService.getForUser(pageable, user.getId(), filter);
        log.debug("First page: {}", result.getContent());
        return result;
    }

    @PostMapping
    public ResponseEntity<ActivityEntryResponse> createEntry(@Valid @RequestBody ActivityEntryRequest request,
                                                             @AuthenticationPrincipal User user) {
        log.debug("Creating entry {} for user: {}", request, user);
        var created = activityEntryService.createActivityEntry(request, user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ActivityEntryMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityEntryResponse> updateEntry(@PathVariable Long id,
                                                             @Valid @RequestBody ActivityEntryRequest request,
                                                             @AuthenticationPrincipal User user)  {
        log.debug("Updating entry {} for user: {}", request, user);
        var updated = activityEntryService.updateActivityEntry(id, request, user);
        return ResponseEntity.ok(ActivityEntryMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long id,
                                         @AuthenticationPrincipal User user) {
        log.debug("Deleting entry with id: {} for user: {}", id, user);
        activityEntryService.deleteActivityEntry(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
