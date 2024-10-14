package com.example.Banking.application.emailNotifications;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EmailNotificationRepositoryTest {

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    private EmailNotification emailNotification;

    @BeforeEach
    void setUp() {
        emailNotification = EmailNotification.builder()
                .recipientEmail("test@example.com")
                .subject("Test Subject")
                .content("Test content")
                .status(EmailStatus.SENT)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    void testSaveEmailNotification() {
        // Act
        EmailNotification savedNotification = emailNotificationRepository.save(emailNotification);

        // Assert
        assertNotNull(savedNotification.getId());
        assertEquals("test@example.com", savedNotification.getRecipientEmail());
        assertEquals("Test Subject", savedNotification.getSubject());
        assertEquals("Test content", savedNotification.getContent());
        assertEquals(EmailStatus.SENT, savedNotification.getStatus());
    }

    @Test
    void testFindById() {
        // Arrange
        EmailNotification savedNotification = emailNotificationRepository.save(emailNotification);

        // Act
        Optional<EmailNotification> foundNotification = emailNotificationRepository.findById(savedNotification.getId());

        // Assert
        assertTrue(foundNotification.isPresent());
        assertEquals(savedNotification.getId(), foundNotification.get().getId());
        assertEquals("test@example.com", foundNotification.get().getRecipientEmail());
    }

    @Test
    void testUpdateEmailNotification() {
        // Arrange
        EmailNotification savedNotification = emailNotificationRepository.save(emailNotification);
        savedNotification.setStatus(EmailStatus.FAILED);

        // Act
        EmailNotification updatedNotification = emailNotificationRepository.save(savedNotification);

        // Assert
        assertEquals(EmailStatus.FAILED, updatedNotification.getStatus());
    }

    @Test
    void testDeleteEmailNotification() {
        // Arrange
        EmailNotification savedNotification = emailNotificationRepository.save(emailNotification);
        Long id = savedNotification.getId();

        // Act
        emailNotificationRepository.deleteById(id);
        Optional<EmailNotification> foundNotification = emailNotificationRepository.findById(id);

        // Assert
        assertFalse(foundNotification.isPresent());
    }
}
