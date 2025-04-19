package com.example.wallet.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    private final Map<String, Integer> otpStorage = new HashMap<>();
    private final Map<String, Integer> changePassword = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // _______________________ PROCESS PAYMENT _______________________
    // generate otp and put to otp storage
    public String generateOtp(String email) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpStorage.put(email, otp);

        // delete email, otp after 5 minute
        scheduler.schedule(() -> otpStorage.remove(email), 5, TimeUnit.MINUTES);

        return String.valueOf(otp);
    }

    // send otp to process payment
    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("long130720042@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Process payment, Your OTP is: " + otp);

        mailSender.send(message);
        System.out.println("OTP Sent to: " + toEmail);
    }

    public boolean verifyOtp(String email, int userOtp) {
        System.out.println(otpStorage);
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(userOtp);
    }
    // _____________________________________________________________________________________

    // _______________________ CHANGE PASSWORD _____________________________
    // generate otp and put to change password
    public String generateOtpChangePassword(String email) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);

        // put otp to hash map
        changePassword.put(email, otp);

        // delete email, otp after 5 minute
        scheduler.schedule(() -> changePassword.remove(email), 5, TimeUnit.MINUTES);

        return String.valueOf(otp);
    }

    public void sendOtpEmailChangePassword(String toEmail, String otp) {

        // create message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("long130720042@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Change password, Your OTP is: " + otp);

        // send to email
        mailSender.send(message);
        System.out.println("OTP Sent to: " + toEmail);
    }

    public boolean verifyChangePassword(String email, int userOtp) {
        System.out.println(changePassword);
        return otpStorage.containsKey(email) && changePassword.get(email).equals(userOtp);
    }
}
