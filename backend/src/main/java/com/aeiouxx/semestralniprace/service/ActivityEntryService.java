package com.aeiouxx.semestralniprace.service;

import com.aeiouxx.semestralniprace.model.Activity;
import com.aeiouxx.semestralniprace.model.ActivityEntry;
import com.aeiouxx.semestralniprace.repository.ActivityEntryRepository;
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
        var violations = validator.validate(activity);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (var violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(sb.toString());
        }
        return activityEntryRepository.save(activity);
    }
}
