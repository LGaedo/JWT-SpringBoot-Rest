package com.example.bciusermicroservice.repository;

import com.example.bciusermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> getByToken(String token);
}

