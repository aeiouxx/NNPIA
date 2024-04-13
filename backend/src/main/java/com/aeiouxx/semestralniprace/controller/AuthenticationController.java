package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.dto.AuthenticationRequest;
import com.aeiouxx.semestralniprace.dto.AuthenticationResponse;
import com.aeiouxx.semestralniprace.dto.RegistrationRequest;
import com.aeiouxx.semestralniprace.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authenticationService.register(registrationRequest));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }

    @PostMapping("/refresh-token")
    public void refreshToken() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
