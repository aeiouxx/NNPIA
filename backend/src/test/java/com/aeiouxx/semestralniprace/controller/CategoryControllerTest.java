package com.aeiouxx.semestralniprace.controller;

import com.aeiouxx.semestralniprace.config.SecurityConfiguration;
import com.aeiouxx.semestralniprace.model.Category;
import com.aeiouxx.semestralniprace.model.Role;
import com.aeiouxx.semestralniprace.model.User;
import com.aeiouxx.semestralniprace.security.JwtAuthenticationFilter;
import com.aeiouxx.semestralniprace.service.CategoryService;
import com.aeiouxx.semestralniprace.service.CategorySummaryService;
import com.aeiouxx.semestralniprace.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import(SecurityConfiguration.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private CategorySummaryService categorySummaryService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private JwtService jwtService;
    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @InjectMocks
    private CategoryController categoryController;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private User user;
    private UserDetails userDetails;
    private Category category;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .addFilters(new JwtAuthenticationFilter(jwtService, userDetailsService))
                .build();

        user = new User();
        user.setId(1L);
        user.setUsername("pepa");
        user.setPassword("password");
        user.setRole(Role.USER);

        category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setUser(user);

        userDetails = org.springframework.security.core.userdetails.User
                .withUsername("pepa")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    @Test
    @WithMockUser(username = "pepa", password ="password", roles = {"USER"})
    public void testGetCategories() throws Exception {
        when(jwtService.extractUsername(anyString())).thenReturn("pepa");
        when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(true);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(categoryService.findAllByUser(eq(user))).thenReturn(List.of(category));
        mockMvc.perform(get("/categories")
                        .header("Authorization", "Bearer " + "dummy-jwt-token")
                        .with(user(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(category.getName()));
    }

    @Test
    @WithMockUser(username = "pepa", password ="password", roles = {"USER"})
    public void testDeleteCategories() throws Exception {
        when(categoryService.findAllByUser(eq(user))).thenReturn(List.of(category));
        when(jwtService.extractUsername(anyString())).thenReturn("pepa");
        when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(true);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        doNothing().when(categoryService).deleteByNameAndUser(eq("Test"), eq(user));
        mockMvc.perform(delete("/categories/Test")
                        .header("Authorization", "Bearer " + "dummy-jwt-token")
                        .with(user(user)))
                .andExpect(status().isNoContent());
    }
}
