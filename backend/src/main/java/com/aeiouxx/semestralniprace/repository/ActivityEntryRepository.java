package com.aeiouxx.semestralniprace.repository;

import com.aeiouxx.semestralniprace.model.ActivityEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityEntryRepository extends JpaRepository<ActivityEntry, Long> {
    List<ActivityEntry> findByActivityId(Long activityId);
}
