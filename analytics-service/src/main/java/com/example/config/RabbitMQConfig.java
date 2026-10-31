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
    public Queue analyticsQueue() {
        return new Queue(
                "shipment.analytics.queue");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(
                "shipment.exchange");
    }

    @Bean
    public Binding shipmentBinding(
            Queue analyticsQueue,
            TopicExchange exchange) {

        return BindingBuilder
                .bind(analyticsQueue)
                .to(exchange)
                .with("shipment.*");
    }

    @Bean
    public Binding deliveryBinding(
            Queue analyticsQueue,
            TopicExchange exchange) {

        return BindingBuilder
                .bind(analyticsQueue)
                .to(exchange)
                .with("delivery.*");
    }
}
