package com.example.Banking.application.emailNotifications;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * During my research on Lombok, I discovered several powerful features that greatly simplify code,
 * including annotations such as @NoArgsConstructor, @AllArgsConstructor, and @Builder.
 * These annotations streamline the creation of constructors and builder patterns, making it easier
 * to build and maintain classes with less boilerplate code.
 */

public class EmailNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipient_email", nullable = false)
    private String recipientEmail;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EmailStatus status;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    // Custom constructor without ID and timestamp
    public EmailNotification(String recipientEmail, String subject, String content, EmailStatus status) {
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.content = content;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}