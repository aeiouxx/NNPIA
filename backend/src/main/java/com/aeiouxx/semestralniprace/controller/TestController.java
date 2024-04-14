package com.aeiouxx.semestralniprace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.aeiouxx.semestralniprace.service.UserService;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final UserService userService;

    @GetMapping("/test")
    public ResponseEntity<UserDetails> checkSecurityContext() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userService.getUserByUsername(auth.getName());
        String s = "Hello, " + user.getUsername() + "your password is: " + user.getPassword();
        return ResponseEntity.ok().body(user);
    }
}
