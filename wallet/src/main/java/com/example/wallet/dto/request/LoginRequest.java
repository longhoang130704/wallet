package com.example.wallet.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Size(min = 10, max = 10, message = "User ID phải có đúng 10 ký tự")
    private String username;

    private String password;
}
