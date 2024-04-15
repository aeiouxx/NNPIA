package com.aeiouxx.semestralniprace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "activity_entries")
public class ActivityEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;
    @Column(nullable = false)
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
