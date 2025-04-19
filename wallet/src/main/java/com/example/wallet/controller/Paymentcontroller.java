package com.example.wallet.controller;

import java.util.Optional;

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
import com.example.wallet.entity.User;
import com.example.wallet.service.EmailService;
import com.example.wallet.service.KafkaProducerService;
import com.example.wallet.service.SystemService;
import com.example.wallet.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payment")
public class Paymentcontroller {
    private KafkaProducerService kafkaProducerService;
    private SystemService systemService;
    private EmailService emailService;
    private UserService userService;

    @Autowired
    public Paymentcontroller(
            KafkaProducerService kafkaProducerService,
            SystemService systemService,
            EmailService emailService,
            UserService userService) {
        this.kafkaProducerService = kafkaProducerService;
        this.systemService = systemService;
        this.emailService = emailService;
        this.userService = userService;
    }

    // ------------ SEND PAYMENT REQUEST TO CONSUMMER-DATABASE SERVICE ----------

    @PostMapping("/external-payment")
    public ResponseEntity<String> proccessExternalPPayment(@RequestBody @Valid ExternalRequest externalRequest) {

        if (!emailService.verifyOtp(externalRequest.getEmail(), externalRequest.getOtp())) {
            return ResponseEntity.badRequest().body("Invalid OTP!");
        }

        String externalInfo = externalRequest.toString();

        System.out.println(externalInfo);

        kafkaProducerService.sendMessage("external-payment-request", externalInfo);

        return ResponseEntity.ok().body("Verify Otp successfully, Proccess external payment");
    }

    @PostMapping("/internal-payment")
    public ResponseEntity<String> proccessInternalPPayment(@RequestBody @Valid InternalRequest internalRequest) {
        if (!emailService.verifyOtp(internalRequest.getEmail(),
                internalRequest.getOtp())) {
            return ResponseEntity.badRequest().body("Invalid OTP!");
        }
        String internalInfo = internalRequest.toString();

        System.out.println(internalInfo);

        kafkaProducerService.sendMessage("internal-payment-request", internalInfo);

        return ResponseEntity.ok().body("Proccess internal payment");
    }

    // ------------------------------------------------------------
    // ---------------- SEND OTP TO EMAIL --------------

    @PostMapping("/send-otp-email")
    public ResponseEntity<String> verifyOtp(
            @RequestBody verifyOtpEmail verifyOtpEmail) {

        // verify user before proccess
        if (!systemService.verifyUser(verifyOtpEmail.getUsername(), verifyOtpEmail.getPassword())) {
            return ResponseEntity.ok("verify failed, please check username, password");
        }

        // get email by username
        Optional<User> foundUser = userService.getUserByUsername(verifyOtpEmail.getUsername());
        if (!foundUser.isPresent()) {
            return ResponseEntity.badRequest().body("User is not right");
        }

        User validUser = foundUser.get();
        // check email from request vs email user
        if (!validUser.getEmail().equals(verifyOtpEmail.getEmail())) {
            return ResponseEntity.badRequest().body("Email does not match");
        }

        // verify MFA:: generate otp -> send otp to email
        Boolean isSent = systemService.sendToEmail(verifyOtpEmail.getEmail());
        if (!isSent) {
            System.err.print("Email is not sent");
            return ResponseEntity.status(500).body("Email is not sent");
        }

        return ResponseEntity.ok("OTP send successfully!");
    }

    // -----------------------------------------------------------------------
    // --------- LISTEN RESPONSE EVENT FROM CONSUMER-DATABASE SERVICE --------

    // 1. ------- RESPONSE EXTERNAL PROCCESS --------------------

    // EXTERNAL PAYMENT SUCCESS
    @KafkaListener(topics = "external-payment-response-success", groupId = "groupA")
    public void listenExternalPaymentResponse(String responseMessage) {
        System.out.println("-----------------------");
        System.out.println(responseMessage);
        System.out.println("Send success notification to user: " + responseMessage);
    }

    // EXTERNAL PAYMENT FAILED
    @KafkaListener(topics = "external-payment-response-failed", groupId = "groupA")
    public void listenExternalPaymentResponseFailed(String responseMessage) {
        System.out.println("-----------------------");
        System.out.println(responseMessage);
        System.out.println("Send failed notification to user: " + responseMessage);

        // xử lí logic rollback SAGA pattern
        // TO DO
    }

    // 2. ------- RESPONSE INTERNAL PROCCESS --------------------

    // INTERNAL PAYMENT SUCCESS
    @KafkaListener(topics = "internal-payment-response-success", groupId = "groupA")
    public void listenInternalPaymentResponse(String responseMessage) {
        System.out.println("-----------------------");
        System.out.println(responseMessage);
        System.out.println("Send success notification to user: " + responseMessage);
    }

    // INTERNAL PAYMENT FAILED
    @KafkaListener(topics = "internal-payment-response-failed", groupId = "groupA")
    public void listenInternalPaymentResponseFailed(String responseMessage) {
        System.out.println("-----------------------");
        System.out.println(responseMessage);
        System.out.println("Send failed notification to user: " + responseMessage);

        // xử lí logic rollback SAGA pattern
        // TO DO
    }
}
