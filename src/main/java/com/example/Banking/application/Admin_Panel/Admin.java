package com.example.Banking.application.Admin_Panel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long adminId;
    private String username;
    private String password;

}