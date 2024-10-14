package com.example.Banking.application.emailNotifications;
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

public class Notification {
    private Long notificationId;
    private Long accountId;
    private String emailAddress;
    private String subject;
    private String messageBody;
    private LocalDateTime sentDate;

}