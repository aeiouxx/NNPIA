package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.dto.ActivityRequest;
import com.aeiouxx.semestralniprace.dto.ActivityResponse;
import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.service.ActivityService;
import com.aeiouxx.semestralniprace.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aeiouxx.semestralniprace.controller.utils.PageUtils.createPageRequest;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
@Slf4j
public class ActivityController {
    private final ActivityService activityService;
    private final CategoryService categoryService;

    @GetMapping
    public Page<ActivityResponse> getActivities(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(required = false) String filter,
                                                @RequestParam(required = false) String sortField,
                                                @RequestParam(required = false) String sortOrder,
                                                @AuthenticationPrincipal User user) {
        log.debug("Getting category summaries for user: {}", user);
        PageRequest pageable = createPageRequest(page, size, sortField, sortOrder);
        Page<ActivityResponse> result = activityService.getForUser(pageable, user.getId(), filter);
        log.debug("First page: {}", result.getContent());
        return result;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ActivityResponse>> getAllActivities(@AuthenticationPrincipal User user) {
        log.debug("Getting all activities for user: {}", user);
        List<ActivityResponse> result = activityService.getAllForUser(user.getId());
        log.debug("All activities: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ActivityResponse> createActivity(@Valid @RequestBody ActivityRequest activityRequest,
                                                           @AuthenticationPrincipal User user) {
        log.debug("Creating activity: {}, for user: {}", activityRequest, user.getUsername());
        var created = activityService.createActivity(activityRequest, user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ActivityResponse.fromEntity(created));
    }
    @PutMapping("/{name}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable String name,
                                                           @Valid @RequestBody ActivityRequest activityRequest,
                                                           @AuthenticationPrincipal User user
    ) {
        log.debug("Updating activity `{}`: {}, for user: {}", name, activityRequest, user.getUsername());
        var updated = activityService.updateActivity(name, activityRequest, user);
        return ResponseEntity.ok(ActivityResponse.fromEntity(updated));
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteActivity(@PathVariable String name,
                                            @AuthenticationPrincipal User user
     ) {
        log.info("Deleting activity `{}` for user: {}", name, user.getUsername());
        activityService.deleteByNameAndUserId(name, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
     }
}
