package com.example.Banking.application.adminPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminRepositoryTest {

    @Mock
    private AdminRepository adminRepository;

    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the Admin object
        admin = Admin.builder()
                .adminId(1L)
                .username("adminUser")
                .password("securePassword")
                .build();
    }

    @Test
    void testFindByUsername() {
        // Arrange
        when(adminRepository.findByUsername("adminUser")).thenReturn(admin);

        // Act
        Admin foundAdmin = adminRepository.findByUsername("adminUser");

        // Assert
        assertNotNull(foundAdmin);
        assertEquals("adminUser", foundAdmin.getUsername());
        verify(adminRepository, times(1)).findByUsername("adminUser");
    }

    @Test
    void testFindById() {
        // Arrange
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        // Act
        Optional<Admin> foundAdmin = adminRepository.findById(1L);

        // Assert
        assertTrue(foundAdmin.isPresent());
        assertEquals(1L, foundAdmin.get().getAdminId());
        verify(adminRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveAdmin() {
        // Arrange
        when(adminRepository.save(admin)).thenReturn(admin);

        // Act
        Admin savedAdmin = adminRepository.save(admin);

        // Assert
        assertNotNull(savedAdmin);
        assertEquals("adminUser", savedAdmin.getUsername());
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void testDeleteAdmin() {
        // Act
        adminRepository.delete(admin);

        // Assert
        verify(adminRepository, times(1)).delete(admin);
    }

    @Test
    void testUpdateAdmin() {
        // Arrange
        admin.setPassword("newSecurePassword");
        when(adminRepository.save(admin)).thenReturn(admin);

        // Act
        Admin updatedAdmin = adminRepository.save(admin);

        // Assert
        assertNotNull(updatedAdmin);
        assertEquals("newSecurePassword", updatedAdmin.getPassword());
        verify(adminRepository, times(1)).save(admin);
    }
}
