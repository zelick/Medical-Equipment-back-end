package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.NewDeliveryDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@NoArgsConstructor
public class LocationService {

    @RabbitListener(queues = "location")
    public void consumeMessageFromQueue(String orderStatus) {
        System.out.println("Message recieved from queue: " + orderStatus);
    }

    public void StartDelivery(NewDeliveryDto newDelivery) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8083/start";
        restTemplate.postForObject(url, newDelivery, Object.class);
    }
}
