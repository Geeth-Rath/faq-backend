package com.faq.faqbackend.controller;

import com.faq.faqbackend.exception.ResourceNotFoundException;
import com.faq.faqbackend.model.User;
import com.faq.faqbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//These test cases cover the remaining CRUD operations of the UserController class:
//
//testGetAllUsers: Tests retrieving all users.
//testGetUserById: Tests retrieving a user by ID.
//testUpdateUser: Tests updating a user.
//testDeleteUser: Tests deleting a user.
//testDeleteUser_NotFound: Tests deleting a non-existing user and expects a ResourceNotFoundException.

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testAddUser() throws Exception {
        String requestBody = "{\"username\": \"username\", \"name\": \"Name\", \"email\": \"email@example.com\", \"password\": \"password\"}";
        when(userService.createUser(any(User.class))).thenReturn(new User(1L, "username", "Name", "email@example.com", "password"));

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("User created successfully")));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(new User()));

        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(new User(1L, "username", "Name", "email@example.com", "password"));

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }



    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User deleted successfully")));
    }

    @Test
    public void testDeleteUser_NotFound() throws Exception {
        doThrow(ResourceNotFoundException.class).when(userService).deleteUser(1L);

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Resource not found")));
    }
}
