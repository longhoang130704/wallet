package com.example.databaseService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @KafkaListener(topics = "topic-demo-request", groupId = "groupB")
    public void consume(String message) {
        System.out.println("Received message: " + message);
        System.out.println("Phan hoi lai la consumer da nhan duoc");
        String response = "Im consumer, receive message: " + message;
        kafkaProducerService.sendMessage(response);
    }
}
