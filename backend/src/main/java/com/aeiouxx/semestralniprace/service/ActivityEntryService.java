package com.aeiouxx.semestralniprace.service;

import com.aeiouxx.semestralniprace.model.Activity;
import com.aeiouxx.semestralniprace.model.ActivityEntry;
import com.aeiouxx.semestralniprace.repository.ActivityEntryRepository;
import com.aeiouxx.semestralniprace.service.exception.OverlappingActivityEntryException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityEntryService {
    private final ActivityEntryRepository activityEntryRepository;
    private final Validator validator;

    @Transactional
    public ActivityEntry createActivityEntry(ActivityEntry activity) {
        return activityEntryRepository.save(activity);
    }

    @Transactional
    public ActivityEntry updateActivityEntry(Long id, ActivityEntry activity) {
        var existing = activityEntryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Activity entry not found"));
        activity.setId(existing.getId());
        return activityEntryRepository.save(activity);
    }

    private void validateNoOverlappingEntries(ActivityEntry newEntry) {
        List<ActivityEntry> overlapping = activityEntryRepository.findOverlappingEntries(
                newEntry.getActivity().getUser().getId(),
                newEntry.getStartTime(),
                newEntry.getEndTime()
        );
        if (!overlapping.isEmpty()) {
            throw new OverlappingActivityEntryException("Activity time slot overlaps with existing entry");
        }
    }
}
