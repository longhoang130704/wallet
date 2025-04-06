package com.example.wallet.dto.request;

import com.example.wallet.enums.ExternalType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalRequest {
    @NotNull(message = "Wallet ID không được để trống")
    @Positive(message = "Wallet ID phải là số dương")
    private Long wallet_id; // username in database service

    private ExternalType type;

    @NotNull(message = "Amount không được để trống")
    private Double amount;

    @NotNull(message = "Branch không được để trống")
    private String branch;

    private String description = "Not valid";

    private String metadata;

    @NotNull(message = "Require otp")
    private int otp;

    @NotNull(message = "Require email")
    private String email;
}
