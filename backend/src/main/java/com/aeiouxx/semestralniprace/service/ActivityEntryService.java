package com.aeiouxx.semestralniprace.service;

import com.aeiouxx.semestralniprace.model.Activity;
import com.aeiouxx.semestralniprace.model.ActivityEntry;
import com.aeiouxx.semestralniprace.repository.ActivityEntryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityEntryService {
    private final ActivityEntryRepository activityEntryRepository;

    @Transactional
    public ActivityEntry createActivityEntry(ActivityEntry activity) {
        return activityEntryRepository.save(activity);
    }
    public List<ActivityEntry> getActivitiesByActivity(Long activityId) {
        return activityEntryRepository.findByActivityId(activityId);
    }
    @Transactional
    public ActivityEntry updateActivityEntry(ActivityEntry activity) {
        return activityEntryRepository.save(activity);
    }
    @Transactional
    public void deleteActivityEntry(Long activityEntryId) {
        activityEntryRepository.deleteById(activityEntryId);
    }
}
