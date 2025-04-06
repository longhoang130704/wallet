package com.example.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.service.EmailService;

@RestController
@RequestMapping("/mfa")
public class MfaController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        String otp = emailService.generateOtp(email);
        emailService.sendOtpEmail(email, otp);

        return ResponseEntity.ok().body("Otp is sent to yoru email. Please check!");
    }

    @PostMapping("/otp-verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam int otp) {
        if (emailService.verifyOtp(email, otp)) {
            return ResponseEntity.ok("OTP verified successfully!");
        }

        return ResponseEntity.badRequest().body("Invalid OTP!");
    }
}
