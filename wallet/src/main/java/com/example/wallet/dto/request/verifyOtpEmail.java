package com.example.wallet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class verifyOtpEmail {
    private String username;
    private String password;
    private String email;
}
