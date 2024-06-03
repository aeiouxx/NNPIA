package com.aeiouxx.semestralniprace.model;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
class CategorySummaryId implements Serializable {
    private String name;
    private Long userId;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategorySummaryId that = (CategorySummaryId) o;
        return Objects.equals(name, that.name) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userId);
    }
}
