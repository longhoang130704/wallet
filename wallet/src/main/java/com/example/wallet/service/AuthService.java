package com.example.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wallet.entity.User;
import com.example.wallet.repository.UserRepository;
import com.example.wallet.util.JwtUtil;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil; // Sử dụng jwtUtil đã tạo ở JwtUtil

    public String login(String username, String password) {

        User user = userRepository.findByusername(username).orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }
        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }
}
