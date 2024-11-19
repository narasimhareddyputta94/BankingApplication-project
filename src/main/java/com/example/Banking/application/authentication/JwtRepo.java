package com.example.Banking.application.authentication;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JwtRepo extends MongoRepository<JwtToken , String> {
    Optional<JwtToken> findByToken(String token);
}