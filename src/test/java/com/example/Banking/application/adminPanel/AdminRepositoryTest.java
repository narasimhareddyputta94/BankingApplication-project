package com.example.Banking.application.adminPanel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void testFindByUsername_WhenUsernameExists() {
        Admin admin = Admin.builder()
                .username("admin1")
                .password("password1")
                .build();
        adminRepository.save(admin);

        Admin foundAdmin = adminRepository.findByUsername("admin1");
        assertNotNull(foundAdmin);
    }

    @Test
    public void testFindByUsername_WhenUsernameDoesNotExist() {
        Admin foundAdmin = adminRepository.findByUsername("nonexistent");
        assertNull(foundAdmin);
    }

    @Test
    public void testFindByUsername_WhenUsernameIsNull() {
        Admin foundAdmin = adminRepository.findByUsername(null);
        assertNull(foundAdmin);
    }
}
