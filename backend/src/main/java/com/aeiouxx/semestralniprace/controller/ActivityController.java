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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @GetMapping
    public Page<ActivityResponse> getActivities(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(required = false) String filter,
                                                @RequestParam(defaultValue = "name") String sortField,
                                                @RequestParam(defaultValue = "asc") String sortOrder,
                                                @AuthenticationPrincipal User user) {
        log.debug("Getting category summaries for user: {}", user);
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<ActivityResponse> result = activityService.getForUser(pageable, user.getId(), filter);
        log.debug("First page: {}", result.getContent());
        return result;
    }
    @PostMapping
    public ResponseEntity<ActivityResponse> createActivity(@Valid @RequestBody ActivityRequest activityRequest,
                                                           @AuthenticationPrincipal User user) {
        log.info("Creating activity: {}, for user: {}", activityRequest, user.getUsername());
        var created = activityService.createActivity(activityRequest, user);
        return ResponseEntity.ok(ActivityResponse.fromEntity(created));
    }
    @PutMapping("/{name}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable String name,
                                                           @Valid @RequestBody ActivityRequest activityRequest,
                                                           @AuthenticationPrincipal User user
    ) {
        log.info("Updating activity `{}`: {}, for user: {}", name, activityRequest, user.getUsername());
        var updated = activityService.updateActivity(name, activityRequest, user);
        return ResponseEntity.ok(ActivityResponse.fromEntity(updated));
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteActivity(@PathVariable String name,
                               @AuthenticationPrincipal User user
     ) {
        log.info("Deleting activity `{}` for user: {}", name, user.getUsername());
        activityService.deleteByNameAndUserId(name, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }
}
