package com.example.wallet.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalRequest {
    @NotNull(message = "from_wallet ID không được để trống")
    @Positive(message = "from_wallet ID phải là số dương")
    private Long from_wallet;

    @NotNull(message = "from_branch ID không được để trống")
    private String from_branch;

    @NotNull(message = "to_wallet ID không được để trống")
    @Positive(message = "to_wallet ID phải là số dương")
    private Long to_wallet;

    @NotNull(message = "to_branch ID không được để trống")
    private String to_branch;

    @NotNull(message = "amount ID không được để trống")
    private Double amount;

    private String description = "Not valid";

    private String metadata;

    @NotNull(message = "Require otp")
    private int otp;

    @NotNull(message = "Require email")
    private String email;
}
