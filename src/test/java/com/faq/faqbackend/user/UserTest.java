package com.faq.faqbackend.user;

import com.faq.faqbackend.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserConstructor() {
        User user = new User(1L, "GeethR", "Geethma", "gee234@example.com", "1298password");
        assertEquals(1L, user.getId());
        assertEquals("GeethR", user.getUsername());
        assertEquals("Geethma", user.getName());
        assertEquals("gee234@example.com", user.getEmail());
        assertEquals("1298password", user.getPassword());
    }

}
