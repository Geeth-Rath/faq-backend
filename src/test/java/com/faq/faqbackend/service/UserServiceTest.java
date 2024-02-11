package com.faq.faqbackend.service;
import com.faq.faqbackend.exception.ResourceNotFoundException;
import com.faq.faqbackend.model.User;
import com.faq.faqbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Collections;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

//These test cases cover all CRUD operations of the UserService class:
//
//testCreateUser: Tests creating a new user.
//        testGetAllUsers: Tests retrieving all users.
//testGetUserById: Tests retrieving a user by ID.
//testUpdateUser: Tests updating a user.
//testDeleteUser: Tests deleting a user.
//testDeleteUser_NotFound: Tests deleting a non-existing user and expects a ResourceNotFoundException.



@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        User newUser = new User(null, "username", "Name", "email@example.com", "password");
        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(false);
        when(userRepository.save(newUser)).thenReturn(newUser);

        User savedUser = userService.createUser(newUser);
        assertNotNull(savedUser);
        assertEquals("username", savedUser.getUsername());
    }
    @Test
    public void testGetAllUsers() {
        // Mock the UserRepository to return a list of users
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));

        // Perform the getAllUsers operation
        Iterable<User> users = userService.getAllUsers();

        // Check if the returned list is not empty
        assertNotNull(users);
        assertTrue(users.iterator().hasNext());
    }

    @Test
    public void testGetUserById() {
        // Create a new user with ID 1
        User user = new User(1L, "username", "Name", "email@example.com", "password");

        // Mock the UserRepository to return an Optional containing the user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Perform the getUserById operation
        User retrievedUser = userService.getUserById(1L);

        // Check if the retrieved user is not null and has the correct ID
        assertNotNull(retrievedUser);
        assertEquals(1L, retrievedUser.getId());
    }

    @Test
    public void testUpdateUser() {

        User existingUser = new User(1L, "username", "Name", "email@example.com", "password");

        User updatedUser = new User(1L, "new_username", "New Name", "new_email@example.com", "new_password");


        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User resultUser = userService.updateUser(1L, updatedUser);

        assertNotNull(resultUser);
        assertEquals("new_username", resultUser.getUsername());
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(any());
    }

    @Test
    public void testDeleteUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
    }


}
