package com.aeiouxx.semestralniprace.repository;

import com.aeiouxx.semestralniprace.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUserId(Long userId);
    Optional<Activity> findByName(String name);
    Optional<Activity> findByNameAndUserId(String name, Long userId);

    void deleteByNameAndUserId(String name, Long userId);
}
