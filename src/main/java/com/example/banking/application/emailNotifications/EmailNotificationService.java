package com.example.banking.application.emailNotifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.banking.application.emailNotifications.EmailNotification;
import com.example.banking.application.emailNotifications.EmailStatus;

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

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            emailNotification.setStatus(EmailStatus.FAILED);
            emailNotification.setContent("Failed to send email due to: " + e.getMessage());
        }

        return emailNotificationRepository.save(emailNotification);
    }


    public void sendTransactionNotification(String recipientEmail, String accountNumber, String transactionType, String status, BigDecimal amount) {
        String subject = "Transaction " + status + ": " + transactionType;
        String content = "Dear customer, your " + transactionType + " transaction of $" + amount + " on account " + accountNumber + " was " + status + ".";

        sendEmailNotification(recipientEmail, subject, content);
    }
}
