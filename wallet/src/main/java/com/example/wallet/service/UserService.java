package com.example.wallet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.wallet.entity.User;
import com.example.wallet.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CachePut(value = "user", key = "#user.username")
    public User createUser(User user) {
        user.setRole("USER");
        User savedUser = userRepository.save(user);
        System.out.println(savedUser);
        return savedUser;
    }

    @PostConstruct
    public User createAdmin() {

        Optional<User> existingAdmin = userRepository.findByRole("ADMIN");

        if (existingAdmin.isPresent()) {
            System.out.println("Admin already exists.");
            System.out.println("username: 9999999999");
            System.out.println("password: 9999999999");
            return existingAdmin.get();
        }

        User admin = new User();
        admin.setUsername("9999999999");
        admin.setPassword("9999999999");
        admin.setRole("ADMIN");

        System.out.println("Admin is created");
        System.out.println("username: 9999999999");
        System.out.println("password: 9999999999");

        return userRepository.save(admin);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Cacheable(value = "user", key = "#username", unless = "#result == null")
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByusername(username);
    }

    public Optional<User> updateUser(String username, User updatedUser) {
        return userRepository.findByusername(username).map(existingUser -> {
            existingUser.setPassword(updatedUser.getPassword());
            return userRepository.save(existingUser);
        });
    }

    @CachePut(value = "user", key = "#username")
    public boolean deleteUser(String username) {
        Optional<User> user = userRepository.findByusername(username);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }
}
