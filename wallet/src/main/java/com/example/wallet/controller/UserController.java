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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.entity.Device;
import com.example.wallet.entity.User;
import com.example.wallet.exception.type.CookieCreatedException;
import com.example.wallet.service.DeviceService;
import com.example.wallet.service.KafkaProducerService;
import com.example.wallet.service.KeyService;
import com.example.wallet.service.UserService;
import com.example.wallet.util.CookieUtil;
import com.example.wallet.util.WalletUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private KafkaProducerService kafkaProducerService;
    private DeviceService deviceService;
    private KeyService keyService;

    @Autowired
    public UserController(
            UserService userService,
            KafkaProducerService kafkaProducerService,
            DeviceService deviceService,
            KeyService keyService) {
        this.userService = userService;
        this.kafkaProducerService = kafkaProducerService;
        this.deviceService = deviceService;
        this.keyService = keyService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestHeader("User-Agent") String userAgent,
            HttpServletResponse response,
            @RequestBody User user) {
        // check user name is exist
        Optional<User> foundUser = userService.getUserByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username đã tồn tại");
        }
        // -------------------- Business logic -----------------------------
        // -------------------- Onboard user --------------------------------

        // tao device information
        Device deviceInfo = new Device();
        System.out.println(deviceInfo);
        // parse user agent to device info
        deviceInfo = deviceService.info(userAgent);

        // tao unique id voi hashing
        String userAgentInfo = user.getUsername() + " - " +
                user.getPassword() + "-" +
                user.getId();
        System.out.println("Chuỗi đầu vào tạo token: " + userAgentInfo);

        String hashUniqueUser = keyService.generateToken(userAgentInfo);

        // set devide_id befor create user
        user.setDevide_id(hashUniqueUser);

        // ghi hash string vao cookie
        Boolean isSuccess = CookieUtil.createCookie(response, "device_id", hashUniqueUser);
        if (!isSuccess) {
            throw new CookieCreatedException("create cookie failed");
        }

        // ------------------------------------------------------
        // call user service to save user to database
        User createdUser = userService.createUser(user);

        System.out.println(createdUser.toString());

        String userToString = createdUser.toString();
        System.out.println(userToString);

        // send by Kafka
        // kafkaProducerService.sendMessage("create-user", userToString);

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
