package com.moviebooking.auth.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    
    @KafkaListener(topics = "movietopic", groupId = "movietopic_group")
    public void consume(String message){
        System.out.println( message);
    }
}