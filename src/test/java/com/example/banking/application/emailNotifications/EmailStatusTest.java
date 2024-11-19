package com.example.banking.application.emailNotifications;

import com.example.banking.application.emailNotifications.EmailStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailStatusTest {

    @Test
    public void testEnumValues() {
        assertEquals(EmailStatus.SENT, EmailStatus.valueOf("SENT"));
        assertEquals(EmailStatus.FAILED, EmailStatus.valueOf("FAILED"));
    }

    @Test
    public void testEnumToString() {
        assertEquals("SENT", EmailStatus.SENT.toString());
        assertEquals("FAILED", EmailStatus.FAILED.toString());
    }
}
