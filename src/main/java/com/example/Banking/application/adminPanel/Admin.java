package com.example.Banking.application.adminPanel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins")
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

public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

}