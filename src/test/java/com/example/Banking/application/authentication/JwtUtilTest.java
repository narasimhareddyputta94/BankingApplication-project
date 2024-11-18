package com.example.Banking.application.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testGenerateToken() {
        User user = new User();
        user.setId(1L);
        user.setEmail("testuser@example.com");

        String token = jwtUtil.generateToken(user);
        assertNotNull(token);
        assertEquals("testuser@example.com", jwtUtil.extractEmail(token));
    }

    @Test
    public void testIsTokenValid() {
        User user = new User();
        user.setId(1L);
        user.setEmail("testuser@example.com");

        String token = jwtUtil.generateToken(user);
        assertTrue(jwtUtil.isTokenValid(token, user));
    }

    @Test
    public void testIsTokenExpired() {
        User user = new User();
        user.setId(1L);
        user.setEmail("testuser@example.com");

        String token = jwtUtil.generateToken(user);
        assertFalse(jwtUtil.isTokenExpired(token));
    }
}
