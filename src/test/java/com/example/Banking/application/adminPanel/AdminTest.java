package com.example.Banking.application.adminPanel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    @Test
    public void testAdminConstructorAndGetters() {
        // Create an Admin object using constructor
        Admin admin = new Admin(1L, "adminUser", "password123");

        // Test if values are correctly set
        assertEquals(1L, admin.getAdminId());
        assertEquals("adminUser", admin.getUsername());
        assertEquals("password123", admin.getPassword());
    }

    @Test
    public void testAdminSetters() {
        // Create an Admin object and modify fields
        Admin admin = new Admin();
        admin.setAdminId(2L);
        admin.setUsername("newUser");
        admin.setPassword("newPassword");

        // Verify updated values
        assertEquals(2L, admin.getAdminId());
        assertEquals("newUser", admin.getUsername());
        assertEquals("newPassword", admin.getPassword());
    }

    @Test
    public void testAdminBuilder() {
        // Create an Admin object using the builder
        Admin admin = Admin.builder()
                .adminId(3L)
                .username("builderUser")
                .password("builderPassword")
                .build();

        // Verify the values
        assertEquals(3L, admin.getAdminId());
        assertEquals("builderUser", admin.getUsername());
        assertEquals("builderPassword", admin.getPassword());
    }
}
