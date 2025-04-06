package com.example.databaseService.entity;

import org.springframework.stereotype.Component;

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
public class User {

    private String id;

    @Size(min = 10, max = 10, message = "User ID phải có đúng 10 ký tự")
    private String username;

    private String password;

    private String role;
}
