package com.example.Banking.application.authentication;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "jwtTokens")
public class JwtToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String userId;

    @Column(nullable = false, unique = true)
    private String token;

    private boolean revoked;
    private boolean expired;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}