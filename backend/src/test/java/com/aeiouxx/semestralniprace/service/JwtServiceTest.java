package com.aeiouxx.semestralniprace.service;

import com.aeiouxx.semestralniprace.model.Role;
import io.jsonwebtoken.Jwts;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtServiceTest {
    private final String TEST_USER = "TEST_USER";
    private final String TEST_PASSWORD = "TEST_PASSWORD";
    private final String secretKey = "D2ACFDDCD72E7015C9272BE77E7B35C1EE6077111F5B8302F1C8AA2B66B3B57B";
    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        userDetails = org.springframework.security.core.userdetails.User
                .withUsername(TEST_USER)
                .password(TEST_PASSWORD)
                .authorities(Role.USER.name())
                .build();
    }

    @Test
    void generateToken() {
        var token = jwtService.generateToken(userDetails);
        assert token != null;
    }

    @Test
    void testExtractUsername() {
        var token = jwtService.generateToken(userDetails);
        var username = jwtService.extractUsername(token);
        assert username.equals(TEST_USER);
    }

    @Test
    void testIsTokenValid() {
        String token = jwtService.generateToken(userDetails);
        assert (jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testIsTokenExpired() {
        Date beforeNow = new Date(System.currentTimeMillis() - 1000);
        String token = Jwts.builder()
                .setSubject(TEST_USER)
                .setExpiration(beforeNow)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secretKey)
                .compact();
        assert(jwtService.isTokenExpired(token));
    }
}
