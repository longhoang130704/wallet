package com.example.databaseService.dto.request.wallet;

import org.springframework.stereotype.Component;

import com.example.databaseService.enums.WalletStatus;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WalletCreationRequest {
    @NotNull(message = "User ID không được để trống")
    @Min(value = 1000000000, message = "User ID phải có ít nhất 10 chữ số")
    @Max(value = 9999999999L, message = "User ID không được vượt quá 10 chữ số")
    private Long user_id;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 255, message = "Mật khẩu phải từ 6 đến 255 ký tự")
    private String password;

    private WalletStatus status = WalletStatus.ACTIVE;
}
