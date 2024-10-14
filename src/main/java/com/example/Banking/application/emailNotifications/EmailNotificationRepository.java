package com.example.Banking.application.emailNotifications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {
}
