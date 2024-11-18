package com.example.Banking.application.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
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

        return token;
    }


    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, User user) {
        final String email = extractEmail(token);
        return (email.equals(user.getEmail()) && !isTokenExpired(token) && !isTokenRevoked(token));
    }

    boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    private boolean isTokenRevoked(String token) {
        JwtToken jwtToken = jwtRepo.findByToken(token).orElse(null);
        return jwtToken != null && jwtToken.isRevoked();
    }

    public void revokeToken(String token) {
        JwtToken jwtToken = jwtRepo.findByToken(token).orElse(null);
        if (jwtToken != null) {
            jwtToken.setRevoked(true);
            jwtRepo.save(jwtToken);

        }
    }
}
