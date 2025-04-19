package com.example.wallet.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;

    @Size(min = 10, max = 10, message = "User ID phải có đúng 10 ký tự")
    private String username;

    private String password;

    private String role;

    private String email;
}
