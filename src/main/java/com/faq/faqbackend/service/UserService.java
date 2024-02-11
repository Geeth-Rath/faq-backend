package com.faq.faqbackend.service;
import com.faq.faqbackend.exception.ResourceNotFoundException;
import com.faq.faqbackend.exception.UserAlreadyExistsException;
import com.faq.faqbackend.model.User;
import com.faq.faqbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createUser(User newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new UserAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new UserAlreadyExistsException("Email '" + newUser.getEmail() + "' already exists");
        }
        return userRepository.save(newUser);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        return userRepository.save(user);
    }
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

}
