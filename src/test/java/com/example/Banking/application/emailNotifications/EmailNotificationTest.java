package com.example.Banking.application.emailNotifications;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EmailNotificationTest {

    @Test
    void testEmailNotificationBuilder() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        EmailNotification emailNotification = EmailNotification.builder()
                .recipientEmail("test@example.com")
                .subject("Test Subject")
                .content("This is a test email.")
                .status(EmailStatus.SENT)
                .timestamp(timestamp)
                .build();

        // Act & Assert
        assertEquals("test@example.com", emailNotification.getRecipientEmail());
        assertEquals("Test Subject", emailNotification.getSubject());
        assertEquals("This is a test email.", emailNotification.getContent());
        assertEquals(EmailStatus.SENT, emailNotification.getStatus());
        assertEquals(timestamp, emailNotification.getTimestamp());
    }

    @Test
    void testEmailNotificationCustomConstructor() {
        // Arrange
        EmailNotification emailNotification = new EmailNotification(
                "test@example.com",
                "Test Subject",
                "This is a test email.",
                EmailStatus.SENT
        );

        // Act & Assert
        assertEquals("test@example.com", emailNotification.getRecipientEmail());
        assertEquals("Test Subject", emailNotification.getSubject());
        assertEquals("This is a test email.", emailNotification.getContent());
        assertEquals(EmailStatus.SENT, emailNotification.getStatus());
        assertNotNull(emailNotification.getTimestamp());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        EmailNotification emailNotification = new EmailNotification();
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        emailNotification.setRecipientEmail("recipient@example.com");
        emailNotification.setSubject("Subject");
        emailNotification.setContent("Email content");
        emailNotification.setStatus(EmailStatus.SENT);
        emailNotification.setTimestamp(timestamp);

        // Assert
        assertEquals("recipient@example.com", emailNotification.getRecipientEmail());
        assertEquals("Subject", emailNotification.getSubject());
        assertEquals("Email content", emailNotification.getContent());
        assertEquals(EmailStatus.SENT, emailNotification.getStatus());
        assertEquals(timestamp, emailNotification.getTimestamp());
    }
}
