package com.example.databaseService.dto.request.externalPayment;

import org.springframework.stereotype.Component;

import com.example.databaseService.enums.ExternalType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalRequest {
    @NotNull(message = "Wallet ID không được để trống")
    @Positive(message = "Wallet ID phải là số dương")
    private Long wallet_id;

    private ExternalType type;

    @NotNull(message = "Amount không được để trống")
    private Double amount;

    @NotNull(message = "Branch không được để trống")
    private String branch;

    private String description = "Not valid";

    private String metadata;
}
