package com.example.userservice.Repository;

import com.example.userservice.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Token save(Token token);
    Optional<Token> findByValueAndDeleted(String token, boolean isDeleted);
    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String token, boolean isDeleted, Date expiryGreaterThan);
}
