package com.aeiouxx.semestralniprace.service;

import com.aeiouxx.semestralniprace.dto.AuthenticationRequest;
import com.aeiouxx.semestralniprace.dto.AuthenticationResponse;
import com.aeiouxx.semestralniprace.dto.RegistrationRequest;
import com.aeiouxx.semestralniprace.model.Role;
import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        var user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest registrationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                registrationRequest.getUsername(),
                registrationRequest.getPassword()
        ));
        var user = userRepository.findByUsername(registrationRequest.getUsername()).orElseThrow();
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
