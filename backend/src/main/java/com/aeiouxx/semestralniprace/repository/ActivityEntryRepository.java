package com.aeiouxx.semestralniprace.repository;

import com.aeiouxx.semestralniprace.model.ActivityEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityEntryRepository extends JpaRepository<ActivityEntry, Long> {
    List<ActivityEntry> findByActivityId(Long activityId);



    @Query("SELECT ae FROM ActivityEntry ae WHERE ae.activity.user.id = :userId "
            + "AND ((:startTime BETWEEN ae.startTime AND ae.endTime) "
            + "OR (:endTime BETWEEN ae.startTime AND ae.endTime) "
            + "OR (ae.startTime BETWEEN :startTime AND :endTime) "
            + "OR (ae.endTime BETWEEN :startTime and :endTime))")
    List<ActivityEntry> findOverlappingEntries(@Param("userId") Long userId,
                                               @Param("startTime") LocalDateTime startTime,
                                               @Param("endTime") LocalDateTime endTime);
}
