package com.example.wallet.service;

import org.springframework.stereotype.Service;

import com.example.wallet.entity.Device;

import eu.bitwalker.useragentutils.UserAgent;

@Service
public class DeviceService {

    public Device info(String userAgent) {
        UserAgent ua = UserAgent.parseUserAgentString(userAgent);

        String osName = ua.getOperatingSystem().getName();
        String browserName = ua.getBrowser().getName();

        Device userAgentInfo = new Device();
        userAgentInfo.setOsName(osName);
        userAgentInfo.setBrowserName(browserName);

        return userAgentInfo;
    }
}
