package com.example.wallet.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "topic-demo-response", groupId = "groupA")
    public void consume(String message) {
        System.out.println("Received response message: " + message);
    }
}
