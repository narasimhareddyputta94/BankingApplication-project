package com.example.Banking.application.emailNotifications;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EmailNotificationTest {

    @Test
    public void testAllArgsConstructor() {
        LocalDateTime timestamp = LocalDateTime.now();
        EmailNotification emailNotification = new EmailNotification(
                1L,
                "test@example.com",
                "Test Subject",
                "This is a test email content.",
                EmailStatus.SENT,
                timestamp
        );

        assertEquals(1L, emailNotification.getId());
        assertEquals("test@example.com", emailNotification.getRecipientEmail());
        assertEquals("Test Subject", emailNotification.getSubject());
        assertEquals("This is a test email content.", emailNotification.getContent());
        assertEquals(EmailStatus.SENT, emailNotification.getStatus());
        assertEquals(timestamp, emailNotification.getTimestamp());
    }

    @Test
    public void testCustomConstructor() {
        EmailNotification emailNotification = new EmailNotification(
                "test@example.com",
                "Test Subject",
                "This is a test email content.",
                EmailStatus.SENT
        );

        assertNotNull(emailNotification);
        assertEquals("test@example.com", emailNotification.getRecipientEmail());
        assertEquals("Test Subject", emailNotification.getSubject());
        assertEquals("This is a test email content.", emailNotification.getContent());
        assertEquals(EmailStatus.SENT, emailNotification.getStatus());
        assertNotNull(emailNotification.getTimestamp());
    }

    @Test
    public void testSettersAndGetters() {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setId(2L);
        emailNotification.setRecipientEmail("test@example.com");
        emailNotification.setSubject("Subject");
        emailNotification.setContent("Content");
        emailNotification.setStatus(EmailStatus.FAILED);
        LocalDateTime timestamp = LocalDateTime.now();
        emailNotification.setTimestamp(timestamp);

        assertEquals(2L, emailNotification.getId());
        assertEquals("test@example.com", emailNotification.getRecipientEmail());
        assertEquals("Subject", emailNotification.getSubject());
        assertEquals("Content", emailNotification.getContent());
        assertEquals(EmailStatus.FAILED, emailNotification.getStatus());
        assertEquals(timestamp, emailNotification.getTimestamp());
    }
}
