package com.example.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wallet.entity.User;
import com.example.wallet.repository.UserRepository;

@Service
public class SystemService {
    private UserRepository userRepository;
    private EmailService emailService;

    @Autowired
    public SystemService(
            UserRepository userRepository,
            EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public Boolean verifyUser(String username, String password) {

        User foundUser = userRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!foundUser.getPassword().equals(password)) {
            return false;
        }

        return true;
    }

    public Boolean sendToEmail(String toEmail) {
        try {
            String otpCode = emailService.generateOtp(toEmail);
            emailService.sendOtpEmail(toEmail, otpCode);

            System.out.println(otpCode);
            return true;
        } catch (Exception e) {
            System.err.println("Error in send otp to email");
            return false;
        }
    }
}
