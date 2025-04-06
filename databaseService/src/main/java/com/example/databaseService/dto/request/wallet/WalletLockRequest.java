package com.example.databaseService.dto.request.wallet;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletLockRequest {
    @NotNull(message = "Wallet ID không được để trống")
    @Positive(message = "Wallet ID phải là số dương")
    private Long wallet_id;
}
