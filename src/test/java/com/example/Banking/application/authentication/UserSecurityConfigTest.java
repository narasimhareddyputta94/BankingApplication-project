package com.example.Banking.application.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserSecurityConfigTest {

    @Autowired
    private UserSecurityConfig userSecurityConfig;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testPasswordEncoder() {
        String rawPassword = "password";
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        assertTrue(bCryptPasswordEncoder.matches(rawPassword, encodedPassword));
    }

}


