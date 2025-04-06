package com.example.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.dto.request.ExternalRequest;
import com.example.wallet.dto.request.InternalRequest;
import com.example.wallet.dto.request.verifyOtpEmail;
import com.example.wallet.service.EmailService;
import com.example.wallet.service.KafkaProducerService;
import com.example.wallet.service.SystemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
public class Paymentcontroller {
    private KafkaProducerService kafkaProducerService;
    private SystemService systemService;
    private EmailService emailService;

    @Autowired
    public Paymentcontroller(
            KafkaProducerService kafkaProducerService,
            SystemService systemService,
            EmailService emailService) {
        this.kafkaProducerService = kafkaProducerService;
        this.systemService = systemService;
        this.emailService = emailService;
    }

    @PostMapping("/external-payment")
    public String proccessExternalPPayment(@Valid @RequestBody ExternalRequest externalRequest) {

        if (!emailService.verifyOtp(externalRequest.getEmail(), externalRequest.getOtp())) {
            return "Invalid OTP!";
        }

        String externalInfo = externalRequest.toString();

        System.out.println(externalInfo);

        kafkaProducerService.sendMessage("external-payment-request", externalInfo);

        return "Verify Otp successfully, Proccess external payment";
    }

    @PostMapping("/send-otp-email")
    public ResponseEntity<String> verifyOtp(
            @RequestBody verifyOtpEmail verifyOtpEmail) {

        // verify user before proccess
        if (!systemService.verifyUser(verifyOtpEmail.getUsername(), verifyOtpEmail.getPassword())) {
            return ResponseEntity.ok("verify failed, please check username, password");
        }

        // verify MFA:: generate otp -> send otp to email
        Boolean isSent = systemService.sendToEmail(verifyOtpEmail.getEmail());
        if (!isSent) {
            System.err.print("Email is not sent");
            return ResponseEntity.status(500).body("Email is not sent");
        }

        return ResponseEntity.ok("OTP verified successfully!");
    }

    @KafkaListener(topics = "external-payment-response", groupId = "groupA")
    public void listenExternalPaymentresponse(String responseMessage) {
        System.out.println("-----------------------");
        System.out.println(responseMessage);
        System.out.println("Send notification to user: " + responseMessage);
    }

    @PostMapping("internal-payment")
    public String proccessInternalPPayment(@Valid @RequestBody InternalRequest internalRequest) {

        return "Proccess internal payment";
    }
}
