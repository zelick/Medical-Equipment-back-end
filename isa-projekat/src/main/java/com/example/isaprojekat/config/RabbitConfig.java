package com.example.isaprojekat.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue orderQueue() {
        return new Queue("order", true);
    }

    @Bean
    public Queue equipmentQueue() {
        return new Queue("equipment", true);
    }

    @Bean
    public Queue rejectionQueue() {
        return new Queue("rejection", true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("order-exchange");
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderQueue).to(exchange).with("order.#");
    }

    @Bean
    public Binding equipmentBinding(Queue equipmentQueue, TopicExchange exchange) {
        return BindingBuilder.bind(equipmentQueue).to(exchange).with("equipment.#");
    }

    @Bean
    public Binding rejectionBinding(Queue equipmentQueue, TopicExchange exchange) {
        return BindingBuilder.bind(equipmentQueue).to(exchange).with("rejection.#");
    }
}

