package com.example.Banking.application.adminPanel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    @Test
    void testAdminConstructorAndGetters() {
        // Using the all-args constructor
        Admin admin = new Admin(1L, "adminUser", "securePassword");

        assertEquals(1L, admin.getAdminId());
        assertEquals("adminUser", admin.getUsername());
        assertEquals("securePassword", admin.getPassword());
    }

    @Test
    void testAdminSetters() {
        // Using no-args constructor and setters
        Admin admin = new Admin();
        admin.setAdminId(1L);
        admin.setUsername("adminUser");
        admin.setPassword("securePassword");

        assertEquals(1L, admin.getAdminId());
        assertEquals("adminUser", admin.getUsername());
        assertEquals("securePassword", admin.getPassword());
    }

    @Test
    void testAdminBuilder() {
        // Using Lombok builder
        Admin admin = Admin.builder()
                .adminId(1L)
                .username("adminUser")
                .password("securePassword")
                .build();

        assertEquals(1L, admin.getAdminId());
        assertEquals("adminUser", admin.getUsername());
        assertEquals("securePassword", admin.getPassword());
    }
}
