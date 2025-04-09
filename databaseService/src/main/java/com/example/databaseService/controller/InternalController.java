package com.example.databaseService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.databaseService.dto.request.internalPayment.InternalRequest;
import com.example.databaseService.entity.Wallet;
import com.example.databaseService.mapper.InternalMapper;
import com.example.databaseService.service.InternalService;
import com.example.databaseService.service.KafkaProducerService;
import com.example.databaseService.service.WalletService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/internal-transaction")
public class InternalController {
    private final InternalService internalService;
    private WalletService walletService;
    private KafkaProducerService kafkaProducerService;

    @Autowired
    public InternalController(
            InternalService internalService,
            WalletService walletService,
            KafkaProducerService kafkaProducerService) {
        this.internalService = internalService;
        this.walletService = walletService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping
    public String proocessInternalTransaction(@Valid @RequestBody InternalRequest internalRequest) {

        internalService.processInternalTransaction(
                internalRequest.getFrom_wallet(),
                internalRequest.getFrom_branch(),
                internalRequest.getTo_wallet(),
                internalRequest.getTo_branch(),
                internalRequest.getAmount(),
                internalRequest.getDescription(),
                internalRequest.getMetadata());

        return "validate success";
    }

    @KafkaListener(topics = "internal-payment-request", groupId = "groupB")
    public Boolean consume(String message) {
        // System.out.println(message);

        InternalRequest kafkaInternalRequest = InternalMapper.toInternalRequest(message);
        // System.out.println(internalRequest.toString());
        Wallet fromWallet = walletService.getWalletByUserId(kafkaInternalRequest.getFrom_wallet());
        Wallet toWallet = walletService.getWalletByUserId(kafkaInternalRequest.getTo_wallet());

        System.out.println(fromWallet.toString());
        System.out.println(toWallet.toString());

        // ghi vao database
        Boolean isSuccess = internalService.processInternalTransaction(
                fromWallet.getWallet_id(),
                kafkaInternalRequest.getFrom_branch(),
                toWallet.getWallet_id(),
                kafkaInternalRequest.getTo_branch(),
                kafkaInternalRequest.getAmount(),
                kafkaInternalRequest.getDescription(),
                kafkaInternalRequest.getMetadata());
        // -------------------------------------------
        if (!isSuccess) {
            System.out.println("-------------------");
            System.out.println("Phan hoi lai internal-payment-request");
            kafkaProducerService.sendResponseMessage("internal-payment-response",
                    "internal payment response: failed!");
            System.out.println("-------------------");
            return false;
        }
        // ---------------------------------------------
        System.out.println("-------------------");
        System.out.println("Phan hoi lai internal-payment-request");
        kafkaProducerService.sendResponseMessage("internal-payment-response",
                "internal payment response: success!");
        System.out.println("-------------------");

        return true;
    }
}
