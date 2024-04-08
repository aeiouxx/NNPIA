package com.aeiouxx.semestralniprace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping
    public ResponseEntity<String> testSecured() {
        return ResponseEntity.ok("I should not be accessible without a token");
    }
}
