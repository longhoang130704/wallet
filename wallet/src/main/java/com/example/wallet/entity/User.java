package com.example.wallet.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;

    @Size(min = 10, max = 10, message = "User ID phải có đúng 10 ký tự")
    @Indexed(unique = true)
    private String username;

    private String password;

    private String role;
}
