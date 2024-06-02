package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.dto.AuthenticationRequest;
import com.aeiouxx.semestralniprace.dto.AuthenticationResponse;
import com.aeiouxx.semestralniprace.service.AuthenticationService;
import com.aeiouxx.semestralniprace.service.JwtService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        log.debug("Received registration request: {}", registrationRequest);
        var response = authenticationService.register(registrationRequest);
        log.debug("Returning response: {}", response);
        return ResponseEntity.ok(
                response
        );
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest) {
        log.debug("Received authentication request: {}", authRequest);
        var response = authenticationService.authenticate((authRequest));
        log.debug("Returning response: {}", response.getToken());
        return ResponseEntity.ok(
                response);
    }

    record TokenToValidate(String token) {}
    @PostMapping(value = "/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody TokenToValidate token) {
        try {
            var jwt = token.token;
            if (jwtService.isTokenValid(jwt,
                    userDetailsService.loadUserByUsername(
                            jwtService.extractUsername(jwt)
                    )
            )) {
                return ResponseEntity.ok("Token is valid");
            }
        } catch (Exception e) {
            log.atError()
                    .setCause(e)
                    .addArgument(token)
                    .log("Error parsing token: {}");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
    }

    @PostMapping("/refresh-token")
    public void refreshToken() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
