package com.example.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.entity.Device;
import com.example.wallet.entity.User;
import com.example.wallet.service.DeviceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/device")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/info")
    public Device getDeviceInfo(@RequestHeader("User-Agent") String userAgent, @Valid @RequestBody User user) {
        Device deviceInfo = new Device();
        deviceInfo = deviceService.info(userAgent);
        System.out.println(deviceInfo.getOsName());
        System.out.println(deviceInfo.getBrowserName());
        return deviceInfo;
    }
}
