package com.example.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.entity.Device;
import com.example.wallet.exception.type.CookieCreatedException;
import com.example.wallet.service.DeviceService;
import com.example.wallet.service.KeyService;
import com.example.wallet.util.CookieUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/device")
public class DeviceController {
    private final DeviceService deviceService;
    private final KeyService keyService;

    @Autowired
    public DeviceController(
            DeviceService deviceService,
            KeyService keyService) {
        this.deviceService = deviceService;
        this.keyService = keyService;
    }

    @GetMapping("/info")
    // public Device getDeviceInfo(@RequestHeader("User-Agent") String userAgent,
    // @Valid @RequestBody User user) {
    public Device getDeviceInfo(
            @RequestHeader("User-Agent") String userAgent,
            HttpServletResponse response) {
        // tao device information
        Device deviceInfo = new Device();
        // parse user agent to device info
        deviceInfo = deviceService.info(userAgent);

        String userAgentInfo = deviceInfo.getBrowserName() + " - " + deviceInfo.getOsName();
        System.out.println("Chuỗi đầu vào tạo token: " + userAgentInfo);
        // tao unique id voi hashing
        String hashUniqueUser = keyService.generateToken(userAgentInfo);

        // them hashing string lam id unique
        deviceInfo.setId(hashUniqueUser);

        // ghi hash string vao cookie
        Boolean isSuccess = CookieUtil.createCookie(response, "device_id", hashUniqueUser);
        if (!isSuccess) {
            throw new CookieCreatedException("create cookie failed");
        }
        System.out.println(deviceInfo.getOsName());
        System.out.println(deviceInfo.getBrowserName());
        System.out.println(userAgent);
        return deviceInfo;
    }

    @GetMapping("/id")
    public ResponseEntity<String> getDeviceId(
            @CookieValue(name = "device_id", defaultValue = "Chưa có") String device_id) {
        return ResponseEntity.ok().body(device_id);
    }
}
