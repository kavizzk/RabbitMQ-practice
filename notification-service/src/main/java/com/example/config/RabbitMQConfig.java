package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue notificationQueue() {
        return new Queue(
                "shipment.notification.queue");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(
                "shipment.exchange");
    }

    @Bean
    public Binding shipmentBinding(
            Queue notificationQueue,
            TopicExchange exchange) {

        return BindingBuilder
                .bind(notificationQueue)
                .to(exchange)
                .with("shipment.*");
    }

    @Bean
    public Binding deliveryBinding(
            Queue notificationQueue,
            TopicExchange exchange) {

        return BindingBuilder
                .bind(notificationQueue)
                .to(exchange)
                .with("delivery.*");
    }
}
