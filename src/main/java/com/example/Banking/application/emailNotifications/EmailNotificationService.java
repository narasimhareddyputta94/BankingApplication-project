package com.example.Banking.application.emailNotifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class EmailNotificationService {

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Transactional
    public EmailNotification sendEmailNotification(String recipientEmail, String subject, String content) {
        EmailNotification emailNotification = new EmailNotification(recipientEmail, subject, content, EmailStatus.SENT);

        // Create the email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(content);

        try {
            // Send the email
            mailSender.send(message);
        } catch (Exception e) {
            // Update status to FAILED and set failure reason
            emailNotification.setStatus(EmailStatus.FAILED);
            emailNotification.setContent("Failed to send email due to: " + e.getMessage());
        }

        // Save and return the emailNotification, even if it failed
        return emailNotificationRepository.save(emailNotification);
    }


    public void sendTransactionNotification(String recipientEmail, String accountNumber, String transactionType, String status, BigDecimal amount) {
        String subject = "Transaction " + status + ": " + transactionType;
        String content = "Dear customer, your " + transactionType + " transaction of $" + amount + " on account " + accountNumber + " was " + status + ".";

        sendEmailNotification(recipientEmail, subject, content);
    }
}
