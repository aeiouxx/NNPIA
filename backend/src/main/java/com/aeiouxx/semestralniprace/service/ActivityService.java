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
        var existingActivity = activityRepository.findByName(oldName)
                .orElseThrow(() -> new NotFoundException(Activity.class));
        existingActivity.setName(activity.getName());
        existingActivity.setDescription(activity.getDescription());
        existingActivity.setCategory(activity.getCategory());
        return activityRepository.save(existingActivity);
    }

    @Transactional
    public void deleteActivityByName(String name) {
        activityRepository.deleteByName(name);
    }

    public List<Activity> getActivitiesByUser(Long userId) {
        return activityRepository.findByUserId(userId);
    }

    public Activity getActivityById(Long activityId) {
        return activityRepository.findById(activityId).orElse(null);
    }

    @Transactional
    public Activity updateActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Transactional
    public void deleteActivity(Long activityId) {
        activityRepository.deleteById(activityId);
    }
}
