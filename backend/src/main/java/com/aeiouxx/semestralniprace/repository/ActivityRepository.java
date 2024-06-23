package com.aeiouxx.semestralniprace.repository;

import com.aeiouxx.semestralniprace.model.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("SELECT a FROM Activity a " +
            "WHERE a.user.id = :userId " +
            "AND (:filter IS NULL OR a.name LIKE %:filter%)")
    Page<Activity> findActivitiesByUserId(Pageable pageable,
                                          @Param("userId") Long userId,
                                          @Param("filter") String filter);
    List<Activity> findByUserId(Long userId);
    Optional<Activity> findByNameAndUserId(String name, Long userId);
    void deleteByNameAndUserId(String name, Long userId);
}
