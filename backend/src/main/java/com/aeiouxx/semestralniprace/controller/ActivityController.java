package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.model.Activity;
import com.aeiouxx.semestralniprace.service.ActivityService;
import com.aeiouxx.semestralniprace.service.UserService;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    private final UserService userService;
}
