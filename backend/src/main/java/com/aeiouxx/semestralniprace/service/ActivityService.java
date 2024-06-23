package com.aeiouxx.semestralniprace.service;

import com.aeiouxx.semestralniprace.dto.ActivityRequest;
import com.aeiouxx.semestralniprace.dto.ActivityResponse;
import com.aeiouxx.semestralniprace.model.Activity;
import com.aeiouxx.semestralniprace.model.Category;
import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.repository.ActivityRepository;
import com.aeiouxx.semestralniprace.repository.CategoryRepository;
import com.aeiouxx.semestralniprace.repository.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final CategoryRepository categoryRepository;
    public Page<ActivityResponse> getForUser(Pageable pageable, Long userId, String filter) {
        return activityRepository.findActivitiesByUserId(pageable, userId, filter)
                .map(ActivityResponse::fromEntity);
    }
    @Transactional
    public Activity createActivity(ActivityRequest request, User user) {
        var activity = request.toEntity();
        activity.setUser(user);
        categoryRepository.findByUserIdAndName(user.getId(), request.getCategory())
                .ifPresentOrElse(activity::setCategory, () -> {
                    throw new NotFoundException(Category.class);
                });
        return activityRepository.save(activity);
    }

    @Transactional
    public Activity updateActivity(String oldName, ActivityRequest request, User user) {
        var updatedActivity = request.toEntity();
        var existingActivity = activityRepository.findByNameAndUserId(oldName, user.getId())
                .orElseThrow(() -> new NotFoundException(Activity.class));
        existingActivity.setName(updatedActivity.getName());
        existingActivity.setDescription(updatedActivity.getDescription());
        existingActivity.setCategory(updatedActivity.getCategory());
        return activityRepository.save(existingActivity);
    }

    @Transactional
    public void deleteByNameAndUserId(String activityName, User user) {
        activityRepository.deleteByNameAndUserId(activityName, user.getId());
    }
}
