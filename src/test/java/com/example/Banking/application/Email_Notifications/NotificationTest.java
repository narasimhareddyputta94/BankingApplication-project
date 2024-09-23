package com.example.Banking.application.Email_Notifications;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {

    @Test
    public void testNotificationConstructorAndGetters() {
        // Create a LocalDateTime instance for the  date
        LocalDateTime sentDate = LocalDateTime.now();

        // Create a Notification object using the constructor
        Notification notification = new Notification(1L, 100L, "user@example.com",
                "Test Subject", "This is a test message.", sentDate);

        // Test if values are correctly set
        assertEquals(1L, notification.getNotificationId());
        assertEquals(100L, notification.getAccountId());
        assertEquals("user@example.com", notification.getEmailAddress());
        assertEquals("Test Subject", notification.getSubject());
        assertEquals("This is a test message.", notification.getMessageBody());
        assertEquals(sentDate, notification.getSentDate());
    }

    @Test
    public void testNotificationSetters() {
        // Create a LocalDateTime instance for the  date
        LocalDateTime sentDate = LocalDateTime.now();

        // Create a Notification object and modify fields
        Notification notification = new Notification();
        notification.setNotificationId(2L);
        notification.setAccountId(200L);
        notification.setEmailAddress("newuser@example.com");
        notification.setSubject("New Subject");
        notification.setMessageBody("New message body.");
        notification.setSentDate(sentDate);

        // Verify updated values
        assertEquals(2L, notification.getNotificationId());
        assertEquals(200L, notification.getAccountId());
        assertEquals("newuser@example.com", notification.getEmailAddress());
        assertEquals("New Subject", notification.getSubject());
        assertEquals("New message body.", notification.getMessageBody());
        assertEquals(sentDate, notification.getSentDate());
    }

    @Test
    public void testNotificationBuilder() {
        // Create a LocalDateTime instance for the date
        LocalDateTime sentDate = LocalDateTime.now();

        // Create a Notification object using the builder
        Notification notification = Notification.builder()
                .notificationId(3L)
                .accountId(300L)
                .emailAddress("builder@example.com")
                .subject("Builder Subject")
                .messageBody("Builder message body.")
                .sentDate(sentDate)
                .build();

        // Verify the values
        assertEquals(3L, notification.getNotificationId());
        assertEquals(300L, notification.getAccountId());
        assertEquals("builder@example.com", notification.getEmailAddress());
        assertEquals("Builder Subject", notification.getSubject());
        assertEquals("Builder message body.", notification.getMessageBody());
        assertEquals(sentDate, notification.getSentDate());
    }
}

