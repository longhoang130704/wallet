package com.example.databaseService.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.databaseService.dto.request.wallet.WalletCreationRequest;
import com.example.databaseService.entity.User;
import com.example.databaseService.entity.Wallet;
import com.example.databaseService.enums.WalletStatus;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class WalletMapper {
    public static Wallet toWallet(WalletCreationRequest walletCreationRequest) {
        Wallet newWallet = new Wallet();

        newWallet.setUser_id(walletCreationRequest.getUser_id());
        newWallet.setPassword(walletCreationRequest.getPassword());
        newWallet.setBalance(0.0);
        newWallet.setStatus(WalletStatus.ACTIVE);
        newWallet.setCreated_at(LocalDateTime.now());
        newWallet.setUpdated_at(LocalDateTime.now());

        return newWallet;
    }

    public static Wallet toWallet(User user) {
        Wallet wallet = new Wallet();

        Long user_id = Long.parseLong(user.getUsername());

        wallet.setUser_id(user_id);
        wallet.setPassword(user.getPassword());
        wallet.setBalance(0.0);
        wallet.setStatus(WalletStatus.ACTIVE);
        wallet.setCreated_at(LocalDateTime.now());
        wallet.setUpdated_at(LocalDateTime.now());

        return wallet;
    }
}
