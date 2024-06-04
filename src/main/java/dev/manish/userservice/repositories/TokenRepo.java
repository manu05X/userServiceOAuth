package dev.manish.userservice.repositories;

import dev.manish.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository()
public interface TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndDeletedEquals(String value, boolean isDeleted);
}