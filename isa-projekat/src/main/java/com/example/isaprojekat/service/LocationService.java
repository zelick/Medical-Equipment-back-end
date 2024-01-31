package com.example.isaprojekat.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class LocationService {

    @RabbitListener(queues = "location")
    public void consumeMessageFromQueue(String orderStatus) {
        System.out.println("Message recieved from queue: " + orderStatus);
    }
}
