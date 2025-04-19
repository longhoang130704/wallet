package com.example.databaseService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SystemController {
    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @PostMapping("/pause-topic-response-test")
    public String pauseTopicTest() {

        MessageListenerContainer container = registry.getListenerContainer("topicReponseTest");
        container.pause();

        return "Pause success";
    }

    @PostMapping("/resume-topic-response-test")
    public String resumeTopicTest() {

        MessageListenerContainer container = registry.getListenerContainer("topicReponseTest");
        container.resume();

        return "resume success";
    }
}
