// package com.example.wallet.configuration;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SecurityConfig {

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {
// http
// .csrf(csrf -> csrf.disable()) // Tắt CSRF (không cần cho API)
// .authorizeHttpRequests(auth -> auth
// .requestMatchers("/auth/**").permitAll()
// .requestMatchers("/user/**").authenticated() // Đảm bảo route /user yêu cầu
// phải xác thực
// .anyRequest().authenticated()) // Các route còn lại yêu cầu phải xác thực
// .formLogin(login -> login.disable()) // Tắt form login mặc định của Spring
// Security
// .httpBasic(basic -> basic.disable()); // Tắt HTTP Basic authentication

// return http.build();
// }
// }
