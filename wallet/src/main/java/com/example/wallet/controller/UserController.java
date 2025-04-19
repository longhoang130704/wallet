package com.example.wallet.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.entity.User;
import com.example.wallet.service.KafkaProducerService;
import com.example.wallet.service.UserService;
import com.example.wallet.util.WalletUtil;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private KafkaProducerService kafkaProducerService;

    @Autowired
    public UserController(
            UserService userService,
            KafkaProducerService kafkaProducerService) {
        this.userService = userService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Optional<User> foundUser = userService.getUserByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username đã tồn tại");
        }

        User createdUser = userService.createUser(user);

        System.out.println(createdUser.toString());

        String userToString = createdUser.toString();

        // send by Kafka
        kafkaProducerService.sendMessage("create-user", userToString);

        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/balance")
    public ResponseEntity<String> getBalance(@RequestParam String username) {
        kafkaProducerService.sendMessage("get-balance-request", username);

        return ResponseEntity.ok().body("balance");
    }

    @KafkaListener(topics = "get-balance-response", groupId = "groupA")
    public ResponseEntity<String> responseBalance(String message) {
        System.out.println(message);

        // extract balance
        Double resultBalance = WalletUtil.extractBalance(message);
        String result = "balance: " + resultBalance;
        System.out.println(result);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User updatedUser) {
        Optional<User> user = userService.updateUser(username, updatedUser);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username)
                ? ResponseEntity.ok("User deleted successfully.")
                : ResponseEntity.notFound().build();
    }
}
