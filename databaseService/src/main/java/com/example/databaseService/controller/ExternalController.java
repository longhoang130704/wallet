package com.example.databaseService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.databaseService.dto.request.externalPayment.ExternalRequest;
import com.example.databaseService.entity.Wallet;
import com.example.databaseService.mapper.ExternalMapper;
import com.example.databaseService.service.ExternalService;
import com.example.databaseService.service.KafkaProducerService;
import com.example.databaseService.service.WalletService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/external-transaction")
public class ExternalController {
    private final ExternalService externalService;
    private KafkaProducerService kafkaProducerService;
    private WalletService walletService;

    @Autowired
    public ExternalController(
            ExternalService externalService,
            KafkaProducerService kafkaProducerService,
            WalletService walletService) {
        this.externalService = externalService;
        this.kafkaProducerService = kafkaProducerService;
        this.walletService = walletService;
    }

    @PostMapping
    public String proccessExternalTransaction(@Valid @RequestBody ExternalRequest externalRequest) {

        externalService.processExternalTransaction(
                externalRequest.getWallet_id(),
                externalRequest.getType().toString(),
                externalRequest.getAmount(),
                externalRequest.getBranch(),
                externalRequest.getDescription(),
                externalRequest.getMetadata());
        return "validate success";
    }

    @KafkaListener(topics = "external-payment-request", groupId = "groupB")
    public Boolean consume(String message) {
        try {
            // System.out.println("Received message: " + message);
            // System.out.println("-------------------");

            // System.out.println("Phan hoi lai la consumer da nhan duoc");
            // String response = "Im consumer, receive message: " + message;
            // System.out.println(response);
            // System.out.println("-------------------");

            ExternalRequest kafkaExternalRequest = ExternalMapper.toExternalRequest(message);
            System.out.println(kafkaExternalRequest.toString());
            System.out.println("-------------------");

            Wallet foundWallet = walletService.getWalletByUserId(kafkaExternalRequest.getWallet_id());
            // System.out.println("foundWallet: " + foundWallet.toString());

            externalService.processExternalTransaction(
                    foundWallet.getWallet_id(),
                    kafkaExternalRequest.getType().toString(),
                    kafkaExternalRequest.getAmount(),
                    kafkaExternalRequest.getBranch(),
                    kafkaExternalRequest.getDescription(),
                    kafkaExternalRequest.getMetadata());

            System.out.println("-------------------");

            System.out.println("Phan hoi lai external-payment-request-success");
            kafkaProducerService.sendResponseMessage("external-payment-response-success",
                    "external payment response: success!");
            System.out.println("-------------------");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            kafkaProducerService.sendResponseMessage("external-payment-response-failed",
                    "external payment response: failed!");
        }
        return true;
    }

}
