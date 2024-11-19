package com.example.banking.application.emailNotifications;

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
        when(emailNotificationRepository.save(any(EmailNotification.class))).thenReturn(emailNotification);

        EmailNotification result = emailNotificationService.sendEmailNotification("test@example.com", "Test Subject", "This is a test email content.");

        assertNotNull(result);
        assertEquals("test@example.com", result.getRecipientEmail());
        assertEquals("Test Subject", result.getSubject());
        assertEquals("This is a test email content.", result.getContent());
        assertEquals(EmailStatus.SENT, result.getStatus());

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(emailNotificationRepository, times(1)).save(any(EmailNotification.class));
    }

    @Test
    public void testSendEmailNotification_Failure() {
        doThrow(new RuntimeException("Mail server error")).when(mailSender).send(any(SimpleMailMessage.class));

        when(emailNotificationRepository.save(any(EmailNotification.class))).thenAnswer(invocation -> {
            EmailNotification savedNotification = invocation.getArgument(0);
            savedNotification.setStatus(EmailStatus.FAILED); // Manually set status to FAILED
            return savedNotification;
        });

        EmailNotification result = emailNotificationService.sendEmailNotification("test@example.com", "Test Subject", "This is a test email content.");

        assertNotNull(result);
        assertEquals(EmailStatus.FAILED, result.getStatus());
        assertTrue(result.getContent().contains("Failed to send email due to: Mail server error"));

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(emailNotificationRepository, times(1)).save(any(EmailNotification.class));
    }

    @Test
    public void testSendTransactionNotification() {
        when(emailNotificationRepository.save(any(EmailNotification.class))).thenReturn(emailNotification);

        emailNotificationService.sendTransactionNotification("test@example.com", "1234567890", "CREDIT", "SUCCESS", BigDecimal.valueOf(100.00));

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(emailNotificationRepository, times(1)).save(any(EmailNotification.class));
    }
}
