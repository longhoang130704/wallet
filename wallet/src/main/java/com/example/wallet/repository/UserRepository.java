package com.example.wallet.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.wallet.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByusername(String username);

    Optional<User> findByRole(String role);
}
