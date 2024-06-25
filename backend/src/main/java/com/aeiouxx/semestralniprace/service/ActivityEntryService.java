package com.aeiouxx.semestralniprace.service;

import com.aeiouxx.semestralniprace.dto.ActivityEntryRequest;
import com.aeiouxx.semestralniprace.dto.ActivityEntryResponse;
import com.aeiouxx.semestralniprace.model.Activity;
import com.aeiouxx.semestralniprace.model.ActivityEntry;
import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.repository.ActivityEntryRepository;
import com.aeiouxx.semestralniprace.repository.ActivityRepository;
import com.aeiouxx.semestralniprace.repository.exception.NotFoundException;
import com.aeiouxx.semestralniprace.service.exception.OverlappingActivityEntryException;
import com.aeiouxx.semestralniprace.util.mapper.ActivityEntryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityEntryService {
    private final ActivityRepository activityRepository;
    private final ActivityEntryRepository activityEntryRepository;

    public Page<ActivityEntryResponse> getForUser(Pageable pageable, Long userId, String filter) {
        log.debug("Getting activity entries for user: {}, filter: {}", userId, filter);
        return activityEntryRepository.getForUser(pageable, userId, filter)
                .map(ActivityEntryMapper::toResponse);
    }

    @Transactional
    public ActivityEntry createActivityEntry(ActivityEntryRequest request, User user) {
        var activity = activityRepository.findByNameAndUserId(request.getActivity(), user.getId())
                .orElseThrow(() -> new NotFoundException(Activity.class));
        var entry = ActivityEntryMapper.fromRequest(request, user, activity);
        ThrowIfTimeslotOccupied(activity, entry);
        return activityEntryRepository.save(entry);
    }

    @Transactional
    public ActivityEntry updateActivityEntry(Long id, ActivityEntryRequest request, User user) {
        var existingEntry = activityEntryRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new NotFoundException(ActivityEntry.class));
        var activity = existingEntry.getActivity().getName().equals(request.getActivity())
                ? existingEntry.getActivity()
                : activityRepository.findByNameAndUserId(request.getActivity(), user.getId())
                        .orElseThrow(() -> new NotFoundException(Activity.class));
        var updatedEntry = ActivityEntryMapper.fromRequest(request, user, activity);
        ThrowIfTimeslotOccupied(activity, updatedEntry);
        updatedEntry.setId(id);
        return activityEntryRepository.save(updatedEntry);
    }

    private void ThrowIfTimeslotOccupied(Activity activity, ActivityEntry entry) {
        var overlap = activityEntryRepository.getPotentialOverlappingEntries(activity.getId(),
                entry.getId(),
                entry.getStartTime(),
                entry.getEndTime());
        if (!overlap.isEmpty()) {
            throw new OverlappingActivityEntryException("Activity entry for activity is overlapping with another entry");
        }
    }
    @Transactional
    public void deleteActivityEntry(Long id, User user) {
        activityEntryRepository.deleteByIdAndUserId(id, user.getId());
    }
}
