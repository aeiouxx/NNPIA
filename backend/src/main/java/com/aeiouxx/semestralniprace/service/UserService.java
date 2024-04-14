package com.aeiouxx.semestralniprace.service;

import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.repository.UserRepository;
import com.aeiouxx.semestralniprace.repository.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(User.class));
    }
}
