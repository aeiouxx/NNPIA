package com.aeiouxx.semestralniprace.repository;

import com.aeiouxx.semestralniprace.model.ActivityEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityEntryRepository extends JpaRepository<ActivityEntry, Long> {
    @Query("SELECT ae FROM ActivityEntry ae " +
            "WHERE ae.activity.user.id = :userId " +
            "AND (:filter = '' OR ae.activity.name = :filter)")
    Page<ActivityEntry> getForUser(Pageable pageable,
                                   @Param("userId") Long userId,
                                   @Param("filter") String filter);

    Optional<ActivityEntry> findByIdAndUserId(Long id, Long userId);
    void deleteByIdAndUserId(Long id, Long userId);

    @Query("SELECT ae FROM ActivityEntry ae " +
            "WHERE ae.activity.id = :activityId AND " +
            "((ae.startTime BETWEEN :start AND :end) OR " +
            "(ae.endTime BETWEEN :start AND :end))"
    )
    List<ActivityEntry> getPotentialOverlappingEntries(@Param("activityId") Long activityId,
                                                       @Param("start") ZonedDateTime start,
                                                       @Param("end") ZonedDateTime end);
}
