package com.example.Banking.application.adminPanel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AdminTest {

    @Test
    public void testAdminBuilder() {
        // Using builder to create an Admin object
        Admin admin = Admin.builder()
                .adminId(1L)
                .username("adminUser")
                .password("securePassword")
                .build();

        assertNotNull(admin);
        assertEquals(1L, admin.getAdminId());
        assertEquals("adminUser", admin.getUsername());
        assertEquals("securePassword", admin.getPassword());
    }

    @Test
    public void testAdminAllArgsConstructor() {
        // Using all-args constructor to create an Admin object
        Admin admin = new Admin(2L, "secondAdmin", "anotherPassword");

        assertNotNull(admin);
        assertEquals(2L, admin.getAdminId());
        assertEquals("secondAdmin", admin.getUsername());
        assertEquals("anotherPassword", admin.getPassword());
    }

    @Test
    public void testAdminNoArgsConstructor() {
        // Using no-args constructor to create an Admin object
        Admin admin = new Admin();

        assertNotNull(admin);
        assertNull(admin.getAdminId());
        assertNull(admin.getUsername());
        assertNull(admin.getPassword());
    }

    @Test
    public void testSettersAndGetters() {
        Admin admin = new Admin();
        admin.setAdminId(3L);
        admin.setUsername("newAdmin");
        admin.setPassword("newPassword");

        assertEquals(3L, admin.getAdminId());
        assertEquals("newAdmin", admin.getUsername());
        assertEquals("newPassword", admin.getPassword());
    }

    @Test
    public void testEqualsAndHashCode() {
        Admin admin1 = new Admin(1L, "adminUser", "password123");
        Admin admin2 = new Admin(1L, "adminUser", "password123");

        assertEquals(admin1, admin2);
        assertEquals(admin1.hashCode(), admin2.hashCode());
    }

    @Test
    public void testToString() {
        Admin admin = Admin.builder()
                .adminId(1L)
                .username("adminUser")
                .password("password123")
                .build();

        String expected = "Admin(adminId=1, username=adminUser, password=password123)";
        assertEquals(expected, admin.toString());
    }
}
