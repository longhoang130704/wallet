package com.example.wallet.service;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.wallet.util.KeyGeneratorUtil;

@Service
public class KeyService {
    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;
    private Algorithm algorithm;

    public KeyService() throws Exception {
        KeyPair keyPair = KeyGeneratorUtil.generateRsaKey();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.algorithm = Algorithm.RSA512(publicKey, privateKey);
    }

    public String generateToken(String subject) {
        return JWT.create()
                .withSubject(subject)
                .sign(algorithm);
    }

    public String validateToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }
}
