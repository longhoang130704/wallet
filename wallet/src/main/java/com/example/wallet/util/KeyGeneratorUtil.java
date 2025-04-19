package com.example.wallet.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGeneratorUtil {
    public static KeyPair generateRsaKey() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(4096); // private and public key have length 4096 bit
        return keyGen.generateKeyPair();
    }
}
