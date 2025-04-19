package com.example.wallet.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static Boolean createCookie(HttpServletResponse response, String key, String value) {
        try {
            Cookie cookie = new Cookie(key, value);
            cookie.setMaxAge(7 * 24 * 60 * 60); // tồn tại 7 ngày
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    // public static String readCookie(@CookieValue(name = "device_id", defaultValue
    // = "Chưa có") String device_id) {
    // return device_id;
    // }
}
