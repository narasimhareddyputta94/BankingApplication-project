package com.example.Banking.application.emailNotifications;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmailNotificationServiceTest {

    @Mock
    private EmailNotificationRepository emailNotificationRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailNotificationService emailNotificationService;

    private EmailNotification emailNotification;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        emailNotification = new EmailNotification(
                "test@example.com",
                "Test Subject",
                "This is a test email content.",
                EmailStatus.SENT
        );
    }

    @Test
    public void testSendEmailNotification_Success() {
        // Set up the mock behavior for the repository save method
        when(emailNotificationRepository.save(any(EmailNotification.class))).thenReturn(emailNotification);

        // Test the success case for sending an email
        EmailNotification result = emailNotificationService.sendEmailNotification("test@example.com", "Test Subject", "This is a test email content.");

        // Verify the expected behavior
        assertNotNull(result);
        assertEquals("test@example.com", result.getRecipientEmail());
        assertEquals("Test Subject", result.getSubject());
        assertEquals("This is a test email content.", result.getContent());
        assertEquals(EmailStatus.SENT, result.getStatus());

        // Ensure the mailSender and repository are called as expected
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(emailNotificationRepository, times(1)).save(any(EmailNotification.class));
    }

    @Test
    public void testSendEmailNotification_Failure() {
        // Simulate an exception being thrown by the mailSender
        doThrow(new RuntimeException("Mail server error")).when(mailSender).send(any(SimpleMailMessage.class));

        // Set up the repository to save the failed email notification
        when(emailNotificationRepository.save(any(EmailNotification.class))).thenAnswer(invocation -> {
            EmailNotification savedNotification = invocation.getArgument(0);
            savedNotification.setStatus(EmailStatus.FAILED); // Manually set status to FAILED
            return savedNotification;
        });

        // Test the failure case for sending an email
        EmailNotification result = emailNotificationService.sendEmailNotification("test@example.com", "Test Subject", "This is a test email content.");

        // Verify the result's status and message content indicating the failure
        assertNotNull(result);
        assertEquals(EmailStatus.FAILED, result.getStatus());
        assertTrue(result.getContent().contains("Failed to send email due to: Mail server error"));

        // Verify that the mailSender and repository save were called
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(emailNotificationRepository, times(1)).save(any(EmailNotification.class));
    }

    @Test
    public void testSendTransactionNotification() {
        // Set up the repository save behavior
        when(emailNotificationRepository.save(any(EmailNotification.class))).thenReturn(emailNotification);

        // Invoke the sendTransactionNotification method
        emailNotificationService.sendTransactionNotification("test@example.com", "1234567890", "CREDIT", "SUCCESS", BigDecimal.valueOf(100.00));

        // Verify the mailSender and repository interactions
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(emailNotificationRepository, times(1)).save(any(EmailNotification.class));
    }
}
