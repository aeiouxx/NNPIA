package com.aeiouxx.semestralniprace.service;

import com.aeiouxx.semestralniprace.model.Activity;
import com.aeiouxx.semestralniprace.repository.ActivityRepository;
import com.aeiouxx.semestralniprace.repository.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Transactional
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Transactional
    public Activity updateActivity(String oldName, Activity activity) {
        var existingActivity = activityRepository.findByNameAndUserId(oldName, activity.getUser().getId())
                .orElseThrow(() -> new NotFoundException(Activity.class));
        existingActivity.setName(activity.getName());
        existingActivity.setDescription(activity.getDescription());
        existingActivity.setCategory(activity.getCategory());
        return activityRepository.save(existingActivity);
    }

    @Transactional
    public void deleteActivity(Activity activity) {
        activityRepository.deleteByNameAndUserId(activity.getName(), activity.getUser().getId());
    }
}
