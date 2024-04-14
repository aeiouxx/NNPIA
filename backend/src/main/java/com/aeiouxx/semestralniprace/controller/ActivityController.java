package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.dto.ActivityRequest;
import com.aeiouxx.semestralniprace.dto.ActivityResponse;
import com.aeiouxx.semestralniprace.model.Activity;
import com.aeiouxx.semestralniprace.model.Category;
import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.repository.UserRepository;
import com.aeiouxx.semestralniprace.repository.exception.NotFoundException;
import com.aeiouxx.semestralniprace.service.ActivityService;
import com.aeiouxx.semestralniprace.service.CategoryService;
import com.aeiouxx.semestralniprace.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
@Slf4j
public class ActivityController {
    private final ActivityService activityService;
    private final CategoryService categoryService;

    // todo: Paged and filtered activities
    @GetMapping
    public void getActivities() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    @PostMapping
    public ResponseEntity<ActivityResponse> createActivity(@Valid @RequestBody ActivityRequest activityRequest,
                                                           @AuthenticationPrincipal User user) {
        log.info("Creating activity: {}, for user: {}", activityRequest, user.getUsername());
        var category = activityRequest.getCategoryName() != null
                ? FindCategoryOrThrow(activityRequest.getCategoryName())
                : null;
        var activity = activityRequest.toEntity();
        activity.setCategory(category);
        activity.setUser(user);
        var created = activityService.createActivity(activity);
        return ResponseEntity.ok(ActivityResponse.fromEntity(created));
    }
    @PutMapping("/{name}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable String name,
                                                           @Valid @RequestBody ActivityRequest activityRequest,
                                                           @AuthenticationPrincipal User user
    ) {
        log.info("Updating activity `{}`: {}, for user: {}", name, activityRequest, user.getUsername());
        var category = activityRequest.getCategoryName() != null
                ? FindCategoryOrThrow(activityRequest.getCategoryName())
                : null;
        var activity = activityRequest.toEntity();
        activity.setCategory(category);
        activity.setUser(user);
        var updated = activityService.updateActivity(name, activity);
        return ResponseEntity.ok(ActivityResponse.fromEntity(updated));
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteActivity(@PathVariable String name,
                               @AuthenticationPrincipal User user
     ) {
        log.info("Deleting activity `{}` for user: {}", name, user.getUsername());
        activityService.deleteActivityByName(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }

     private Category FindCategoryOrThrow(String categoryName) {
         return categoryService.findByNameForCurrentUser(categoryName)
                 .orElseThrow(() -> new NotFoundException(Category.class));
     }
}
