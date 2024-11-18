package com.example.Banking.application.emailNotifications;

import com.example.banking.application.emailNotifications.EmailNotification;
import com.example.banking.application.emailNotifications.EmailNotificationRepository;
import com.example.banking.application.emailNotifications.EmailStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EmailNotificationRepositoryTest {

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    @Test
    public void testSaveEmailNotification() {
        EmailNotification emailNotification = new EmailNotification(
                null,
                "test@example.com",
                "Test Subject",
                "This is a test email content.",
                EmailStatus.SENT,
                LocalDateTime.now()
        );

        EmailNotification savedEmail = emailNotificationRepository.save(emailNotification);

        assertNotNull(savedEmail);
        assertNotNull(savedEmail.getId());
        assertEquals("test@example.com", savedEmail.getRecipientEmail());
        assertEquals("Test Subject", savedEmail.getSubject());
        assertEquals("This is a test email content.", savedEmail.getContent());
        assertEquals(EmailStatus.SENT, savedEmail.getStatus());
    }

    @Test
    public void testFindById() {
        EmailNotification emailNotification = new EmailNotification(
                null,
                "test@example.com",
                "Test Subject",
                "This is a test email content.",
                EmailStatus.SENT,
                LocalDateTime.now()
        );

        EmailNotification savedEmail = emailNotificationRepository.save(emailNotification);
        EmailNotification foundEmail = emailNotificationRepository.findById(savedEmail.getId()).orElse(null);

        assertNotNull(foundEmail);
        assertEquals(savedEmail.getId(), foundEmail.getId());
    }

    @Test
    public void testDeleteEmailNotification() {
        EmailNotification emailNotification = new EmailNotification(
                null,
                "test@example.com",
                "Test Subject",
                "This is a test email content.",
                EmailStatus.SENT,
                LocalDateTime.now()
        );

        EmailNotification savedEmail = emailNotificationRepository.save(emailNotification);
        emailNotificationRepository.deleteById(savedEmail.getId());

        assertFalse(emailNotificationRepository.findById(savedEmail.getId()).isPresent());
    }
}
