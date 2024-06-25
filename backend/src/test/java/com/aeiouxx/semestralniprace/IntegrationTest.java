package com.aeiouxx.semestralniprace;

import com.aeiouxx.semestralniprace.dto.AuthenticationRequest;
import com.aeiouxx.semestralniprace.model.Category;
import com.aeiouxx.semestralniprace.service.AuthenticationService;
import com.aeiouxx.semestralniprace.service.CategoryService;
import com.aeiouxx.semestralniprace.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTest {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @AfterAll
    void cleanUp() {
        var user = userService.getUserByUsername("TEST_USER");
        categoryService.deleteByNameAndUser("TEST_CATEGORY", user);
        userService.deleteByUsername("TEST_USER");
    }

    @Test
    void createCategoryForUser() {
        authenticationService.register(
                new AuthenticationRequest("TEST_USER", "TEST_PASSWORD")
        );
        var user = userService.getUserByUsername("TEST_USER");
        var category = new Category();
        category.setUser(user);
        category.setName("TEST_CATEGORY");
        categoryService.create(category, user);
        var categories = categoryService.findAllByUser(user);
        assert categories.size() == 1;
    }
}
