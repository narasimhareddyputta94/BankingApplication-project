package com.example.Banking.application.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class JwtUtil {

    private final JwtRepo jwtRepo;
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public JwtUtil(JwtRepo jwtRepo) {
        this.jwtRepo = jwtRepo;
    }

    public String generateToken(User user) {
        log.traceEntry("Entering generateToken for user: {}", user.getEmail());

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        JwtToken jwtToken = new JwtToken();
        jwtToken.setUserId(String.valueOf(user.getId()));
        jwtToken.setToken(token);
        jwtToken.setCreatedAt(LocalDateTime.now());
        jwtToken.setExpiresAt(LocalDateTime.now().plusHours(10));
        jwtToken.setExpired(false);
        jwtToken.setRevoked(false);

        jwtRepo.save(jwtToken);

        log.traceExit("Exiting generateToken with token: {}", token);
        return token;
    }

    public String extractEmail(String token) {
        log.traceEntry("Entering extractEmail for token");
        String email = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        log.traceExit("Exiting extractEmail with email: {}", email);
        return email;
    }

    public boolean isTokenValid(String token, User user) {
        log.traceEntry("Entering isTokenValid for token and user: {}", user.getEmail());
        final String email = extractEmail(token);
        boolean isValid = email.equals(user.getEmail()) && !isTokenExpired(token) && !isTokenRevoked(token);
        log.traceExit("Exiting isTokenValid with isValid: {}", isValid);
        return isValid;
    }

    boolean isTokenExpired(String token) {
        log.traceEntry("Entering isTokenExpired for token");
        boolean expired = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
        log.traceExit("Exiting isTokenExpired with expired: {}", expired);
        return expired;
    }

    private boolean isTokenRevoked(String token) {
        log.traceEntry("Entering isTokenRevoked for token");
        JwtToken jwtToken = jwtRepo.findByToken(token).orElse(null);
        boolean revoked = jwtToken != null && jwtToken.isRevoked();
        log.traceExit("Exiting isTokenRevoked with revoked: {}", revoked);
        return revoked;
    }

    public void revokeToken(String token) {
        log.traceEntry("Entering revokeToken for token: {}", token);
        JwtToken jwtToken = jwtRepo.findByToken(token).orElse(null);
        if (jwtToken != null) {
            jwtToken.setRevoked(true);
            jwtRepo.save(jwtToken);
        }
        log.traceExit("Exiting revokeToken");
    }
}