package com.aeiouxx.semestralniprace.controller.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageUtils {
    public static PageRequest createPageRequest(int page, int size, String sortField, String sortOrder) {
        PageRequest pageable;
        if (sortField == null || sortOrder == null) {
            pageable = PageRequest.of(page, size);
        }
        else {
            Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
            pageable = PageRequest.of(page, size, sort);
        }
        return pageable;
    }
}
