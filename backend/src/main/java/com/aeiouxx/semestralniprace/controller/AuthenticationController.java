package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.dto.AuthenticationRequest;
import com.aeiouxx.semestralniprace.dto.AuthenticationResponse;
import com.aeiouxx.semestralniprace.service.AuthenticationService;
import com.aeiouxx.semestralniprace.service.JwtService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest registrationRequest) {
        return ResponseEntity.ok(authenticationService.register(registrationRequest));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody String token) {
        try {
            if (jwtService.isTokenValid(token,
                    userDetailsService.loadUserByUsername(
                            jwtService.extractUsername(token)
                    )
            )) {
                return ResponseEntity.ok("Token is valid");
            }
        } catch (Exception e) {
            log.error("Error parsing token", e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
    }

    @PostMapping("/refresh-token")
    public void refreshToken() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
