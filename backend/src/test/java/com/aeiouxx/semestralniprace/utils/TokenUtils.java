package com.aeiouxx.semestralniprace.utils;


import com.aeiouxx.semestralniprace.model.Role;
import com.aeiouxx.semestralniprace.service.JwtService;
import org.springframework.security.core.userdetails.User;

public class TokenUtils {
    public static final String TEST_USERNAME = "TestUser";
    public static final String TEST_PASSWORD = "TestPassword";
    public static final Role TEST_ROLE = Role.USER;
    public static String generateTestToken(JwtService jwtService) {
        var details = User.builder()
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .roles(TEST_ROLE.name())
                .build();
        return jwtService.generateToken(details);
    }
}
