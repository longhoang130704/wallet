package com.example.databaseService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.databaseService.dto.request.wallet.UpdatePasswordRequest;
import com.example.databaseService.dto.request.wallet.WalletCreationRequest;
import com.example.databaseService.dto.request.wallet.WalletLockRequest;
import com.example.databaseService.entity.User;
import com.example.databaseService.entity.Wallet;
import com.example.databaseService.mapper.UserMapper;
import com.example.databaseService.mapper.WalletMapper;
import com.example.databaseService.service.WalletService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/wallet")
public class WalletController {
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService, WalletMapper walletMapper) {
        this.walletService = walletService;
    }

    @PostMapping
    public String create(
            @Valid @RequestBody WalletCreationRequest walletCreationRequest) {
        Wallet newWallet = WalletMapper.toWallet(walletCreationRequest);

        try {
            walletService.create(newWallet);
            return "success";
        } catch (Exception e) {
            System.out.println("Error in WalletControlle create");
            System.out.println(e.getMessage());
            return "failed";
        }
    }

    @KafkaListener(topics = "create-user", groupId = "groupB")
    public String listenCreateUserEvent(String userInfo) {
        System.out.println(userInfo);

        User user = UserMapper.toUser(userInfo);
        System.out.println(user);

        Wallet createdWallet = WalletMapper.toWallet(user);

        System.out.println(createdWallet.toString());

        try {
            walletService.create(createdWallet);
            System.out.println("create wallet success");
            return "create wallet success";
        } catch (Exception e) {
            System.out.println("Error in WalletControlle create");
            System.out.println(e.getMessage());
            System.out.println("create wallet failed");
            return "create wallet failed";
        }
    }

    @PostMapping("/locked")
    public String lockWallet(
            @Valid @RequestBody WalletLockRequest walletLockRequest) {
        System.out.println(walletLockRequest.getWallet_id());
        return walletService.lockWallet(walletLockRequest.getWallet_id());
    }

    @PostMapping("/unlocked")
    public String unlockWallet(
            @Valid @RequestBody WalletLockRequest walletLockRequest) {
        System.out.println(walletLockRequest.getWallet_id());
        return walletService.unlockWallet(walletLockRequest.getWallet_id());
    }

    @GetMapping
    public Page<Wallet> getAllWallets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return walletService.getAllWallets(page, size);
    }

    @PostMapping("/update-password")
    public String updatePassword(
            @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return walletService.updatePassword(updatePasswordRequest.getWallet_id(), updatePasswordRequest.getPassword());
    }
}
